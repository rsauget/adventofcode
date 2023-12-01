(ns advent-clj.2022.day15-test
  (:require [advent-clj.2022.day15 :refer [day15]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day15))
(def part2 (second day15))

(def real-input (slurp-input 2022 15))

(def simple-input
  "Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3")

(deftest ^:year-2022 day15-test
  (testing "Part 1 - simple"
    (is (=
         26
         (part1 simple-input 10))))

  (testing "Part 1 - real"
    (is (=
         4724228
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         56000011
         (part2 simple-input 20))))

  (testing "Part 2 - real"
    (is (=
         13622251246513
         (part2 real-input)))))
