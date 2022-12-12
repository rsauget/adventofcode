(ns advent-clj.day10
  (:require [clojure.string :as str]))

(defn- run-cycles [cycle-count cycle-function instructions]
  (:results
   (reduce
    (fn [{:keys [stack x next-x results]} cycle-counter]
      (let [[line & rest] stack
            addx (when (and (some? line) (nil? next-x))
                   (second (re-matches #"^addx (.+)$" line)))]
        {:stack (if (some? addx) stack rest)
         :x (if (some? next-x) next-x x)
         :next-x (when (some? addx) (+ x (read-string addx)))

         :results (conj results (cycle-function cycle-counter x))}))
    {:stack instructions
     :x 1
     :next-x nil
     :results []}
    (range 1 (inc cycle-count)))))

(defn- part1
  [input]
  (let [signal-strength-cycles [20 60 100 140 180 220]]
    (reduce
     +
     (run-cycles
      (apply max signal-strength-cycles)
      (fn [cycle-counter x]
        (if (some #{cycle-counter} signal-strength-cycles)
          (* cycle-counter x)
          0))
      (str/split input #"\n")))))

(defn- part2
  [input]
  (let [height 6
        width 40
        lit-pixel "█"
        dark-pixel " "]
    (str
     "\n"
     (str/join
      "\n"
      (map
       (partial str/join "")
       (partition
        width
        (run-cycles
         (* height width)
         (fn [cycle-counter x]
           (if (<= (dec x)
                   (mod (dec cycle-counter) width)
                   (inc x))
             lit-pixel
             dark-pixel))
         (str/split input #"\n"))))))))

(def day10 [part1 part2])
