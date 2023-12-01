(ns advent-clj.2022.day12-test
  (:require [advent-clj.2022.day12 :refer [day12]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day12))
(def part2 (second day12))

(def real-input (slurp-input 2022 12))

(def simple-input
  "Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi")

(deftest ^:year-2022 day12-test
  (testing "Part 1 - simple"
    (is (=
         31
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         520
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         29
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         508
         (part2 real-input)))))
