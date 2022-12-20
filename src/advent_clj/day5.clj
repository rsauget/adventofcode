(ns advent-clj.day5
  (:require [clojure.string :as str]))

(def ^:private stack-numbers-matcher
  #"^[ 0-9]+$")

(def ^:private stack-item-matcher
  #"(?:\[(\w)\]|\s{3})\s?")

(def ^:private move-matcher
  #"^move (\d+) from (\d+) to (\d+)$")

(defn- parse-stacks-line
  [stacks line]
  (merge-with
   (fn [stack item] (concat [] stack item))
   stacks
   (into {} (keep-indexed
             (fn [index matches]
               (when (second matches) [(inc index)
                                       (second matches)]))
             (re-seq stack-item-matcher line)))))

(defn- parse-input
  ([lines] (parse-input lines (sorted-map)))
  ([[line & remaining] stacks]
   (if
    (re-matches stack-numbers-matcher line)
     [stacks (rest remaining)]
     (recur
      remaining
      (parse-stacks-line stacks line)))))

(defn- apply-move
  [reverse-items stacks move]
  (let [[count from to] (map read-string (rest (re-matches move-matcher move)))
        from-stack (get stacks from)
        to-stack (get stacks to)]
    (assoc stacks
           from (drop count from-stack)
           to (concat
               ((if reverse-items reverse identity) (take count from-stack))
               to-stack))))

(defn- part1
  [input]
  (let [[stacks moves] (parse-input (str/split-lines input))]
    (str/join
     ""
     (map
      first
      (vals
       (reduce
        (partial apply-move true)
        stacks
        moves))))))

(defn- part2
  [input]
  (let [[stacks moves] (parse-input (str/split-lines input))]
    (str/join
     ""
     (map
      first
      (vals
       (reduce
        (partial apply-move false)
        stacks
        moves))))))

(def day5 [part1 part2])
