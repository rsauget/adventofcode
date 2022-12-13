(ns advent-clj.day2
  (:require [clojure.string :as str]))

(def shape-scores
  {"X" 1
   "Y" 2
   "Z" 3})

(def winning-moves
  {"A" "Y"
   "B" "Z"
   "C" "X"})

(def draw-moves
  {"A" "X"
   "B" "Y"
   "C" "Z"})

(def losing-moves
  {"A" "Z"
   "B" "X"
   "C" "Y"})

(defn- get-turn-score-part1 [turn]
  (let
   [[elf-move my-move] (str/split turn #" ")]
    (+
     (get shape-scores my-move)
     (cond
       (= (get winning-moves elf-move) my-move) 6
       (= (get draw-moves elf-move) my-move) 3
       :else 0))))

(defn- get-turn-score-part2 [turn]
  (let
   [[elf-move outcome] (str/split turn #" ")
    [my-move move-score] (cond
                            (= outcome "X") [(get losing-moves elf-move) 0]
                            (= outcome "Y") [(get draw-moves elf-move) 3]
                            (= outcome "Z") [(get winning-moves elf-move) 6])]
    (+
     (get shape-scores my-move)
     move-score)))

(defn- part1
  [input]
  (reduce
   +
   (map
    get-turn-score-part1
    (str/split input #"\n"))))

(defn- part2
  [input]
  (reduce
   +
   (map
    get-turn-score-part2
    (str/split input #"\n"))))

(def day2 [part1 part2])
