(ns aoc.day5
  (:require [aoc.utils :refer [transmute]]
            [clojure.edn :as edn]
            [clojure.string :as str]))

;; Day 5 - Part one;

(def part-one
  (comp (transmute identity
        (partial merge {:parser nil :reducer nil}))))