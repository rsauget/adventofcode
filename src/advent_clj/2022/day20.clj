(ns advent-clj.2022.day20
  (:require [clojure.string :as str]))

(defn- find-index [value coll]
  (first (keep-indexed #(when (= value %2) %1) coll)))

(defn- move [message message-length indices index]
  (let [item-index (find-index index indices)
        without-item (concat (take item-index indices)
                             (drop (inc item-index) indices))
        new-index (mod (+ item-index (get message index)) (dec message-length))
        new-index-corrected (if (= 0 new-index) message-length new-index)]
    (into [] (concat (take new-index-corrected without-item)
                     [index]
                     (drop new-index-corrected without-item)))))

(defn- part1 [input]
  (let [message (into [] (map read-string (str/split-lines input)))
        message-length (count message)
        indices (range message-length)
        decrypted-message (into [] (map #(get message %)
                                        (reduce
                                         (partial move message message-length)
                                         indices
                                         indices)))
        zero-index (find-index 0 decrypted-message)]
    ;; (println decrypted-message)
    (reduce + (map #(get decrypted-message (mod (+ zero-index %) message-length))
                   [1000
                    2000
                    3000]))))

(defn- part2 [input]
  (let [message (into [] (map (comp (partial * 811589153) read-string)
                              (str/split-lines input)))
        message-length (count message)
        indices (range message-length)
        decrypted-message (into [] (map #(get message %)
                                        (reduce
                                         (partial move message message-length)
                                         indices
                                         (apply concat (repeat 10 indices)))))
        zero-index (find-index 0 decrypted-message)]
    ;; (println decrypted-message)
    (reduce + (map #(get decrypted-message (mod (+ zero-index %) message-length))
                   [1000
                    2000
                    3000]))))

(def day20 [part1 part2])
