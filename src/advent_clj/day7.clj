(ns advent-clj.day7
  (:require [advent-clj.utils :refer [day]]
            [clojure.string :as str]))

(defn- add-file-size
  [folders cwd file-size]
  (:folders
   (reduce
    (fn
      [{:keys [cwd folders]} dir]
      {:cwd (conj cwd dir)
       :folders (update
                 folders
                 (str/join "/" (conj cwd dir))
                 #(+ file-size (if (nil? %) 0 %)))})
    {:cwd [] :folders folders}
    cwd)))

(defn- infer-folders
  [lines]
  (:folders
   (reduce
    (fn
      [acc line]
      (let [{:keys [cwd folders]} acc
            cd (get (re-matches #"^\$ cd (.+)$" line) 1)
            [file-size-str file-name] (rest (re-matches #"^(\d+) (.+)$" line))
            file-size (when (some? file-size-str) (read-string file-size-str))
            dir-name (get (re-matches #"^dir (.+)$" line) 1)]
        (cond
          (= cd "..") {:cwd (vec (butlast cwd))
                       :folders folders}
          (some? cd) {:cwd (conj cwd cd)
                      :folders (update
                                folders
                                (str/join "/" (conj cwd cd))
                                #(if (nil? %) 0 %))}
          (some? dir-name) {:cwd cwd
                            :folders
                            (update
                             folders
                             (str/join "/" (conj cwd dir-name))
                             #(if (nil? %) 0 %))}
          (some? file-name) {:cwd cwd
                             :folders (add-file-size
                                       folders
                                       cwd
                                       file-size)}
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
     (infer-folders (str/split input #"\n"))))))

(defn- part2
  [input]
  (let [folders (infer-folders (str/split input #"\n"))
        used-disk-size (get folders "/")
        free-disk-size (- total-disk-size used-disk-size)
        to-remove-disk-size  (- required-disk-size free-disk-size)]
    (first
     (sort
      (filter
       (partial <= to-remove-disk-size)
       (map
        second
        folders))))))

(defn day7
  []
  (day 7 part1 part2))
