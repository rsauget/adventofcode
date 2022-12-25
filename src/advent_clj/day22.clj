(ns advent-clj.day22
  (:require [clojure.string :as str]
            [clojure.pprint :as pprint]))

(defn- parse-world
  ([input] (parse-world input 1 (sorted-map)))
  ([[line & rest-lines] y world]
   (if (empty? line)
     [world
      (map #(if (re-matches #"\d+" %)
              (read-string %)
              %)
           (re-seq #"\d+|[LR]" (first rest-lines)))]
     (recur rest-lines
            (inc y)
            (into world (keep-indexed (fn [x c]
                                        (case c
                                          \. [[y (inc x)] true]
                                          \# [[y (inc x)] false]
                                          nil))
                                      line))))))

(def right [0 1])
(def left [0 -1])
(def up [-1 0])
(def down [1 0])

(defn- turn-left [direction]
  (cond (= direction right) up
        (= direction up) left
        (= direction left) down
        (= direction down) right))

(defn- turn-right [direction]
  (cond (= direction right) down
        (= direction down) left
        (= direction left) up
        (= direction up) right))

(defn- get-next-position [world [y x :as position] direction]
  (let [next-position (into [] (map + position direction))
        next-position-wrapped (if (some? (get world next-position))
                                next-position
                                (first (cond
                                         (= direction right) (first (filter #(= y (first (first %))) world))
                                         (= direction left) (last (filter #(= y (first (first %))) world))
                                         (= direction up) (last (filter #(= x (second (first %))) world))
                                         (= direction down) (first (filter #(= x (second (first %))) world)))))]
    ;; (pprint/pprint {:position position
    ;;                 :direction direction
    ;;                 :next-position next-position
    ;;                 :next-position-wrapped next-position-wrapped})
    (if (get world next-position-wrapped)
      next-position-wrapped
      position)))

(defn- apply-moves [{:keys [world position direction moves] :as state}]
  (if (nil? moves)
    state
    (let [[move & remaining-moves] moves]
      ;; (pprint/pprint {:position position
      ;;                 :direction direction
      ;;                 :moves moves})
      (cond (= move "L") (recur (assoc state
                                       :direction (turn-left direction)
                                       :moves remaining-moves))
            (= move "R") (recur (assoc state
                                       :direction (turn-right direction)
                                       :moves remaining-moves))
            (= move 0) (recur (assoc state
                                     :moves remaining-moves))
            :else (recur (assoc state
                                :moves (cons (dec move) remaining-moves)
                                :position (get-next-position world position direction)))))))

(defn- part1 [input]
  (let [[world moves] (parse-world (str/split-lines input))
        {:keys [position direction]} (apply-moves
                                      {:world world
                                       :position (first (keys world))
                                       :direction right
                                       :moves moves})]
    (+ (* 1000 (first position))
       (* 4 (second position))
       (cond (= direction right) 0
             (= direction up) 3
             (= direction left) 2
             (= direction down) 1))))

(defn- part2 [input]
  input)

(def day22 [part1 part2])

(def simple-input
  "        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5")

(time (pprint/pprint (part1 simple-input)))