(ns advent-clj.day17
  (:require [advent-clj.utils :refer [enumerate]]
            [clojure.string :as str]))

(defn- height [world]
  (apply max (map #(first (rseq (second %))) world)))

(defn- print-map [world shape]
  (Thread/sleep 500)
  (let [min-x -1
        max-x 7
        max-y (+ 5 (height world))
        min-y (max 0 (- max-y 40))]
    (print (str (char 27) "[2J"))
    (print (str (char 27) "[;H"))
    (println (str/join "\n"
                       (for [y (reverse (range min-y (inc max-y)))]
                         (str/join ""
                                   (for [x (range min-x (inc max-x))]
                                     (cond
                                       (= [0 -1] [y x]) "+"
                                       (= [0 7] [y x]) "+"
                                       (= 0 y) "-"
                                       (= -1 x) "|"
                                       (= 7 x) "|"
                                       ((get world x) y) "#"
                                       (shape [y x]) "@"
                                       :else "."))))))))

(defn- clamp [min max x]
  (cond (< x min) min
        (> x max) max
        :else x))

;; ####
(defn- h-bar [y-top]
  (let [y (+ y-top 4)]
    (sorted-set [y 2] [y 3] [y 4] [y 5])))

;; .#.
;; ###
;; .#.
(defn- cross [y-top]
  (let [y (+ y-top 4)]
    (sorted-set [y 3] [(+ y 1) 2] [(+ y 1) 3] [(+ y 1) 4] [(+ y 2) 3])))

;; ..#
;; ..#
;; ###
(defn- corner [y-top]
  (let [y (+ y-top 4)]
    (sorted-set [y 2] [y 3] [y 4] [(+ y 2) 4] [(+ y 1) 4])))

;; #
;; #
;; #
;; #
(defn- v-bar [y-top]
  (let [y (+ y-top 4)]
    (sorted-set [y 2] [(+ y 1) 2] [(+ y 2) 2] [(+ y 3) 2])))

;; ##
;; ##
(defn- square [y-top]
  (let [y (+ y-top 4)]
    (sorted-set [y 2] [y 3] [(+ y 1) 2] [(+ y 1) 3])))

(def shapes
  (enumerate
   (cycle
    (enumerate
     [h-bar
      cross
      corner
      v-bar
      square]))))

(defn- parse-pushes [input]
  (enumerate (cycle (map-indexed (fn [index push] [index (if (= push \>) 1 -1)]) input))))

(defn- move-shape [world shape [move-y move-x]]
  (let [moved-shape (into (sorted-set) (map (fn [[y x]]
                                              [(+ y move-y) (+ x move-x)])
                                            shape))]
    (if (every? (fn [[y x]]
                  (and (<= 0 x 6)
                       (not ((get world x) y))))
                moved-shape)
      [moved-shape false]
      [shape true])))

(defn- fingerprint [seen-states world shape shape-type shape-index push-type push-index]
  (let [tower-height (height world)
        shape-height (- (apply max (map first shape)) tower-height)
        world-front (into [] (map #(clamp -10 0 (- (first (rseq (second %))) tower-height)) world))
        fingerprint-key [shape-height shape-type push-type world-front]
        already-seen (seen-states fingerprint-key)]
    (if already-seen
      [seen-states already-seen]
      [(assoc seen-states fingerprint-key {:shape-index shape-index
                                           :push-index push-index
                                           :tower-height tower-height})])))

(defn- pick-shape [shapes y]
  (let [[shape-index [shape-type shape]] (first shapes)]
    [shape-index shape-type (shape y)]))

(defn- play-turns
  ([pushes shape-count] (play-turns pushes
                                    {0 (sorted-set 0)
                                     1 (sorted-set 0)
                                     2 (sorted-set 0)
                                     3 (sorted-set 0)
                                     4 (sorted-set 0)
                                     5 (sorted-set 0)
                                     6 (sorted-set 0)}
                                    (rest shapes)
                                    (dec shape-count)
                                    (pick-shape shapes 0)
                                    {}))
  ([pushes world shapes shape-count [shape-index shape-type shape] seen-states]
  ;;  (print-map world shape)
  ;;  (println shape-count)
   (let [[push-index [push-type push]] (first pushes)
         [pushed-shape] (move-shape world shape [0 push])
         [fallen-shape crashed] (move-shape world pushed-shape [-1 0])
         tower-height (height world)
         [seen-states already-seen] (fingerprint seen-states world shape shape-type shape-index push-type push-index)]
     (cond
       (and already-seen
            (> (quot shape-count (- shape-index (:shape-index already-seen))) 0))
       (let [cycle-length (- shape-index (:shape-index already-seen))
             cycle-count (quot shape-count cycle-length)
             skipped-shapes (* cycle-count cycle-length)
             skipped-tower-height (* cycle-count (- tower-height (:tower-height already-seen)))]
        ;;  (println "cycle found from" (:shape-index already-seen) "to" shape-index)
        ;;  (println (format "skipping %d cycles (%d shapes)" cycle-count skipped-shapes))
        ;;  (println "tower height increased by" skipped-tower-height)
         (recur pushes
                (update-vals world (fn [ys]
                                     (into (sorted-set)
                                           (map #(+ % skipped-tower-height) ys))))
                shapes
                (- shape-count skipped-shapes)
                [shape-index
                 shape-type
                 (into (sorted-set) (map (fn [[y x]] [(+ y skipped-tower-height) x]) shape))]
                seen-states))
       (not crashed) (recur (rest pushes)
                            world
                            shapes
                            shape-count
                            [shape-index shape-type fallen-shape]
                            seen-states)
       :else (let [updated-world (reduce (fn [world [y x]] (update world x #(conj % y))) world fallen-shape)]
               (if (= shape-count 0)
                 updated-world
                 (recur (rest pushes)
                        updated-world
                        (rest shapes)
                        (dec shape-count)
                        (pick-shape shapes (height updated-world))
                        seen-states)))))))

(defn- part1 [input]
  (let [world (play-turns (parse-pushes input) 2022)]
    (height world)))

(defn- part2 [input]
  (let [world (play-turns (parse-pushes input) 1000000000000)]
    (height world)))

(def day17 [part1 part2])
