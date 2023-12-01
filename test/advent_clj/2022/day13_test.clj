(ns advent-clj.2022.day13-test
  (:require [advent-clj.2022.day13 :refer [day13]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day13))
(def part2 (second day13))

(def real-input (slurp-input 2022 13))

(def simple-input
  "[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]")

(deftest ^:year-2022 day13-test
  (testing "Part 1 - simple"
    (is (=
         13
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         6240
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         140
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         23142
         (part2 real-input)))))
