(ns advent-clj.day12
  (:require [clojure.string :as str]))

;; Input parsing
(defn- find-value-coordinates [value world]
  (apply concat (map-indexed
                 (fn [y row]

                   (map first
                        (filter (comp #{value} second)
                                (map-indexed (fn [x cell] [[y x] cell]) row))))
                 world)))

(defn- parse-world [input]
  (let [letter-world (map #(vec %) (str/split input #"\n"))]
    [(into [] (map (fn [row]
                     (into [] (map #(- (int (case %
                                              \S \a
                                              \E \z
                                              %))
                                       (int \a))
                                   row)))
                   letter-world))
     (first (find-value-coordinates \S letter-world))
     (first (find-value-coordinates \E letter-world))]))

;; Pathfinding
(defn min-by [f coll]
  (when (seq coll)
    (reduce (fn [min value]
              (if (> (f min) (f value))
                value
                min))
            coll)))

(defn neighbors
  [cell height width]
  (filter (fn [[y x]]
            (and (< -1 y height)
                 (< -1 x width)))
          (map #(into [] (map + cell %))
               [[-1 0] [1 0] [0 -1] [0 1]])))

(defn path-cost [neighbor]
  (inc (get neighbor :cost -1)))

(defn astar [world start ends reachable?]
  (let [height (count world)
        width (count (first world))]
    (loop [steps 0
           visited (into [] (repeat height (into [] (repeat width nil))))
           todo (sorted-set [0 start])]
      (let [found-route (first (keep #(get-in visited %) ends))]
        (if (or found-route (empty? todo))
          found-route
          (let [[_ cell :as item] (first todo)
                rest (disj todo item)
                candidates (neighbors cell height width)
                predecessors (filter #(reachable? world % cell) candidates)
                best-candidate (min-by :cost
                                       (keep #(get-in visited %)
                                             predecessors))
                newcost (path-cost best-candidate)
                oldcost (:cost (get-in visited cell))]
            (if (and oldcost (>= newcost oldcost))
              (recur (inc steps) visited rest)
              (recur (inc steps)
                     (assoc-in visited cell
                               {:cost newcost
                                :steps (conj (:steps best-candidate []) cell)})
                     (into rest
                           (map
                            (fn [successor]
                              [(inc newcost) successor])
                            (filter #(reachable? world cell %) candidates)))))))))))

;; Domain logic

(defn reachable?
  [world from-cell to-cell]
  (let [from-cell-height (get-in world from-cell)
        to-cell-height (get-in world to-cell)]
    (or
     (= from-cell-height \S)
     (= to-cell-height \E)
     (and (number? from-cell-height)
          (number? to-cell-height)
          (<= to-cell-height (inc from-cell-height))))))

(defn- part1 [input]
  (let [[world start end] (parse-world input)]
    (:cost (astar world end [start] (fn [world from-cell to-cell]
                                      (reachable? world to-cell from-cell))))))

(defn- part2 [input]
  (let [[world _start end] (parse-world input)
        starts (find-value-coordinates 0 world)]
    (:cost (astar world end starts (fn [world from-cell to-cell]
                                     (reachable? world to-cell from-cell))))))

(def day12 [part1 part2])
