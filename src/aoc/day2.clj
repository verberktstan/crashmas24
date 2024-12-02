(ns aoc.day2
  (:require
   [aoc.utils :as u]
   [clojure.string :as str]
   [clojure.edn :as edn]))

(let [props {:parser identity
             :reducer nil}]
  (defn- transmuter [& functions]
    (comp
      #(apply u/transmute % functions)
      (partial merge props))))

;; Day 2 - Part one

(def part-one
  (transmuter (partial map identity)))
