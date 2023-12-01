(ns advent-clj.2022.day22-test
  (:require [advent-clj.2022.day22 :refer [day22]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day22))
(def part2 (second day22))

(def real-input (slurp-input 2022 22))

(def simple-input
  "        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5")

(deftest ^:year-2022 day22-test
  (testing "Part 1 - simple"
    (is (=
         6032
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         80392
         (part1 real-input))))

  ;; (testing "Part 2 - simple"
  ;;   (is (=
  ;;        -1
  ;;        (part2 simple-input))))

  ;; (testing "Part 2 - real"
  ;;   (is (=
  ;;        -1
  ;;        (part2 real-input))))
  )
