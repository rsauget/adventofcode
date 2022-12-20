(ns advent-clj.day18
  (:require [clojure.pprint :as pprint]
            [clojure.string :as str]))

(defn- neighbors [[xmin xmax ymin ymax zmin zmax] cell]
  (filter (fn [[x y z]]
            (and (<= xmin x xmax)
                 (<= ymin y ymax)
                 (<= zmin z zmax)))
          (map #(into [] (map + cell %))
               [[-1 0 0] [1 0 0]
                [0 -1 0] [0 1 0]
                [0 0 -1] [0 0 1]])))

(defn find-path
  ([world cubes start] (find-path world cubes #{} [start]))
  ([world cubes visited todo]
   (if (empty? todo)
     visited
     (let [[cell & rest] todo]
       (if (visited cell)
         (recur world cubes visited rest)
         (recur world
                cubes
                (conj visited cell)
                (into rest (filter (comp not cubes)
                                   (neighbors world cell)))))))))

(defn- count-surface [cubes]
  (-
   (* 6 (count cubes))
   (count
    (filter #{1} (for [[x1 y1 z1 :as cube1] cubes
                       [x2 y2 z2 :as cube2] cubes
                       :when (not= cube1 cube2)]
                   (+ (abs (- x1 x2))
                      (abs (- y1 y2))
                      (abs (- z1 z2))))))))

(defn- part1 [input]
  (let [cubes (into #{} (map (fn [cube-input] (map read-string (str/split cube-input #",")))
                             (str/split-lines input)))]
    (count-surface cubes)))

(defn- part2 [input]
  (let [cubes (into #{} (map (fn [cube-input] (map read-string (str/split cube-input #",")))
                             (str/split-lines input)))
        xs (map first cubes)
        xmin (apply min xs)
        xmax (apply max xs)
        ys (map second cubes)
        ymin (apply min ys)
        ymax (apply max ys)
        zs (map #(nth % 2) cubes)
        zmin (apply min zs)
        zmax (apply max zs)
        outside-air (find-path [(dec xmin) (inc xmax)
                                (dec ymin) (inc ymax)
                                (dec zmin) (inc zmax)]
                               cubes
                               [(dec xmin) ymin zmin])]
    (- (count-surface cubes)
       (count-surface (for [x (range xmin (inc xmax))
                            y (range ymin (inc ymax))
                            z (range zmin (inc zmax))
                            :when (not (or (outside-air [x y z])
                                           (cubes [x y z])))]
                        [x y z])))))

(def day18 [part1 part2])

;; (def simple-input
;;   "2,2,2
;; 1,2,2
;; 3,2,2
;; 2,1,2
;; 2,3,2
;; 2,2,1
;; 2,2,3
;; 2,2,4
;; 2,2,6
;; 1,2,5
;; 3,2,5
;; 2,1,5
;; 2,3,5")

;; (pprint/pprint (part2 simple-input))