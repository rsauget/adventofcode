(ns advent-clj.day6
  (:require [advent-clj.utils :refer [day]]))

(defn- find-marker [marker-length input]
  (first
   (keep-indexed
    (fn
      [index value]
      (if
       (=
        (count (set value))
        marker-length)
        (+ index marker-length)
        nil))
    (partition
     marker-length
     1
     input))))

(defn- part1
  [input]
  (find-marker 4 input))

(defn- part2
  [input]
  (find-marker 14 input))

(defn day6
  []
  (day 6 part1 part2))
