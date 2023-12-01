(ns advent-clj.2023.day1-test
  (:require [advent-clj.2023.day1 :refer [day1]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day1))
(def part2 (second day1))

(def real-input (slurp-input 2023 1))

(def simple-input-1
  "1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet")

(def simple-input-2
  "two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen")

(deftest ^:year-2023 day1-test
  (testing "Part 1 - simple"
    (is (=
         142
         (part1 simple-input-1))))

  (testing "Part 1 - real"
    (is (=
         54630
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         281
         (part2 simple-input-2))))

  (testing "Part 2 - real"
    (is (=
         54770
         (part2 real-input)))))
