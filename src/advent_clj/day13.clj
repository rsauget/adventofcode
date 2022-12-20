(ns advent-clj.day13
  (:require [clojure.string :as str]))

(defn- packets-sorted? [left right]
  (cond
    (= left right) nil
    (and (number? left) (number? right)) (< left right)
    (number? left) (recur [left] right)
    (number? right) (recur left [right])
    (empty? left) true
    (empty? right) false
    :else (let [left-head (first left)
                left-rest (rest left)
                right-head (first right)
                right-rest (rest right)
                heads-sorted (packets-sorted? left-head right-head)]
            (if (some? heads-sorted)
              heads-sorted
              (recur left-rest right-rest)))))

(defn- part1 [input]
  (reduce +
          (map-indexed
           (fn [index packet]
             (let [[left right] (map read-string (str/split-lines packet))]
               (if (packets-sorted? left right)
                 (inc index)
                 0)))
           (str/split input #"\n\n"))))

(def dividers [[[2]]
               [[6]]])

(defn- part2 [input]
  (reduce
   *
   (keep-indexed
    (fn [index packet]
      (when ((set dividers) packet)
        (inc index)))
    (sort
     #(if (packets-sorted? %1 %2) -1 1)
     (into dividers (map
                     read-string
                     (filter seq (str/split-lines input))))))))

(def day13 [part1 part2])
