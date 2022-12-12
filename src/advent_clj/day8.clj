(ns advent-clj.day8
  (:require [clojure.string :as str]))

(defn- is-visible
  [trees x y]
  (let [tree (get-in trees [y x])
        height (count trees)
        width (count (first trees))
        up (map #(get % x) (subvec trees 0 y))
        down (map #(get % x) (subvec trees (inc y) height))
        left (subvec (get trees y) 0 x)
        right (subvec (get trees y) (inc x) width)]
    (or
     (empty? up)
     (empty? down)
     (empty? left)
     (empty? right)
     (> tree (apply max up))
     (> tree (apply max down))
     (> tree (apply max left))
     (> tree (apply max right)))))

(defn- viewing-distance
  [trees tree]
  (let [[visible invisible] (split-with (partial > tree) trees)]
    (+
     (count visible)
     (if (empty? invisible) 0 1))))

(defn- scenic-score
  [trees x y]
  (let [tree (get-in trees [y x])
        height (count trees)
        width (count (first trees))
        up (reverse (map #(get % x) (subvec trees 0 y)))
        down (map #(get % x) (subvec trees (inc y) height))
        left (reverse (subvec (get trees y) 0 x))
        right (subvec (get trees y) (inc x) width)]
    (*
     (viewing-distance up tree)
     (viewing-distance down tree)
     (viewing-distance left tree)
     (viewing-distance right tree))))

(defn- part1
  [input]
  (let [trees (vec
               (map
                (fn [line] (vec (map #(Character/digit % 10) (seq line))))
                (str/split input #"\n")))]
    (reduce
     +
     (map-indexed
      (fn [y line]
        (count
         (filter
          identity
          (map-indexed
           (fn [x _tree]
             (is-visible trees x y))
           line))))
      trees))))

(defn- part2
  [input]
  (let [trees (vec
               (map
                (fn [line] (vec (map #(Character/digit % 10) (seq line))))
                (str/split input #"\n")))]
    (apply
     max
     (map-indexed
      (fn [y line]
        (apply
         max
         (map-indexed
          (fn [x _tree]
            (scenic-score trees x y))
          line)))
      trees))))

(def day8 [part1 part2])
