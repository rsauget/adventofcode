(ns advent-clj.day14-test
  (:require [advent-clj.day14 :refer [day14]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day14))
(def part2 (second day14))

(def real-input (slurp-input 14))

(def simple-input
  "498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9")

(deftest day14-test
  (testing "Part 1 - simple"
    (is (=
         24
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         655
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         93
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         26484
         (part2 real-input))))
  )
