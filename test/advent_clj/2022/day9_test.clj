(ns advent-clj.2022.day9-test
  (:require [advent-clj.2022.day9 :refer [day9]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day9))
(def part2 (second day9))

(def real-input (slurp-input 2022 9))

(def simple-input-1
  "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")

(def simple-input-2
  "R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20")

(deftest ^:year-2022 day9-test
  (testing "Part 1 - simple"
    (is (=
         13
         (part1 simple-input-1))))

  (testing "Part 1 - real"
    (is (=
         6271
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         36
         (part2 simple-input-2))))

  (testing "Part 2 - real"
    (is (=
         2458
         (part2 real-input)))))
