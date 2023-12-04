(ns advent-clj.2023.day3-test
  (:require [advent-clj.2023.day3 :refer [day3]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day3))
(def part2 (second day3))

(def real-input (slurp-input 2023 3))

(def simple-input
  "467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..")

(deftest ^:year-2023 day3-test
  (testing "Part 1 - simple"
    (is (=
         4361
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         544433
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         467835
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         76314915
         (part2 real-input)))))
