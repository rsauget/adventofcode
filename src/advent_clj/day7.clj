(ns advent-clj.day7
  (:require [clojure.string :as str]))

(defn- infer-folders
  [lines]
  (:folders
   (reduce
    (fn
      [{:keys [cwd folders] :as acc} line]
      (let [cd (second (re-matches #"^\$ cd (.+)$" line))
            [file-size-str file-name] (rest (re-matches #"^(\d+) (.+)$" line))
            file-size (when (some? file-size-str) (read-string file-size-str))
            dir-name (second (re-matches #"^dir (.+)$" line))]
        (cond
          (= cd "..") {:cwd (into [] (butlast cwd))
                       :folders folders}
          (some? cd) {:cwd (conj cwd (str/join (last cwd) cd))
                      :folders (update
                                folders
                                (str/join "/" (conj cwd cd))
                                #(or % 0))}
          (some? dir-name) {:cwd cwd
                            :folders
                            (update
                             folders
                             (str/join "/" (conj cwd dir-name))
                             #(or % 0))}
          (some? file-name) {:cwd cwd
                             :folders (merge-with
                                       +
                                       folders
                                       (into {} (map (fn [dir] [dir file-size]) cwd)))}
          :else acc)))
    {:cwd [] :folders (sorted-map)}
    lines)))

(def ^:private total-disk-size 70000000)
(def ^:private required-disk-size 30000000)

(defn- part1
  [input]
  (reduce
   +
   (filter
    #(<= % 100000)
    (map
     second
     (infer-folders (str/split-lines input))))))

(defn- part2
  [input]
  (let [folders (infer-folders (str/split-lines input))
        used-disk-size (get folders "/")
        free-disk-size (- total-disk-size used-disk-size)
        to-remove-disk-size  (- required-disk-size free-disk-size)]
    (first
     (sort
      (filter
       #(<= to-remove-disk-size %)
       (map
        second
        folders))))))

(def day7 [part1 part2])
