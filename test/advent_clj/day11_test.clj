(ns advent-clj.day11-test
  (:require [advent-clj.day11 :refer [day11]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day11))
(def part2 (second day11))

(def real-input (slurp-input 11))

(def simple-input
  "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1")

(deftest day11-test
  (testing "Part 1 - simple"
    (is (=
         10605
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         111210
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (= 
         2713310158
         (part2 simple-input))))
  
  (testing "Part 2 - real"
    (is (= 
         15447387620
         (part2 real-input)))))
