(ns advent-clj.2023.day4-test
  (:require [advent-clj.2023.day4 :refer [day4]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day4))
(def part2 (second day4))

(def real-input (slurp-input 2023 4))

(def simple-input
  "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11")

(deftest ^:year-2023 day4-test
  (testing "Part 1 - simple"
    (is (=
         13
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         27454
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         30
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         6857330
         (part2 real-input)))))
