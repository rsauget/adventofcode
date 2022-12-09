(ns advent-clj.day5
  (:require [advent-clj.utils :refer [day]]
            [clojure.string :as str]))

(def ^:private stack-numbers-matcher
  #"^[ 0-9]+$")

(def ^:private stack-item-matcher
  #"(?:\[(\w)\]|\s{3})\s?")

(def ^:private move-matcher
  #"^move (\d+) from (\d+) to (\d+)$")

(defn- extend-vector
  [vector length default-value]
  (let [vector-length (count vector)]
    (if (<= length vector-length)
      vector
      (vec
       (concat
        vector
        (repeat (- length vector-length) default-value))))))

(defn- parse-stacks-line
  [stacks line]
  (reduce
   (fn
     [stacks [index item]]
     (let [stacks (extend-vector stacks (inc index) [])
           stack (get stacks index [])]
       (if
        (nil? item) stacks
        (assoc stacks index (conj stack item)))))
   stacks

   (map-indexed
    (fn [index matches] [index (get matches 1)])
    (re-seq stack-item-matcher line))))

(defn- parse-input
  ([lines] (parse-input lines []))
  ([lines stacks]
   (let
    [line (first lines)
     remaining (rest lines)]
     (if
      (re-matches stack-numbers-matcher line)
       [stacks (rest remaining)]
       (recur
        remaining
        (parse-stacks-line
         stacks
         line))))))

(defn- apply-move
  [reverse-items stacks move]
  (let [[count from to] (map read-string (rest (re-matches move-matcher move)))
        from-index (dec from)
        to-index (dec to)
        from-stack (get stacks from-index)]
    (vec
     (map-indexed
      (fn [index stack]
        (cond
          (= index from-index) (drop count stack)
          (= index to-index) (concat
                              ((if reverse-items reverse identity) (take count from-stack))
                              stack)
          :else stack))
      stacks))))

(defn- part1
  [input]
  (let [[stacks moves] (parse-input
                        (str/split input #"\n"))]
    (str/join
     ""
     (map
      first
      (reduce
       (partial apply-move true)
       stacks
       moves)))))

(defn- part2
  [input]
  (let [[stacks moves] (parse-input
                        (str/split input #"\n"))]
    (str/join
     ""
     (map
      first
      (reduce
       (partial apply-move false)
       stacks
       moves)))))


(defn day5
  []
  (day 5 part1 part2))
