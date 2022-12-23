(ns advent-clj.day19
  (:require [clojure.pprint :as pprint]
            [clojure.string :as str]))

(def blueprint-matcher #"Blueprint (\d+): Each ore robot costs (\d+) ore. Each clay robot costs (\d+) ore. Each obsidian robot costs (\d+) ore and (\d+) clay. Each geode robot costs (\d+) ore and (\d+) obsidian.")

(defn- harvest
  ([state] (harvest state 1))
  ([{:keys [minutes
            ore ore-robots
            clay clay-robots
            obsidian obsidian-robots
            geodes geode-robots] :as state} duration]
   (assoc state
          :minutes (- minutes duration)
          :ore (+ ore (* ore-robots duration))
          :clay (+ clay (* clay-robots duration))
          :obsidian (+ obsidian (* obsidian-robots duration))
          :geodes (+ geodes (* geode-robots duration)))))

(defn- ore-robot-builder [^long ore-robot-cost-ore]
  (fn [{:keys [minutes ^long ore-robots ^long ore] :as state}]
    (cond (>= ore ore-robot-cost-ore)
          (update (harvest (update state
                                   :ore - ore-robot-cost-ore))
                  :ore-robots inc)

          (>= (+ ore (* ore-robots (dec minutes))) ore-robot-cost-ore)
          (recur (harvest state (Math/ceilDiv (- ore-robot-cost-ore ore) ore-robots)))

          :else nil)))

(defn- clay-robot-builder [^long clay-robot-cost-ore]
  (fn [{:keys [minutes ^long ore-robots ^long ore] :as state}]
    (cond (>= ore clay-robot-cost-ore)
          (update (harvest (update state
                                   :ore - clay-robot-cost-ore))
                  :clay-robots inc)

          (>= (+ ore (* ore-robots (dec minutes))) clay-robot-cost-ore)
          (recur (harvest state (Math/ceilDiv (- clay-robot-cost-ore ore) ore-robots)))

          :else nil)))

(defn- obisidian-robot-builder [^long obsidian-robot-cost-ore ^long obsidian-robot-cost-clay]
  (fn [{:keys [minutes ^long clay-robots ^long clay ^long ore-robots ^long ore] :as state}]
    (cond (and (>= ore obsidian-robot-cost-ore)
               (>= clay obsidian-robot-cost-clay))
          (update (harvest (update (update state
                                           :ore - obsidian-robot-cost-ore)
                                   :clay - obsidian-robot-cost-clay))
                  :obsidian-robots inc)

          (and (>= (+ ore (* ore-robots (dec minutes))) obsidian-robot-cost-ore)
               (>= (+ clay (* clay-robots (dec minutes))) obsidian-robot-cost-clay))
          (recur (harvest state (max (Math/ceilDiv (- obsidian-robot-cost-ore ore) ore-robots)
                                     (Math/ceilDiv (- obsidian-robot-cost-clay clay) clay-robots))))

          :else nil)))

(defn- geode-robot-builder [^long geode-robot-cost-ore ^long geode-robot-cost-obsidian]
  (fn [{:keys [minutes ^long obsidian-robots ^long obsidian ^long ore-robots ^long ore] :as state}]
    (cond (and (>= ore geode-robot-cost-ore)
               (>= obsidian geode-robot-cost-obsidian))
          (update (harvest (update (update state
                                           :ore - geode-robot-cost-ore)
                                   :obsidian - geode-robot-cost-obsidian))
                  :geode-robots inc)

          (and (>= (+ ore (* ore-robots (dec minutes))) geode-robot-cost-ore)
               (>= (+ obsidian (* obsidian-robots (dec minutes))) geode-robot-cost-obsidian))

          (recur (harvest state (max (Math/ceilDiv (- geode-robot-cost-ore ore) ore-robots)
                                     (Math/ceilDiv (- geode-robot-cost-obsidian obsidian) obsidian-robots))))

          :else nil)))

(defn- parse-blueprint [input]
  (let [[id
         ore-robot-cost-ore
         clay-robot-cost-ore
         obsidian-robot-cost-ore
         obsidian-robot-cost-clay
         geode-robot-cost-ore
         geode-robot-cost-obsidian] (map read-string
                                         (rest (re-matches blueprint-matcher input)))]
    {:id id
     :max-ore-cost (max ore-robot-cost-ore
                        clay-robot-cost-ore
                        obsidian-robot-cost-ore
                        geode-robot-cost-ore)
     :max-clay-cost obsidian-robot-cost-clay
     :max-obsidian-cost geode-robot-cost-obsidian
     :build-ore-robot (ore-robot-builder ore-robot-cost-ore)
     :build-clay-robot (clay-robot-builder clay-robot-cost-ore)
     :build-obsidian-robot (obisidian-robot-builder obsidian-robot-cost-ore obsidian-robot-cost-clay)
     :build-geode-robot (geode-robot-builder geode-robot-cost-ore geode-robot-cost-obsidian)}))

(defn- possible-next-states [{:keys [build-ore-robot build-clay-robot build-obsidian-robot build-geode-robot
                                     max-ore-cost max-clay-cost max-obsidian-cost]}
                             {:keys [ore-robots clay-robots obsidian-robots] :as state}]
  (or (seq (remove nil? (map #(% state)
                             (remove nil? [build-geode-robot
                                           (when (< obsidian-robots max-obsidian-cost) build-obsidian-robot)
                                           (when (< ore-robots max-ore-cost) build-ore-robot)
                                           (when (< clay-robots max-clay-cost) build-clay-robot)]))))
      [(harvest state (:minutes state))]))

(defn- geode-potential [{:keys [minutes geodes geode-robots]}]
  (if (= minutes 0) geodes
      (recur {:minutes (dec minutes)
              :geodes (+ geodes geode-robots)
              :geode-robots (inc geode-robots)})))

(defn- run-simulation
  ([blueprint minutes] (run-simulation blueprint 0 [{:minutes minutes
                                                     :ore 0
                                                     :clay 0
                                                     :obsidian 0
                                                     :geodes 0
                                                     :ore-robots 1
                                                     :clay-robots 0
                                                     :obsidian-robots 0
                                                     :geode-robots 0}]))
  ([blueprint max-geodes [state & todo]]
  ;;  (println "blueprint" (:id blueprint))
  ;;  (pprint/pprint state)
   (cond
     (nil? state) max-geodes
     (= (:minutes state) 0) (recur blueprint (max max-geodes (:geodes state)) todo)
     :else (recur blueprint max-geodes (into todo (remove #(<= (geode-potential %) max-geodes)
                                                          (possible-next-states blueprint state)))))))

(defn- part1 [input]
  (reduce + (pmap #(let [blueprint (parse-blueprint %)
                         geode-count (run-simulation blueprint 24)]
                    ;;  (println (:id blueprint) geode-count)
                     (* (:id blueprint)
                        geode-count))
                  (str/split-lines input))))

(defn- part2 [input]
  (reduce * (pmap #(let [blueprint (parse-blueprint %)
                         geode-count (run-simulation blueprint 32)]
                    ;;  (println (:id blueprint) geode-count)
                     geode-count)
                  (take 3 (str/split-lines input)))))

(def day19 [part1 part2])
