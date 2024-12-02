(ns aoc.day2
  (:require
   [aoc.utils :as u]))

;; TODO: Could be a simple transducer as well? Refactor that..
(defn- transmuter [& functions]
  (comp #(apply u/transmute % functions)
        (partial merge {:reducer nil})))

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
  (transmuter (partial filter safe?) count))

;; Day 2 - Part two; Tolerated reports with the Problem Dampener

(defn- problem-dampener [coll]
  (->> (for [i (-> coll count range)]
         (keep identity (assoc coll i nil)))
       (some safe?)))

(def part-two
  (transmuter (filter problem-dampener) count))
