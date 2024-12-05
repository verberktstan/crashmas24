(ns aoc.day5
  (:require [aoc.utils :refer [transmute]]
            [clojure.edn :as edn]
            [clojure.string :as str]))

;; Day 5 - Part one;
(defn- parse-line [s]
  (when-let [coll (and (seq s) (str/split s #"\,|\|"))] 
    (assoc nil (if (str/includes? s "|") :rule :update) (map edn/read-string coll))))

(defn- build-rules [coll]
  (reduce
    (fn [m {[a b :as rule] :rule update-coll :update}] 
      (cond-> m
        rule (update-in [:pre a] (comp set into) #{b}) 
        rule (update-in [:post b] (comp set into) #{a}) 
        update-coll (update :updates conj update-coll)))
    nil
    coll))

(defn- check [{:keys [lookup seen invalid?] :as m} x]
  (if invalid?
    (reduced m)
    (cond-> (update m :seen (comp set into) #{x})
      (and seen (lookup x) (some seen (lookup x))) (assoc :invalid? true))))

(defn- valid? [{:keys [pre post]} coll]
  (->> [(reduce check {:lookup pre} coll)
        (reduce check {:lookup post} (reverse coll))]
    (keep :invalid?)
    seq
    not))

(defn- validate [{:keys [updates] :as m}]
  (update m :updates (partial filter (partial valid? m))))

(defn- median [coll]
  (when-let [vect (and (seq coll) (vec coll))]
    (vect (quot (count vect) 2))))

(def part-one
  (comp (transmute build-rules validate :updates (partial map median))
        (partial merge {:parser parse-line})))