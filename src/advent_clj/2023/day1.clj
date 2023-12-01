(ns advent-clj.2023.day1
  (:require [clojure.string :as str]))

(defn- char-to-int
  [c]
  (let [zero (int \0)
        nine (int \9)
        code (int c)]
    (when (<= zero code nine)
      (- code zero))))

(defn- decode-line
  [line]
  (let [digits (keep char-to-int (seq line))]
    (+ (* 10 (first digits)) (last digits))))

(defn- part1
  [input]
  (reduce + (map decode-line (str/split-lines input))))

(def digits-strings ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])

(defn- parse-digit
  [digit-str]
  (or (first (keep-indexed #(when (#{digit-str} %2) %1) digits-strings))
      (read-string digit-str)))

(defn- first-digit
  [line]
  (parse-digit (re-find (re-pattern (str/join "|" (conj digits-strings "\\d")))
                        line)))

(defn- last-digit
  [line]
  (parse-digit (str/reverse (re-find (re-pattern (str/join "|" (conj (map str/reverse digits-strings) "\\d")))
                                     (str/reverse line)))))

(defn- decode-line-2
  [line]
  (+ (* 10 (first-digit line)) (last-digit line)))

(defn- part2
  [input]
  (reduce + (map decode-line-2 (str/split-lines input))))

(def day1 [part1 part2])