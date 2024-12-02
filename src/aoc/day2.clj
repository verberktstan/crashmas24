(ns aoc.day2
  (:require [aoc.utils :refer [transmute dissoc]]))

;; Day 2 - Part one; Find safe 'Red-Nosed Reactor' reports
(defn- adjacent-levels [a b]
  (let [adjacent? (reduce into #{} [(some-> a (- 3) (range a))
                                    (some-> a inc (range (+ a 4)))])]
    (some-> b adjacent?)))

(defn- safe? [coll]
  (and (or (apply < coll) (apply > coll) nil) (reduce adjacent-levels coll) 1))

(def part-one (transmute (partial keep safe?)))

;; Day 2 - Part two; Find tolerated reports with the Problem Dampener
(defn- problem-dampener [coll]
  (->> coll (map-indexed (dissoc coll)) (some safe?)))

(def part-two (transmute (partial keep problem-dampener)))
