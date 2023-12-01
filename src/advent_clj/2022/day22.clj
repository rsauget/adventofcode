(ns advent-clj.2022.day22
  (:require [clojure.string :as str]
            [clojure.pprint :as pprint]
            [clojure.test :as t]))

(defn- parse-world
  ([input] (parse-world input 1 (sorted-map)))
  ([[line & rest-lines] y world]
   (if (empty? line)
     [world
      (map #(if (re-matches #"\d+" %)
              (read-string %)
              %)
           (re-seq #"\d+|[LR]" (first rest-lines)))]
     (recur rest-lines
            (inc y)
            (into world (keep-indexed (fn [x c]
                                        (case c
                                          \. [[y (inc x)] true]
                                          \# [[y (inc x)] false]
                                          nil))
                                      line))))))

(def directions {:up [-1 0]
                 :down [1 0]
                 :left [0 -1]
                 :right [0 1]})

(defn- turn-left [direction]
  (direction {:up :left
              :left :down
              :down :right
              :right :up}))

(defn- turn-right [direction]
  (direction {:up :right
              :right :down
              :down :left
              :left :up}))

(defn- get-next-position [world [y x :as position] direction]
  (let [next-position (into [] (map + position (get directions direction)))
        next-position-wrapped (if (some? (get world next-position))
                                next-position
                                (first (direction
                                        {:right (first (filter #(= y (first (first %))) world))
                                         :left (last (filter #(= y (first (first %))) world))
                                         :up (last (filter #(= x (second (first %))) world))
                                         :down (first (filter #(= x (second (first %))) world))})))]
    (if (get world next-position-wrapped)
      next-position-wrapped
      position)))

(defn- apply-moves [{:keys [world position direction moves] :as state}]
  (if (nil? moves)
    state
    (let [[move & remaining-moves] moves]
      (cond (= move "L") (recur (assoc state
                                       :direction (turn-left direction)
                                       :moves remaining-moves))
            (= move "R") (recur (assoc state
                                       :direction (turn-right direction)
                                       :moves remaining-moves))
            (= move 0) (recur (assoc state
                                     :moves remaining-moves))
            :else (recur (assoc state
                                :moves (cons (dec move) remaining-moves)
                                :position (get-next-position world position direction)))))))

(defn- part1 [input]
  (let [[world moves] (parse-world (str/split-lines input))
        {:keys [position direction]} (apply-moves
                                      {:world world
                                       :position (first (keys world))
                                       :direction :right
                                       :moves moves})]
    (+ (* 1000 (first position))
       (* 4 (second position))
       (direction {:right 0
                   :down 1
                   :left 2
                   :up 3}))))

(defn- neighbor-cube-faces [world length [y x]]
  (filter
   #(some? (world (second %)))
   (update-vals
    directions
    (fn [direction] (into [] (map #(+ (* length %1) %2) direction [y x]))))))

(defn- explore-cube-corners [visited]
  (reduce
   (fn [visited [face direction]]
     (if (get-in visited [face direction]) visited
         (let [left-neighbor (get-in visited [face (turn-left direction)])
               via-left (get-in visited [left-neighbor direction])
               right-neighbor (get-in visited [face (turn-right direction)])
               via-right (get-in visited [right-neighbor direction])]
           (assoc-in visited [face direction] (or via-left via-right)))))
   visited
   (for [face (keys visited)
         direction (keys directions)]
     [face direction])))

(defn- explore-cube
  ([world] (let [start (first (keys world))]
             (explore-cube world
                           (long (Math/sqrt (/ (count world) 6)))
                           {start {}}
                           [start])))
  ([world length visited todo]
   (if (empty? todo)
     (drop-while (partial some #(nil? (second %)))
                 (iterate explore-cube-corners visited))
     (let [[[y x] & remaining] todo
           neighbors (neighbor-cube-faces world length [y x])]
       (recur
        world
        length
        (update visited [y x] #(into (or % {}) neighbors))
        (concat remaining (remove #(get visited %)
                                  (map second neighbors))))))))



(defn- part2 [input]
  (let [[world moves] (parse-world (str/split-lines input))]
    (explore-cube world)))

(def day22 [part1 part2])

(def simple-input
  "        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5")

;; (time (pprint/pprint (part2 simple-input)))