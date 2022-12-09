(ns advent-clj.day3
  (:require [advent-clj.utils :refer [day]]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn- get-item-priority
  [letter]
  (let
   [code (int letter)]
    (cond
      (and (>= code (int \a)) (<= code (int \z))) (+ (- code (int \a)) 1)
      (and (>= code (int \A)) (<= code (int \Z))) (+ (- code (int \A)) 27))))

(defn- get-rucksack-priority
  [line]
  (let
   [line-length (count line)
    pockets (partition (/ line-length 2) line)
    common-item (first
                 (apply
                  set/intersection
                  (map set pockets)))]
    (get-item-priority common-item)))

(defn- part1
  [input]
  (reduce
   +
   (map
    get-rucksack-priority
    (str/split input #"\n"))))

(defn- get-group-priority
  [group]
  (let [common-item (first
                     (apply
                      set/intersection
                      group))]
    (get-item-priority common-item)))

(defn- part2
  [input]
  (let [groups (partition
                3
                (map
                 set
                 (str/split input #"\n")))]
    (reduce
     +
     (map
      get-group-priority
      groups))))

(defn day3
  []
  (day 3 part1 part2))
