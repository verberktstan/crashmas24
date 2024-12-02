(ns aoc.day2
  (:require [aoc.utils :as u]))

;; Day 2 - Part one; Find safe 'Red-Nosed Reactor' reports
(defn- adjacent-levels [a b]
  (let [adjacent? (-> #{}
                      (into (some-> a (- 3) (range a)))
                      (into (some-> a inc (range (+ a 4)))))]
    (some-> b adjacent?)))

(defn- safe? [coll]
  (and (or (apply < coll) (apply > coll) nil)
       (reduce adjacent-levels coll)
       1)) ; Return the count of 1 in case of a safe report

(def part-one
  (u/transmute (partial keep safe?)))

;; Day 2 - Part two; Find tolerated reports with the Problem Dampener
(defn- problem-dampener [coll]
  (->> (for [i (-> coll count range)]
         (keep identity (assoc coll i nil)))
       (some safe?)))

(def part-two
  (u/transmute (partial keep problem-dampener)))
