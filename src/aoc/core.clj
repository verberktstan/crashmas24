(ns aoc.core
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.edn :as edn]))

(def read-lines (comp line-seq io/reader))

(def parse-line (comp (partial mapv edn/read-string)
                      (fn [s] (str/split s #"\s+"))))

(def pivot-sort (juxt (comp sort (partial map first))
                      (comp sort (partial map second))))

(def distance (comp abs -))

(comment
  ;; Day 1 - Part 1 solution; Sum distances between left and right lists
  (->> "resources/day1input.txt" read-lines (map parse-line) pivot-sort (apply map distance) (reduce +)) ; => 2000468
  )
