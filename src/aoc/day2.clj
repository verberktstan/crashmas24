(ns aoc.day2
  (:require [aoc.utils :refer [transmute dissoc]]))

(def day2 (constantly {:filename "day2.txt"}))

;; Day 2 - Part one; Find safe 'Red-Nosed Reactor' reports
(defn- adjacent-levels [a b]
  (let [adjacent? (reduce into #{} [(some-> a (- 3) (range a))
                                    (some-> a inc (range (+ a 4)))])]
    (some-> b adjacent?)))

(defn- safe? [coll]
  (and (or (apply < coll) (apply > coll) nil) (reduce adjacent-levels coll) 1))

(def part-one (comp (transmute (partial keep safe?)) day2))

;; Day 2 - Part two; Find tolerated reports with the Problem Dampener
(defn- problem-dampener [coll]
  (->> coll (map-indexed (dissoc coll)) (some safe?)))

(def part-two (comp (transmute (partial keep problem-dampener)) day2))
