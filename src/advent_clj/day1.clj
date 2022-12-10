(ns advent-clj.day1
  (:require [advent-clj.utils :refer [day]]
            [clojure.string :as str]))

(defn- get-elves-calories [input]
  (let [elves (str/split input #"\n\n")]
    (map
     (fn
       [elf]
       (let
        [calories (map
                   read-string
                   (str/split elf #"\n"))]
         (reduce + calories)))
     elves)))

(defn- part1
  [input]
  (apply
   max
   (get-elves-calories input)))

(defn- part2
  [input]
  (reduce
   +
   (take
    3
    (sort
     >
     (get-elves-calories input)))))

(def day1 #(day 1 part1 part2))

