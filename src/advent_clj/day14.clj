(ns advent-clj.day14
  (:require [clojure.pprint :as pp]
            [clojure.string :as str]))

(defn- rock? [rocks [x y]]
  ((get rocks x #{}) y))

(defn- print-map [rocks floor-y path sand]
  (let [min-x (first (first rocks))
        max-x (first (last rocks))
        min-y 0
        max-y (or floor-y (apply max (map #(apply max (second %)) rocks)))]
    ;; (Thread/sleep 100)
    (print (str (char 27) "[2J"))
    (print (str (char 27) "[;H"))
    (println (str/join "\n"
                       (for [x (reverse (range min-x (inc max-x)))]
                         (str/join ""
                                   (for [y (range min-y (inc max-y))]
                                     (cond
                                       (= sand [x y]) "O"
                                       (rock? rocks [x y]) "#"
                                       (= floor-y y) "#"
                                       :else "."))))))
    (println sand)
    (println (apply str (repeat (inc (- max-x min-x)) "=")))))

(defn- add-rock [rocks [x y]]
  (update rocks x #(conj (or % (sorted-set)) y)))

(defn- parse-rock-path [rock-path]
  (map (comp
        #(into [] (map read-string %))
        rest)
       (re-seq #"(?<x>\d+),(?<y>\d+)" rock-path)))

(defn- add-rock-path [rocks rock-path]
  (if (nil? (second rock-path))
    rocks
    (recur (add-rock rocks (first rock-path))
           (let [[[x1 y1 :as rock1] [x2 y2 :as rock2]] rock-path]
             (cond
               (= rock1 rock2) (rest rock-path)
               (= x1 x2) (conj (rest rock-path) [x1 ((if (> y1 y2) dec inc) y1)])
               (= y1 y2) (conj (rest rock-path) [((if (> x1 x2) dec inc) x1) y1]))))))

(defn- parse-rocks [input]
  (reduce
   add-rock-path
   (sorted-map)
   (map parse-rock-path (str/split input #"\n"))))

(def sand-start [500 0])

(defn- fall-sand-part1
  ([rocks] (fall-sand-part1 rocks '() sand-start))
  ([rocks path [x y]]
  ;;  (print-map rocks nil path [x y])
   (let [first-rock-y (first (filter #(> % y) (get rocks x)))
         max-reachable-y (when (some? first-rock-y)
                           (dec first-rock-y))]
     (cond
       (nil? max-reachable-y) rocks
       (> max-reachable-y y) (recur
                              rocks
                              (into path (map #(vector x %) (range y max-reachable-y)))
                              [x max-reachable-y])
       (not (rock? rocks [x (inc y)])) (recur
                                      rocks
                                      (conj path [x y])
                                      [x (inc y)])
       (not (rock? rocks [(dec x) (inc y)])) (recur
                                              rocks
                                              (conj path [x y])
                                              [(dec x) (inc y)])
       (not (rock? rocks [(inc x) (inc y)])) (recur
                                              rocks
                                              (conj path [x y])
                                              [(inc x) (inc y)])
       (= sand-start [x y]) (add-rock rocks [x y])
       :else (recur
              (add-rock rocks [x y])
              (rest path)
              (first path))))))

(defn- fall-sand-part2
  ([rocks floor-y] (fall-sand-part2 rocks floor-y '() sand-start))
  ([rocks floor-y path sand]
  ;;  (print-map rocks floor-y path sand)
   (if (nil? sand)
     rocks
     (let [[x y] sand
           first-rock-y (first (filter #(> % y) (get rocks x)))
           max-reachable-y (or (when (some? first-rock-y)
                                 (dec first-rock-y))
                               (dec floor-y))]
       (cond
         (nil? max-reachable-y) rocks
         (> max-reachable-y y) (recur
                                rocks
                                floor-y
                                (into path (map #(vector x %) (range y max-reachable-y)))
                                [x max-reachable-y])
         (= y (dec floor-y)) (recur
                              (add-rock rocks sand)
                              floor-y
                              (rest path)
                              (or (first path) sand-start))
         (not (rock? rocks [(dec x) (inc y)])) (recur
                                                (add-rock rocks sand)
                                                floor-y
                                                (if (rock? rocks [(inc x) (inc y)])
                                                  path
                                                  (conj path [(inc x) (inc y)]))
                                                [(dec x) (inc y)])
         (not (rock? rocks [(inc x) (inc y)])) (recur
                                                (add-rock rocks sand)
                                                floor-y
                                                path
                                                [(inc x) (inc y)])
         :else (recur
                (add-rock rocks sand)
                floor-y
                (rest path)
                (first path)))))))



(defn- part1 [input]
  (let [rocks (parse-rocks input)]
    (-
     (reduce + (map (comp count second) (fall-sand-part1 rocks)))
     (reduce + (map (comp count second) rocks)))))

(defn- part2 [input]
  (let [rocks (parse-rocks input)
        floor-y (+ 2 (apply max (map #(apply max (second %)) rocks)))]
    (-
     (reduce + (map (comp count second) (fall-sand-part2 rocks floor-y)))
     (reduce + (map (comp count second) rocks)))))

(def day14 [part1 part2])


;; (def simple-input
;;   "498,4 -> 498,6 -> 496,6
;; 503,4 -> 502,4 -> 502,9 -> 494,9")

;; (part1 simple-input)
;;(part2 simple-input)