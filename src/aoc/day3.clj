(ns aoc.day3
  (:require [aoc.utils :refer [transmute]]
            [clojure.edn :as edn]))

;; Day 3 - Part one;
(def parse-line (partial re-seq #"mul\(\d{1,3}\,\d{1,3}\)"))

(defn- mul [s]
  (->> s (re-matches #"mul\((\d{1,3})\,(\d{1,3})\)") rest (map edn/read-string) (apply *)))

(def part-one
  (comp (transmute (partial mapcat (partial map mul)))
        (partial merge {:parser parse-line})))
