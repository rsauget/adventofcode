(ns advent-clj.utils
  (:require [clojure.java.io :as io]))

(defn slurp-input
  [day-number]
  (slurp (.getFile (io/resource (format "day%s/input.txt" day-number)))))

(defn run-day
  [day-number [& parts]]
  (println (format "****** Day %02d ******" day-number))
  (doall (let
          [input (slurp-input day-number)]
           (map-indexed
            (fn
              [index part]
              (println (format "* Part %d:" (inc index)))
              (println (time (part input))))
            parts)))
  (println "********************")
  (println))
