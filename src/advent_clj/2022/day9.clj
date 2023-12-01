(ns advent-clj.2022.day9
  (:require [clojure.string :as str]))

(defn- update-head
  [[x y] direction]
  (case direction
    "U" [(inc x) y]
    "D" [(dec x) y]
    "L" [x (dec y)]
    "R" [x (inc y)]))

(defn- update-tail
  [[x y] [head-x head-y]]
  (let [distance-x (abs (- x head-x))
        distance-y (abs (- y head-y))]
    (cond
      (and (<= distance-x 1) (<= distance-y 1)) [x y]
      (= distance-x 0) (if (> y head-y)
                         [x (dec y)]
                         [x (inc y)])
      (= distance-y 0) (if (> x head-x)
                         [(dec x) y]
                         [(inc x) y])
      :else [(if (> x head-x) (dec x) (inc x))
             (if (> y head-y) (dec y) (inc y))])))

(defn- apply-move-step
  [direction {:keys [knots visited]}]
  (let [head (first knots)
        others (rest knots)
        new-knots (reduce
                   (fn [knots tail]
                     (conj
                      knots
                      (update-tail tail (last knots))))
                   [(update-head head direction)]
                   others)]
    {:knots new-knots
     :visited (conj visited (last new-knots))}))

(defn- apply-move
  [state move]
  (let [[direction distance] (str/split move #" ")]
    (nth
     (iterate (partial apply-move-step direction) state)
     (read-string distance))))

(defn- part1
  [input]
  (count
   (:visited
    (reduce
     apply-move
     {:knots [[0 0] [0 0]]
      :visited #{[0 0]}}
     (str/split-lines input)))))

(defn- part2
  [input]
  (count
   (:visited
    (reduce
     apply-move
     {:knots (repeat 10 [0 0])
      :visited (set [[0 0]])}
     (str/split-lines input)))))

(def day9 [part1 part2])
