(ns advent-clj.day2-test
  (:require [advent-clj.day2 :refer [day2]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day2))
(def part2 (second day2))

(def real-input (slurp-input 2))

(def simple-input
  "A Y
B X
C Z")

(deftest day2-test
  (testing "Part 1 - simple"
    (is (=
         15
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         11767
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         12
         (part2 simple-input))))
  
  (testing "Part 2 - real"
    (is (=
         13886
         (part2 real-input)))))
