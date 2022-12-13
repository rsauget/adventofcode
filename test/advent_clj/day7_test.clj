(ns advent-clj.day7-test
  (:require [advent-clj.day7 :refer [day7]]
            [advent-clj.utils :refer [slurp-input]]
            [clojure.test :refer [deftest is testing]]))

(def part1 (first day7))
(def part2 (second day7))

(def real-input (slurp-input 7))

(def simple-input
  "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(deftest day7-test
  (testing "Part 1 - simple"
    (is (=
         95437
         (part1 simple-input))))

  (testing "Part 1 - real"
    (is (=
         1642503
         (part1 real-input))))

  (testing "Part 2 - simple"
    (is (=
         24933642
         (part2 simple-input))))
  
  (testing "Part 2 - real"
    (is (=
         6999588
         (part2 real-input)))))
