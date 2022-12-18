(ns advent-clj.day16
  (:require [clojure.string :as str]))

(def ^:private valve-matcher
  #"Valve (\w+) has flow rate=(\d+); tunnels? leads? to valves? (.+)")

(defn- parse-valve [valves input]
  (let [[_ valve-name flow successors] (re-find valve-matcher input)]
    (assoc
     valves
     valve-name
     {:flow (parse-long flow)
      :successors (re-seq #"\w+" successors)})))

(defn- shortest-paths [valves]
  (let [steps (into {} (map
                        (fn [[valve {:keys [successors]}]]
                          [valve (into {} (map #(vector % 1) successors))])
                        valves))]
    (reduce
     (fn [steps [step from to]]
       (update-in steps [from to] #(min (or % ##Inf) (+ (get-in steps [from step] ##Inf)
                                                        (get-in steps [step to] ##Inf)))))
     steps
     (for [step (keys steps)
           from (keys steps)
           to (keys steps)]
       [step from to]))))

(defn- run-simulation
  ([valves start minutes] (run-simulation {:valves (into {} (filter #(> (:flow (second %)) 0) valves))
                                           :paths (shortest-paths valves)
                                           :bitmasks (into {} (map-indexed (fn [index [valve]]
                                                                             [valve (bit-shift-left 1 index)])
                                                                           valves))
                                           :from start
                                           :minutes minutes
                                           :visited 0
                                           :pressure 0
                                           :costs {}}))
  ([{:keys [valves paths from minutes bitmasks visited pressure costs] :as state}]
   (reduce
    (fn [costs to]
      (let [minutes (- minutes (get-in paths [from to]) 1)]
        (if (or (> (bit-and (get bitmasks to) visited) 0)
                (<= minutes 0))
          costs
          (run-simulation (assoc state
                                 :from to
                                 :minutes minutes
                                 :visited (bit-or visited (get bitmasks to))
                                 :pressure (+ pressure (* minutes (get-in valves [to :flow])))
                                 :costs costs)))))
    (update costs visited #(max (or % 0) pressure))
    (keys valves))))

(defn- part1 [input]
  (let [valves (reduce
                parse-valve
                {}
                (str/split input #"\n"))]
    (apply max (vals (run-simulation valves "AA" 30)))))

(defn- part2 [input]
  (let [valves (reduce
                parse-valve
                {}
                (str/split input #"\n"))
        paths (run-simulation valves "AA" 26)]
    (apply max (for [[my-visited my-pressure] paths
                     [elephant-visited elephant-pressure] paths
                     :when (= (bit-and my-visited elephant-visited) 0)]
                 (+ my-pressure elephant-pressure)))))

(def day16 [part1 part2])
