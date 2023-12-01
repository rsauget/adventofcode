(ns advent-clj.core
  (:gen-class)
  (:require [advent-clj.utils :refer [run-day]]
            [advent-clj.2022.core :as year-2022]
            [advent-clj.2023.core :as year-2023]))

(def days {2022 year-2022/days
           2023 year-2023/days})

(defn -main
  [& args]
  (let [first-arg (first args)
        year (if (some? first-arg)
               (read-string first-arg)
               (.getYear (java.time.LocalDateTime/now)))]
    (println year)
    (doall
     (map-indexed
      (fn [index [& parts]]
        (run-day year (inc index) parts))
      (get days year)))))

