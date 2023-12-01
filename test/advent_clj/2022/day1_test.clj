(ns advent-clj.2022.day1-test
  (:require [advent-clj.2022.day1 :refer [day1]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day1))
(def part2 (second day1))

(def real-input (slurp-input 2022 1))

(def simple-input
  "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

(deftest ^:year-2022 day1-test
  (testing "Part 1 - simple"
    (is (=
         24000
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         74198
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         45000
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         209914
         (part2 real-input)))))
