(ns advent-clj.core
  (:gen-class)
  (:require [advent-clj.day1 :refer [day1]]
            [advent-clj.day10 :refer [day10]]
            [advent-clj.day11 :refer [day11]]
            [advent-clj.day2 :refer [day2]]
            [advent-clj.day3 :refer [day3]]
            [advent-clj.day4 :refer [day4]]
            [advent-clj.day5 :refer [day5]]
            [advent-clj.day6 :refer [day6]]
            [advent-clj.day7 :refer [day7]]
            [advent-clj.day8 :refer [day8]]
            [advent-clj.day9 :refer [day9]]
            [advent-clj.utils :refer [run-day]]))

(def days
  [day1
   day2
   day3
   day4
   day5
   day6
   day7
   day8
   day9
   day10
   day11])

(defn -main
  []
  (doall
   (map-indexed
    (fn [index [& parts]]
      (let [day-number (inc index)]
        (println (format "****** Day %02d ******" day-number))
        (doseq
         [[number, result] (run-day day-number parts)]
          (println (format "* Part %d:" number))
          (println result)))
      (println "********************")
      (println))
    days)))
