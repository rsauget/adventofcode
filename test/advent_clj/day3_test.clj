(ns advent-clj.day3-test
  (:require [advent-clj.day3 :refer [day3]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day3))
(def part2 (second day3))

(def real-input (slurp-input 3))

(def simple-input
  "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(deftest day3-test
  (testing "Part 1 - simple"
    (is (=
         157
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         7811
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         70
         (part2 simple-input))))
  
  (testing "Part 2 - real"
    (is (=
         2639
         (part2 real-input)))))
