(ns advent-clj.2022.day4
  (:require [clojure.string :as str]))

(defn- parse-range [range]
  (map read-string (str/split range #"-")))

(defn- range-contains
  [left right]
  (let
   [[left-min left-max] (parse-range left)
    [right-min right-max] (parse-range right)]
    (and
     (<= left-min right-min)
     (>= left-max right-max))))

(defn- ranges-overlap
  [left right]
  (let
   [[left-min left-max] (parse-range left)
    [right-min right-max] (parse-range right)]
    (and
     (<= left-min right-max)
     (>= left-max right-min))))

(defn- part1
  [input]
  (count
   (filter
    (comp
     #(or
       (apply range-contains %)
       (apply range-contains (reverse %)))
     #(str/split % #","))

    (str/split-lines input))))

(defn- part2
  [input]
  (count
   (filter
    #(apply ranges-overlap (str/split % #","))
    (str/split-lines input))))

(def day4 [part1 part2])
