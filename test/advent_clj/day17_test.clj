(ns advent-clj.day17-test
  (:require [advent-clj.day17 :refer [day17]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day17))
(def part2 (second day17))

(def real-input (slurp-input 17))

(def simple-input
  ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(deftest day17-test
  (testing "Part 1 - simple"
    (is (=
         3068
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         3219
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         1514285714288
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         1582758620701
         (part2 real-input)))))
