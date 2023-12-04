(ns advent-clj.2023.day2
  (:require [clojure.string :as str]))

(defn- parse-score-item
  [[_score-item count color]]
  [(keyword color) (read-string count)])

(defn- parse-score
  [score]
  (into (sorted-map)
        (map parse-score-item
             (re-seq #"(\d+) (red|green|blue)" score))))

(defn- get-power
  [turns]
  (let [red (apply max (keep :red turns))
        green (apply max (keep :green turns))
        blue (apply max (keep :blue turns))]
    (* red green blue)))

(defn- parse-game
  [input]
  (let [game-id (read-string (second (re-find #"Game (\d+)" input)))
        turns (map parse-score (str/split (second (str/split input #": ")) #"; "))]
    {:id game-id
     :turns turns
     :power (get-power turns)}))

(def available-cubes {:red 12
                      :green 13
                      :blue 14})

(defn- is-game-possible
  [game]
  (every? #(and
            (<= (:red % 0) (:red available-cubes))
            (<= (:green % 0) (:green available-cubes))
            (<= (:blue % 0) (:blue available-cubes))) (:turns game)))

(defn- part1
  [input]
  (reduce + (map :id
                 (filter is-game-possible
                         (map parse-game
                              (str/split-lines input))))))

(defn- part2
  [input]
  (reduce + (map :power (map parse-game
                             (str/split-lines input)))))

(def day2 [part1 part2])