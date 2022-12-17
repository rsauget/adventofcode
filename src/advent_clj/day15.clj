(ns advent-clj.day15
  (:require [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn- distance [[x1 y1] [x2 y2]]
  (+ (abs (- x1 x2)) (abs (- y1 y2))))

(defn- parse-input [input]
  (map
   (fn [line]
     (let [[sensor beacon] (map (comp (partial map read-string) rest)
                                (re-seq #"x=(-?\d+), y=(-?\d+)" line))]
       {:sensor sensor
        :beacon beacon
        :distance (distance sensor beacon)}))
   (str/split input #"\n")))

(defn- get-blocked-xs [target {:keys [sensor beacon]}]
  (let [radius (distance sensor beacon)
        [x y] sensor
        sensor-range (- radius (abs (- target y)))]
    (when (>= sensor-range 0)
      [(- x sensor-range)
       (+ x sensor-range)])))

(defn- add-range [ranges [from to]]
  (let [covered-ranges (filter #(and (<= (dec from) (second %)) (>= to (first %))) ranges)
        start-range (first covered-ranges)
        end-range (last covered-ranges)]
    (if (empty? covered-ranges) (conj ranges [from to])
        (conj
         (apply disj ranges covered-ranges)
         [(min from (first start-range)) (max to (second end-range))]))))

(defn- remove-range [ranges [from to]]
  (let [covered-ranges (filter #(and (<= from (second %)) (>= to (first %))) ranges)
        start-range (first covered-ranges)
        end-range (last covered-ranges)]
    (cond (empty? covered-ranges) ranges
          :else
          (apply
           conj
           (apply disj ranges covered-ranges)
           (filter
            #(<= (first %) (second %))
            [[(first start-range) (dec from)]
             [(inc to) (second end-range)]])))))

;; (defn- intersection [ranges [from to]]
;;   (let [covered-ranges (filter #(and (<= from (second %)) (>= to (first %))) ranges)
;;         start-range (first covered-ranges)
;;         end-range (last covered-ranges)]
;;     (if (empty? covered-ranges)
;;       []
;;       (conj
;;        (into (sorted-set) (butlast (rest covered-ranges)))
;;        [from (min to (second start-range))]
;;        [(max from (first end-range)) to]))))

(defn- part1
  ([input] (part1 input 2000000))
  ([input target]
   (let [coords (parse-input input)
         blocked-ranges (reduce
                         add-range
                         (sorted-set)
                         (keep
                          (partial get-blocked-xs target)
                          coords))
         net-blocked-ranges (reduce
                             remove-range
                             blocked-ranges
                             (keep (fn [{[x y] :beacon}] (when (= y target) [x x])) coords))]
     (reduce + (map (fn [[from to]]
                      (inc (- to from)))
                    net-blocked-ranges)))))

(defn- part2
  ([input] (part2 input 4000000))
  ([input max-search]
   (let [coords (parse-input input)]
     (first
      (keep
       (fn [y]
         (let [blocked-ranges (reduce
                               add-range
                               (sorted-set)
                               (keep
                                (partial get-blocked-xs y)
                                coords))
               free-x (first
                       (reduce
                        remove-range
                        (sorted-set [0 max-search])
                        blocked-ranges))]
           (println blocked-ranges)
           (when (some? free-x) (+ (* (first free-x) 4000000) y))))
       (range 0 max-search))))))

(def day15 [part1 part2])


;; (def simple-input
;;   "Sensor at x=2, y=18: closest beacon is at x=-2, y=15
;; Sensor at x=9, y=16: closest beacon is at x=10, y=16
;; Sensor at x=13, y=2: closest beacon is at x=15, y=3
;; Sensor at x=12, y=14: closest beacon is at x=10, y=16
;; Sensor at x=10, y=20: closest beacon is at x=10, y=16
;; Sensor at x=14, y=17: closest beacon is at x=10, y=16
;; Sensor at x=8, y=7: closest beacon is at x=2, y=10
;; Sensor at x=2, y=0: closest beacon is at x=2, y=10
;; Sensor at x=0, y=11: closest beacon is at x=2, y=10
;; Sensor at x=20, y=14: closest beacon is at x=25, y=17
;; Sensor at x=17, y=20: closest beacon is at x=21, y=22
;; Sensor at x=16, y=7: closest beacon is at x=15, y=3
;; Sensor at x=14, y=3: closest beacon is at x=15, y=3
;; Sensor at x=20, y=1: closest beacon is at x=15, y=3")

;; (part1 simple-input 10)
;; (part2 simple-input 20)