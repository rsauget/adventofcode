(ns advent-clj.2022.day18-test
  (:require [advent-clj.2022.day18 :refer [day18]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day18))
(def part2 (second day18))

(def real-input (slurp-input 2022 18))

(def simple-input
  "2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5")

(deftest ^:year-2022 day18-test
  (testing "Part 1 - simple"
    (is (=
         64
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         3576
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         58
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         2066
         (part2 real-input)))))
