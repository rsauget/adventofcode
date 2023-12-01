(ns advent-clj.2022.day6-test
  (:require [advent-clj.2022.day6 :refer [day6]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day6))
(def part2 (second day6))

(def real-input (slurp-input 2022 6))

(def simple-input
  "mjqjpqmgbljsphdztnvjfqwrcgsmlb")

(deftest ^:year-2022 day6-test
  (testing "Part 1 - simple"
    (is (=
         7
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         1542
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         19
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         3153
         (part2 real-input)))))
