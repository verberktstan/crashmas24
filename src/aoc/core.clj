(ns aoc.core
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.edn :as edn]))

(def read-lines (comp line-seq io/reader))

(def parse-line (comp (partial mapv edn/read-string)
                      (fn [s] (str/split s #"\s+"))))

;; TODO: Factor sort functionality out of this function..
(def pivot-sort (juxt (comp sort (partial map first))
                      (comp sort (partial map second))))

(def distance (comp abs -))

(defn similarity [coll]
  (let [[left right] (map frequencies coll)]
    (->> left
         (reduce-kv
          (fn [m location-id frequency]
            (let [similarity (some-> right (get location-id) (* location-id frequency))]
              (cond-> m
                similarity (update location-id (fnil + 0) similarity))))
          nil)
         vals
         (reduce +))))

(comment
  ;; Day 1 - Part 1 solution; Sum distances between left and right lists
  (->> "resources/day1input.txt" read-lines (map parse-line) pivot-sort (apply map distance) (reduce +)) ; => 2000468

  ;; Day 1 - Part 2 solution; Sum similarity between left and right lists
  (->> "resources/day1input.txt" read-lines (map parse-line) pivot-sort similarity) ; => 18567089
  )

