(ns aoc.day2
  (:require
   [aoc.utils :as u]
   [clojure.string :as str]
   [clojure.edn :as edn]))

(let [props {:reducer nil}]
  (defn- transmuter [& functions]
    (comp
     #(apply u/transmute % functions)
     (partial merge props))))

;; Day 2 - Part one

(defn- safe? [coll]
  (and (or (apply < coll) (apply > coll))
       (reduce
        (fn [a b]
          (let [next? (set (concat (some-> a (- 3) (range a))
                                   (some-> a inc (range (+ a 4)))))]
            (some-> (and next? b) next?)))
        coll)))

(def part-one
  (transmuter (partial filter safe?) count))
