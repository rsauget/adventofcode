(ns advent-clj.2022.day19-test
  (:require [advent-clj.2022.day19 :refer [day19]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day19))
(def part2 (second day19))

(def real-input (slurp-input 2022 19))

(def simple-input
  (str/replace (str/replace "Blueprint 1:
  Each ore robot costs 4 ore.
  Each clay robot costs 2 ore.
  Each obsidian robot costs 3 ore and 14 clay.
  Each geode robot costs 2 ore and 7 obsidian.

Blueprint 2:
  Each ore robot costs 2 ore.
  Each clay robot costs 3 ore.
  Each obsidian robot costs 3 ore and 8 clay.
  Each geode robot costs 3 ore and 12 obsidian."
                            #"\n  " " ")
               #"\n\n" "\n"))

(deftest ^:year-2022 day19-test
  (testing "Part 1 - simple"
    (is (=
         33
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         1725
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         3472
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         15510
         (part2 real-input)))))
