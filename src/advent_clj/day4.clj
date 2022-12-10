(ns advent-clj.day4
  (:require [advent-clj.utils :refer [day]]
            [clojure.string :as str]))

(defn- parse-range [range]
  (map read-string (str/split range #"-")))

(defn- range-contains
  [left right]
  (let
   [[left-min, left-max] (parse-range left)
    [right-min, right-max] (parse-range right)]
    (and
     (<= left-min right-min)
     (>= left-max right-max))))

(defn- ranges-overlap
  [left right]
  (let
   [[left-min, left-max] (parse-range left)
    [right-min, right-max] (parse-range right)]
    (and
     (<= left-min right-max)
     (>= left-max right-min))))

(defn- part1
  [input]
  (count
   (filter
    (fn
      [line]
      (let
       [[left, right] (str/split line #",")]
        (or
         (range-contains left right)
         (range-contains right left))))
    (str/split input #"\n"))))

(defn- part2
  [input]
  (count
   (filter
    (fn
      [line]
      (let
       [[left, right] (str/split line #",")]
        (ranges-overlap left right)))
    (str/split input #"\n"))))


(def day4 #(day 4 part1 part2))
