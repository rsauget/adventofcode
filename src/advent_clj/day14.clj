(ns advent-clj.day14
  (:require [clojure.string :as str]))

(defn- print-map [rocks floor-y path sand]
  (Thread/sleep 100)
  (let [min-x (apply min (map first rocks))
        max-x (apply max (map first rocks))
        min-y 0
        max-y (or floor-y (apply max (map second rocks)))]
    (print (str (char 27) "[2J"))
    (print (str (char 27) "[;H"))
    (println path)
    (println (str/join "\n"
                       (for [x (range min-x (inc max-x))]
                         (str/join ""
                                   (for [y (range min-y (inc max-y))]
                                     (cond
                                       (= sand [x y]) "O"
                                       (rocks [x y]) "#"
                                       (= floor-y y) "#"
                                       :else "."))))))))

(defn- parse-rock-path [rock-path]
  (map (comp
        #(into [] (map read-string %))
        rest)
       (re-seq #"(?<x>\d+),(?<y>\d+)" rock-path)))

(defn- add-rock-path [rocks rock-path]
  (if (nil? (second rock-path))
    rocks
    (recur (conj rocks (first rock-path))
           (let [[[x1 y1 :as rock1] [x2 y2 :as rock2]] rock-path]
             (cond
               (= rock1 rock2) (rest rock-path)
               (= x1 x2) (conj (rest rock-path) [x1 ((if (> y1 y2) dec inc) y1)])
               (= y1 y2) (conj (rest rock-path) [((if (> x1 x2) dec inc) x1) y1]))))))

(def sand-start [500 0])

(defn- fall-sand
  ([rocks] (fall-sand rocks nil '() sand-start))
  ([rocks floor-y] (fall-sand rocks floor-y '() sand-start))
  ([rocks floor-y path [x y]]
  ;;  (print-map rocks floor-y path [x y])
   (let [max-reachable-y (or (first
                              (keep (fn [[rock-x rock-y]]
                                      (when (and (> rock-y y) (= rock-x x))
                                        (dec rock-y))) rocks))
                             (when floor-y (dec floor-y)))]
     (cond
       (nil? max-reachable-y) rocks
       (> max-reachable-y y) (recur
                              rocks
                              floor-y
                              (into path (map #(vector x %) (range y max-reachable-y)))
                              [x max-reachable-y])
       (and (some? floor-y) (= y (dec floor-y))) (recur
                                                  (conj rocks [x y])
                                                  floor-y
                                                  (rest path)
                                                  (first path))
       (not (rocks [x (inc y)])) (recur
                                  rocks
                                  floor-y
                                  (conj path [x y])
                                  [x (inc y)])
       (not (rocks [(dec x) (inc y)])) (recur
                                        rocks
                                        floor-y
                                        (conj path [x y])
                                        [(dec x) (inc y)])
       (not (rocks [(inc x) (inc y)])) (recur
                                        rocks
                                        floor-y
                                        (conj path [x y])
                                        [(inc x) (inc y)])
       (= sand-start [x y]) (conj rocks [x y])
       :else (recur
              (conj rocks [x y])
              floor-y
              (rest path)
              (first path))))))

(defn- parse-rocks [input]
  (reduce
   add-rock-path
   (sorted-set)
   (map parse-rock-path (str/split input #"\n"))))

(defn- part1 [input]
  (let [rocks (parse-rocks input)]
    (-
     (count (fall-sand rocks))
     (count rocks))))

(defn- part2 [input]
  (let [rocks (parse-rocks input)
        floor-y (+ 2 (apply max (map second rocks)))]
    (-
     (count (fall-sand rocks floor-y))
     (count rocks))))

(def day14 [part1 part2])


(def simple-input
  "498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9")

(part1 simple-input)
;;(part2 simple-input)