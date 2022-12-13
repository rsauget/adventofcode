(ns advent-clj.day10
  (:require [clojure.string :as str]))

(defn- run-cycle
  [{:keys [stack x next-x results cycle-counter cycle-function]
    :or {cycle-counter 1
         x 1
         next-x nil
         results []}}]
  (let [[line & rest] stack
        addx (when (and (some? line) (nil? next-x))
               (second (re-matches #"^addx (.+)$" line)))]
    {:cycle-counter (inc cycle-counter)
     :cycle-function cycle-function
     :stack (if (some? addx) stack rest)
     :x (if (some? next-x) next-x x)
     :next-x (when (some? addx) (+ x (read-string addx)))

     :results (conj results (cycle-function cycle-counter x))}))

(defn- part1
  [input]
  (let [signal-strength-cycles [20 60 100 140 180 220]]
    (reduce
     +
     (:results
      (nth
       (iterate
        run-cycle
        {:stack (str/split input #"\n")
         :cycle-function (fn [cycle-counter x]
                           (if (some #{cycle-counter} signal-strength-cycles)
                             (* cycle-counter x)
                             0))})
       (apply max signal-strength-cycles))))))

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
        (:results
         (nth
          (iterate
           run-cycle
           {:stack (str/split input #"\n")
            :cycle-function (fn [cycle-counter x]
                              (if (<= (dec x)
                                      (mod (dec cycle-counter) width)
                                      (inc x))
                                lit-pixel
                                dark-pixel))})
          (* height width)))))))))

(def day10 [part1 part2])
