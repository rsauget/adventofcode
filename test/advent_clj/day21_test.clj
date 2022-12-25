(ns advent-clj.day21-test
  (:require [advent-clj.day21 :refer [day21]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day21))
(def part2 (second day21))

(def real-input (slurp-input 21))

(def simple-input
  "root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32")

(deftest day21-test
  (testing "Part 1 - simple"
    (is (=
         152
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         62386792426088
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         301
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         -1
         (part2 real-input)))))
