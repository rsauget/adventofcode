(ns advent-clj.day10-test
  (:require [advent-clj.day10 :refer [day10]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day10))
(def part2 (second day10))

(def real-input (slurp-input 10))

(def simple-input
  "addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop")

(deftest day10-test
  (testing "Part 1 - simple"
    (is (=
         13140
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         13060
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (= "
██  ██  ██  ██  ██  ██  ██  ██  ██  ██  
███   ███   ███   ███   ███   ███   ███ 
████    ████    ████    ████    ████    
█████     █████     █████     █████     
██████      ██████      ██████      ████
███████       ███████       ███████     "
         (part2 simple-input))))
  
  (testing "Part 2 - real"
    (is (= "
████   ██ █  █ ███  █  █ █    ███  ████ 
█       █ █  █ █  █ █  █ █    █  █    █ 
███     █ █  █ ███  █  █ █    █  █   █  
█       █ █  █ █  █ █  █ █    ███   █   
█    █  █ █  █ █  █ █  █ █    █ █  █    
█     ██   ██  ███   ██  ████ █  █ ████ "
         (part2 real-input)))))
