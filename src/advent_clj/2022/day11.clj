(ns advent-clj.2022.day11
  (:require [clojure.string :as str]))

(defn- parse-test
  [test if-true if-false]
  (let [divisible-by (read-string (re-find #"\d+" test))
        next-monkey-id-if-true (re-find #"\d+" if-true)
        next-monkey-id-if-false (re-find #"\d+" if-false)]
    {:divisible-by divisible-by
     :next-monkey (fn [worry]
                    (if (= 0 (mod worry divisible-by))
                      next-monkey-id-if-true
                      next-monkey-id-if-false))}))

(defn- parse-operation
  [definition]
  (let [[left operator right] (rest (re-matches #"\s*Operation: new = (old|\d+) ([+-/*]) (old|\d+)" definition))]
    (fn [old]
      (let [left (if (= left "old") old (read-string left))
            right (if (= right "old") old (read-string right))]
        (case operator
          "+" (+ left right)
          "-" (- left right)
          "*" (* left right)
          "/" (/ left right))))))

(defn- parse-monkey
  [definition]
  (let [lines (str/split-lines definition)
        [monkey-id starting-items operation test if-true if-false] lines]
    (merge
     {:id (re-find #"\d+" monkey-id)
      :items (map read-string (re-seq #"\d+" starting-items))
      :operation (parse-operation operation)}
     (parse-test test if-true if-false))))

(defn- handle-item
  [relief monkeys monkey-id item]
  (let [monkey (get monkeys monkey-id)
        item (relief ((:operation monkey) item))
        next-monkey-id ((:next-monkey monkey) item)
        next-monkey (get monkeys next-monkey-id)]
    (merge
     monkeys
     {monkey-id (merge
                 monkey
                 {:items (rest (:items monkey))
                  :inspected (inc (get monkey :inspected 0))})
      next-monkey-id (update
                      next-monkey
                      :items #(conj % item))})))

(defn- run-rounds
  [rounds initial-monkeys relief]
  (let [monkey-ids (keys initial-monkeys)
        handle-item (partial handle-item relief)]
    (loop [round 1
           remaining-monkey-ids monkey-ids
           monkeys initial-monkeys]
      (cond
        (> round rounds) monkeys
        (empty? remaining-monkey-ids) (recur
                                       (inc round)
                                       monkey-ids
                                       monkeys)
        :else (let [monkey-id (first remaining-monkey-ids)
                    items (get-in monkeys [monkey-id :items])
                    item (first items)]
                (if (nil? item)
                  (recur
                   round
                   (rest remaining-monkey-ids)
                   monkeys)
                  (recur
                   round
                   remaining-monkey-ids
                   (handle-item monkeys monkey-id item))))))))

(defn- monkey-business
  [monkeys]
  (reduce
   *
   (take
    2
    (sort
     >
     (map
      #(get (second %) :inspected 0)
      monkeys)))))

(defn- part1
  [input]
  (let [definitions (str/split input #"\n\n")
        monkeys (into
                 (sorted-map)
                 (map
                  (fn [monkey]
                    [(:id monkey) (dissoc monkey :id)])
                  (map parse-monkey definitions)))]
    (monkey-business
     (run-rounds 20 monkeys #(Math/floorDiv (long %) 3)))))

(defn- part2
  [input]
  (let [definitions (str/split input #"\n\n")
        monkeys (into
                 (sorted-map)
                 (map
                  (fn [monkey]
                    [(:id monkey) (dissoc monkey :id)])
                  (map parse-monkey definitions)))
        modulo (reduce * (map #(:divisible-by (second %)) monkeys))]
    (monkey-business
     (run-rounds 10000 monkeys #(mod % modulo)))))

(def day11 [part1 part2])
