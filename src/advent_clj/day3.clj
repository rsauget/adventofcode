(ns advent-clj.day3
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn- get-item-priority
  [letter]
  (let
   [code (int letter)]
    (cond
      (<= (int \a) code (int \z)) (+ 1 (- code (int \a)))
      (<= (int \A) code (int \Z)) (+ 27 (- code (int \A))))))

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
                      (map
                       set group)))]
    (get-item-priority common-item)))

(defn- part2
  [input]
  (let [groups (partition
                3
                (str/split input #"\n"))]
    (reduce
     +
     (map
      get-group-priority
      groups))))

(def day3 [part1 part2])
