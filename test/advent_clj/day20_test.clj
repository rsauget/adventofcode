(ns advent-clj.day20-test
  (:require [advent-clj.day20 :refer [day20]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day20))
(def part2 (second day20))

(def real-input (slurp-input 20))

(def simple-input
  "1
2
-3
3
-2
0
4")

(deftest day20-test
  (testing "Part 1 - simple"
    (is (=
         3
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         7225
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         1623178306
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         2066
         (part2 real-input)))))
