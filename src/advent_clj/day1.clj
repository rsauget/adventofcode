(ns advent-clj.day1
  (:require [clojure.string :as str]))

(defn- get-elf-calories
  [elf]
  (let
   [calories (map
              read-string
              (str/split-lines elf))]
    (reduce + calories)))

(defn- get-elves-calories [input]
  (let [elves (str/split input #"\n\n")]
    (map
     get-elf-calories
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

(def day1 [part1 part2])