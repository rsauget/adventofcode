(ns advent-clj.2022.day6)

(defn- find-marker [marker-length input]
  (first
   (keep-indexed
    (fn
      [index value]
      (when
       (= marker-length (count (set value)))
        (+ index marker-length)))
    (partition
     marker-length
     1
     input))))

(defn- part1
  [input]
  (find-marker 4 input))

(defn- part2
  [input]
  (find-marker 14 input))

(def day6 [part1 part2])
