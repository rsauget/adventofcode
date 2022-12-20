(ns advent-clj.day15
  (:require [clojure.string :as str]))

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
   (str/split-lines input)))

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
      (filter
       some?
       (pmap
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
            (when (some? free-x) (+ (* (first free-x) 4000000) y))))
        (range 0 max-search)))))))

(def day15 [part1 part2])
