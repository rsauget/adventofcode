(ns advent-clj.day7
  (:require [advent-clj.utils :refer [day]]
            [clojure.string :as str]))

(defn- read-tree
  [lines]
  (:tree
   (reduce
    (fn
      [acc line]
      (let [{:keys [tree cwd]} acc
            cd (get (re-matches #"^\$ cd (.+)$" line) 1)
            [file-size-str file-name] (rest (re-matches #"^(\d+) (.+)$" line))
            file-size (when (some? file-size-str) (read-string file-size-str))
            dir-name (get (re-matches #"^dir (.+)$" line) 1)]
        (cond
          (some? cd) (merge
                      acc
                      (if (= cd "..")
                        {:cwd (vec (butlast cwd))}
                        {:cwd (conj cwd cd)
                         :tree (update-in tree (conj cwd cd) #(if (nil? %) (sorted-map) %))}))
          (some? dir-name) (assoc acc :tree (assoc-in
                                             tree
                                             (conj cwd dir-name)
                                             (sorted-map)))
          (some? file-name) (assoc acc :tree (assoc-in
                                              tree
                                              (conj cwd file-name)
                                              file-size))
          :else acc)))
    {:tree (sorted-map) :cwd []}
    lines)))

(defn- get-folder-size [folder]
  (reduce
   +
   (map
    (fn
      [[_key value]]
      (if
       (number? value) value
       (:size value)))
    (dissoc folder :size))))

(defn- add-folders-sizes
  [tree]
  (let [tree-with-sizes (update-vals
                         tree
                         (fn
                           [branch]
                          ;;  (println branch)
                           (if (number? branch)
                             branch
                             (let [branch-with-sizes (add-folders-sizes branch)]
                               (assoc
                                branch-with-sizes
                                :size
                                (get-folder-size branch-with-sizes))))))]
    (assoc
     tree-with-sizes
     :size
     (get-folder-size tree-with-sizes))))



(defn- flatten-folders
  ([tree] (flatten-folders [tree] []))
  ([trees folders]
   (if (empty? trees)
     folders
     (let [tree (first trees)
           [key value] (first tree)]
       (recur
        (filter
         seq
         (conj
          (rest trees)
          (dissoc tree key :size)
          (when (not (number? value)) value)))
        (if (number? value)
          folders
          (conj
           folders
           [key (:size value)])))))))

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
     (flatten-folders
      (add-folders-sizes
       (read-tree (str/split input #"\n"))))))))

(defn- part2
  [input]
  (let [tree (add-folders-sizes
              (read-tree (str/split input #"\n")))
        folders (flatten-folders tree)
        used-disk-size (get-in tree ["/" :size])
        free-disk-size (- total-disk-size used-disk-size)
        to-remove-disk-size  (- required-disk-size free-disk-size)]
    (first
     (sort
      (filter
       #(>= % to-remove-disk-size)
       (map
        second
        folders))))))

(defn day7
  []
  (day 7 part1 part2))

(day7)

(def test-case
  "$ cd /
$ ls
dir x
dir y
1 a
2 b
$ cd x
$ ls
3 aa
$ cd ..
$ cd y
$ ls
dir w
5 ab")


(defn- read-tree-2
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
          (some? cd) (merge
                      acc
                      (if (= cd "..")
                        {:cwd (vec (butlast cwd))}
                        {:cwd (conj cwd cd)
                         :folders (update-in
                                   folders
                                   (str/join "/" (conj cwd cd))
                                   #(if (nil? %) 0 %))}))
          (some? dir-name) (update-in
                            acc
                            [:folders (str/join "/" cwd)]
                            #(if (nil? %) 0 %))
          (some? file-name) (update-in
                             acc
                             [:folders (str/join "/" cwd)]
                             #(+ file-size (if (nil? %) 0 %)))
          :else acc)))
    {:cwd [] :folders (sorted-map)}
    lines)))

;; (pprint/pprint
;;  (flatten-folders
;;   (add-folders-sizes
;; (read-tree-2
;;  (str/split
;;   test-case #"\n"))
;;)))

;; (day7)
