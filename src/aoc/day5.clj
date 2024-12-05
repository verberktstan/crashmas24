(ns aoc.day5
  (:require [aoc.utils :refer [transmute]]
            [clojure.edn :as edn]
            [clojure.string :as str]))

;; Day 5 - Part one;
(defn parse-line [s]
  (when-let [coll (and (seq s) (str/split s #"\,|\|"))] 
    (assoc nil (if (str/includes? s "|") :rule :update) (map edn/read-string coll))))

(defn build-rules [coll]
  (reduce
    (fn [m {[a b :as rule] :rule update-coll :update}] 
      (cond-> m
        rule (update-in [:pre a] (comp set into) #{b}) 
        rule (update-in [:post b] (comp set into) #{a}) 
        update-coll (update :updates conj update-coll)))
    nil
    coll))

(def part-one
  (comp (transmute build-rules)
        (partial merge {:parser parse-line :reducer nil})))