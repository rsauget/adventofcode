(ns advent-clj.day21
  (:require [clojure.string :as str]))

(defn- make-get-value [monkey left right operator]
  {:operation (str monkey " = " left " " operator " " right)
   :get-value (fn [monkeys]
                (let [left-value (get-in monkeys [left :value])
                      right-value (get-in monkeys [right :value])]
                  (when (and (some? left-value) (some? right-value))
                    ((case operator
                       "+" +
                       "-" -
                       "*" *
                       "/" /) left-value right-value))))})

(defn- parse-operation [monkey input]
  (let [[left operator right] (rest (re-matches #"(\w+) ([*/+-]) (\w+)$" input))]
    [monkey (make-get-value monkey left right operator)]))

(defn- parse-monkeys [input]
  (into {} (map (fn [line]
                  (let [[monkey input] (rest (re-matches #"(\w+): (.+)$" line))
                        value (re-matches #"^\d+$" input)]
                    (if (some? value)
                      [monkey {:value (read-string value)}]
                      (parse-operation monkey input))))
                (str/split-lines input))))

(defn- eval-monkeys [monkeys]
  ;; (pprint/pprint (update-vals monkeys #(dissoc % :get-value)))
  (into {} (map (fn [[monkey {:keys [value get-value] :as monkey-info}]]
                  [monkey (cond (some? value) {:value value}
                                (some? (get-value monkeys)) {:value (get-value monkeys)}
                                :else monkey-info)])
                monkeys)))

(defn- parse-operation-reverse [monkey input]
  (let [[left operator right] (rest (re-matches #"(\w+) ([*/+-]) (\w+)$" input))]
    (if (= monkey "root")
      [[left {:get-value (fn [monkeys] (get-in monkeys [right :value]))}]
       [right {:get-value (fn [monkeys] (get-in monkeys [left :value]))}]]
      (case operator
        ; m = l + r
        ; l = m - r
        ; r = m - l
        "+" [[left (make-get-value left monkey right "-")]
             [right (make-get-value right monkey left "-")]]
        ; m = l - r
        ; l = m + r
        ; r = l - m
        "-" [[left (make-get-value left monkey right "+")]
             [right (make-get-value right left monkey "-")]]
        ; m = l * r
        ; l = m / r
        ; r = m / l
        "*" [[left (make-get-value left monkey right "/")]
             [right (make-get-value right monkey left "/")]]
        ; m = l / r
        ; l = m * r
        ; r = l / m
        "/" [[left (make-get-value left monkey right "*")]
             [right (make-get-value right left monkey "/")]]))))

(defn- parse-monkeys-part2 [input]
  (update-vals
   (group-by first
             (mapcat (fn [line]
                       (let [[monkey input] (rest (re-matches #"(\w+): (.+)$" line))
                             value (re-matches #"^\d+$" input)]
                         (cond (= monkey "humn") []
                               (some? value) [[monkey {:value (read-string value)}]]
                               :else (conj (parse-operation-reverse monkey input)
                                           (parse-operation monkey input)))))
                     (str/split-lines input)))
   (comp (fn [rules]
           (let [value (first (keep :value rules))]
             (if (some? value)
               {:value value}
               {:operation (keep :operation rules)
                :get-value (fn [monkeys] (first (keep #((:get-value %) monkeys) rules)))})))
         (partial map second))))

(defn- part1 [input]
  (get-in (first
           (drop-while #(nil? (get-in % ["root" :value]))
                       (iterate eval-monkeys (parse-monkeys input))))
          ["root" :value]))

(defn- part2 [input]
  (get-in (first
           (drop-while #(nil? (get-in % ["humn" :value]))
                       (iterate eval-monkeys (parse-monkeys-part2 input))))
          ["humn" :value]))

(def day21 [part1 part2])
