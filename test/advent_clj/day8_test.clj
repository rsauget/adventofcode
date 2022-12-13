(ns advent-clj.day8-test
  (:require [advent-clj.day8 :refer [day8]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day8))
(def part2 (second day8))

(def real-input (slurp-input 8))

(def simple-input
  "30373
25512
65332
33549
35390")

(deftest day8-test
  (testing "Part 1 - simple"
    (is (=
         21
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         1676
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         8
         (part2 simple-input))))
  
  (testing "Part 2 - real"
    (is (=
         313200
         (part2 real-input)))))
