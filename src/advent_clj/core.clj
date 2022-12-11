(ns advent-clj.core
  (:gen-class)
  (:require [advent-clj.day1 :as day1]
            [advent-clj.day2 :as day2]
            [advent-clj.day3 :as day3]
            [advent-clj.day4 :as day4]
            [advent-clj.day5 :as day5]
            [advent-clj.day6 :as day6]
            [advent-clj.day7 :as day7]
            [advent-clj.day8 :as day8]
            [advent-clj.day9 :as day9]
            [advent-clj.day10 :as day10]
            [advent-clj.day11 :as day11]))

(defn -main
  []
  (day1/day1)
  (day2/day2)
  (day3/day3)
  (day4/day4)
  (day5/day5)
  (day6/day6)
  (day7/day7)
  (day8/day8)
  (day9/day9)
  (day10/day10)
  (day11/day11))
