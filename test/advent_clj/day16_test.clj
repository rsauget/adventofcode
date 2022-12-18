(ns advent-clj.day16-test
  (:require [advent-clj.day16 :refer [day16]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day16))
(def part2 (second day16))

(def real-input (slurp-input 16))

(def simple-input
  "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II")

(deftest day16-test
  (testing "Part 1 - simple"
    (is (=
         1651
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         1647
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         1707
         (part2 simple-input))))

  (testing "Part 2 - real"
    (is (=
         2169
         (part2 real-input)))))
