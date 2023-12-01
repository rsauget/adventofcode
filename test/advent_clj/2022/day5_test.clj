(ns advent-clj.2022.day5-test
  (:require [advent-clj.2022.day5 :refer [day5]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day5))
(def part2 (second day5))

(def real-input (slurp-input 2022 5))

(def simple-input
  "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(deftest ^:year-2022 day5-test
  (testing "Part 1 - simple"
    (is (=
         "CMZ"
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         "QNNTGTPFN"
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         "MCD"
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         "GGNPJBTTR"
         (part2 real-input)))))
