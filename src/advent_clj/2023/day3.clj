(ns advent-clj.2023.day3
  (:require [clojure.string :as str]))

(defn- parse-engine-schematic
  [input]
  (into (sorted-map)
        (apply concat
               (map-indexed (fn [x line]
                              (map-indexed (fn [y c]
                                             [[x y] c])
                                           ; append final . for easier parsing: number ends when a \. is found
                                           (str line \.)))
                            (str/split-lines input)))))
(def ascii-zero (int \0))
(def ascii-nine (int \9))

(defn- parse-digit
  [c]
  (let [ascii-c (int c)]
    (when (<= ascii-zero ascii-c ascii-nine)
      (- ascii-c ascii-zero))))

(defn- is-symbol?
  [c]
  (and c
       (not (#{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \.} c))))

(defn- get-adjacent-symbols
  [schematic y x]
  (let [neighbors [[y (dec x)]
                   [(dec y) (dec x)]
                   [(dec y) x]
                   [(dec y) (inc x)]
                   [y (inc x)]
                   [(inc y) (inc x)]
                   [(inc y) x]
                   [(inc y) (dec x)]]]
    (keep #(when (is-symbol? (get schematic %)) [% (get schematic %)]) neighbors)))

(defn- get-parts
  [{:keys [number symbols parts schematic]
    :or {number nil symbols (sorted-map) parts []}
    :as acc} [[y x] c]]
  (if-let [n (parse-digit c)]
    (assoc acc
           :symbols (into symbols (get-adjacent-symbols schematic y x))
           :number (if number
                     (+ (* 10 number) n)
                     n))

    (merge acc {:number nil
                :symbols (sorted-map)}
           (when (and number (seq symbols))
             {:parts (conj parts {:number number
                                  :symbols symbols})}))))

(defn- part1
  [input]
  (let [schematic (parse-engine-schematic input)]
    (reduce + (map :number (:parts (reduce get-parts
                                           {:schematic schematic}
                                           schematic))))))

(defn- part2
  [input]
  (let [schematic (parse-engine-schematic input)]
    (reduce +
            (map #(reduce * %)
                 (filter #(= 2 (count %))
                         (keep (fn [[_pos parts]]
                                 (seq (keep (fn [[_pos part]]
                                              (when (= \* (:symbol part))
                                                (:number part)))
                                            parts)))
                               (group-by first
                                         (mapcat (fn [{:keys [number symbols]}]
                                                   (map (fn [[[y x] s]] [[y x] {:symbol s :number number}])
                                                        symbols))
                                                 (:parts (reduce get-parts
                                                                 {:schematic schematic}
                                                                 schematic))))))))))

(def day3 [part1 part2])