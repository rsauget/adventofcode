(ns advent-clj.2023.day4
  (:require [clojure.math :refer [pow]]
            [clojure.pprint :as pprint]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn- parse-card
  [input]
  (let [[game-name card-numbers] (str/split input #": ")
        game-id (read-string (re-find #"\d+" game-name))
        [winning-part numbers-part] (str/split card-numbers #" \| ")
        winning-numbers (into (sorted-set) (map read-string (re-seq #"\d+" winning-part)))
        numbers (into (sorted-set) (map read-string (re-seq #"\d+" numbers-part)))
        winners (count (set/intersection winning-numbers numbers))]
    {:id game-id
     :winning winning-numbers
     :numbers numbers
     :winners winners}))

(defn- part1
  [input]
  (reduce + (map #(int (pow 2 (dec (:winners %))))
                 (map parse-card (str/split-lines input)))))

(defn- process-card
  [counts {:keys [id winners]}]
  (reduce (fn [counts-acc id-to-update]
            (update counts-acc id-to-update #(+ (or % 0) (get counts-acc id))))
          (update counts id #(inc (or % 0)))
          (take winners (drop (inc id) (range)))))

(defn- part2
  [input]
  (reduce + (map val (reduce process-card
                             (sorted-map) 
                             (map parse-card (str/split-lines input))))))

(def day4 [part1 part2])