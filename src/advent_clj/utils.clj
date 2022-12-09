(ns advent-clj.utils
  (:require [clojure.java.io :as io]))

(defn day
  [day-number & parts]
  (let
   [day (format "day%d" day-number)
    input (slurp (.getFile (io/resource (format "%s/input.txt" day))))]
    (println (format "------ %s ------" day))
    (doseq
     [[number, part] (map-indexed
                      (fn [index, part] [(inc index), part])
                      parts)]
      (println
       (format "part%d:" number)
       (part input)))
    (println "------------------")
    (println)))
