(ns advent-clj.day4-test
  (:require [advent-clj.day4 :refer [day4]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day4))
(def part2 (second day4))

(def real-input (slurp-input 4))

(def simple-input
  "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(deftest day4-test
  (testing "Part 1 - simple"
    (is (=
         2
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         542
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         4
         (part2 simple-input))))
  
  (testing "Part 2 - real"
    (is (=
         900
         (part2 real-input)))))
