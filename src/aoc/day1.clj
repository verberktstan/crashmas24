(ns aoc.day1
  (:require
   [aoc.utils :as u]))

(defn- transmuter [& functions]
  (fn [props] (apply u/transmute props functions)))

;; Day 1 - Part one / pivot, sort, distance

(def pivot (juxt (partial map first) (partial map second)))

(def distance (comp abs -))

(def part-one
  (transmuter pivot (partial map sort) (partial apply map distance)))

;; Day 1 - Part two / pivot, similarity

(defn- location-lookup [right]
  (fn [m location-id frequency]
    (let [similarity (some-> location-id right (* location-id frequency))]
      (cond-> m
        similarity (update location-id (fnil + 0) similarity)))))

(defn- similarity [coll]
  (let [[left right] (map frequencies coll)]
    (->> left
         (reduce-kv (location-lookup right) nil)
         vals)))

(def part-two
  (transmuter pivot similarity))
