(ns aoc.day2
  (:require [aoc.utils :as u]))

;; Day 2 - Part one; Find safe 'Red-Nosed Reactor' reports

(defn- adjacent-levels [a b]
  (let [adjacent? (-> #{}
                      (into (some-> a (- 3) (range a)))
                      (into (some-> a inc (range (+ a 4)))))]
    (some-> b adjacent?)))

(defn- safe? [coll]
  (and (or (apply < coll) (apply > coll))
       (reduce adjacent-levels coll)))

(def part-one
  (comp (u/transmute (partial filter safe?) count)
        (partial merge {:reducer nil})))

;; Day 2 - Part two; Find tolerated reports with the Problem Dampener

(defn- problem-dampener [coll]
  (->> (for [i (-> coll count range)]
         (keep identity (assoc coll i nil)))
       (some safe?)))

(def part-two
  (comp (u/transmute (partial filter problem-dampener) count)
        (partial merge {:reducer nil})))
