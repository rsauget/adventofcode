(ns advent-clj.day20
  (:require [advent-clj.utils :refer [enumerate]]
            [clojure.string :as str]
            [clojure.pprint :as pprint]))

(defn- find-index [value coll]
  (first (keep-indexed #(when (= value %2) %1) coll)))

(defn- move [message-length message [_ item-value :as item]]
  (let [item-index (find-index item message)
        without-item (concat (take item-index message)
                             (drop (inc item-index) message))
        new-index (mod (+ item-index item-value) (dec message-length))
        new-index-corrected (if (= 0 new-index) message-length new-index)]
    ;; (println (map second message))
    (into [] (concat (take new-index-corrected without-item)
                     [item]
                     (drop new-index-corrected without-item)))))

(defn- part1 [input]
  (let [message (enumerate (map read-string (str/split-lines input)))
        message-length (count message)
        decrypted-message (into [] (map second
                                        (reduce
                                         (partial move message-length)
                                         message
                                         message)))
        zero-index (find-index 0 decrypted-message)]
    ;; (println decrypted-message)
    (reduce + (map #(get decrypted-message (mod (+ zero-index %) message-length))
                   [1000
                    2000
                    3000]))))

(defn- part2 [input]
  (let [message (enumerate (map (comp (partial * 811589153) read-string)
                                (str/split-lines input)))
        message-length (count message)
        decrypted-message (into [] (map second
                                        (reduce
                                         (partial move message-length)
                                         message
                                         (apply concat (repeat 10 message)))))
        zero-index (find-index 0 decrypted-message)]
    ;; (println decrypted-message)
    (reduce + (map #(get decrypted-message (mod (+ zero-index %) message-length))
                   [1000
                    2000
                    3000]))))

(def day20 [part1 part2])
