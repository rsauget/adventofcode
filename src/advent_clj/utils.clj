(ns advent-clj.utils
  (:require [clojure.java.io :as io]))

(defn slurp-input
  [year day-number]
  (slurp (.getFile (io/resource (format "%s/day%s/input.txt" year day-number)))))

(defn run-day
  [year day-number [& parts]]
  (println (format "****** Day %02d ******" day-number))
  (doall (let
          [input (slurp-input year day-number)]
           (map-indexed
            (fn
              [index part]
              (println (format "* Part %d:" (inc index)))
              (println (time (part input))))
            parts)))
  (println "********************")
  (println))

;; (defn gcd [a b]
;;   (if (zero? b) a
;;       (recur b (mod a b))))

;; (defn lcm [a b]
;;   (cond (zero? a) 0
;;         (zero? b) 0
;;         :else (abs (* b (quot a (gcd a b))))))

(defn enumerate [coll]
  (map-indexed (fn [index item] [index item]) coll))