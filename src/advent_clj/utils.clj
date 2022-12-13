(ns advent-clj.utils
  (:require [clojure.java.io :as io]))

(defn slurp-input
  [day-number]
  (slurp (.getFile (io/resource (format "day%s/input.txt" day-number)))))

(defn run-day
  [day-number [& parts]]
  (let
   [input (slurp-input day-number)]
    (map-indexed
     (fn
       [index part]
       [(inc index) (part input)])
     parts)))
