(ns advent-clj.2023.day2-test
  (:require [advent-clj.2023.day2 :refer [day2]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day2))
(def part2 (second day2))

(def real-input (slurp-input 2023 2))

(def simple-input
  "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

(deftest ^:year-2023 day2-test
  (testing "Part 1 - simple"
    (is (=
         8
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         3099
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         2286
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         72970
         (part2 real-input)))))
