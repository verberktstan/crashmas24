(ns aoc.day3
  (:require [aoc.utils :refer [transmute]]
            [clojure.edn :as edn]
            [clojure.string :as str]))

(def day3 {:filename "day3.txt"})

;; Day 3 - Part one;
(def mul-re #"mul\(\d{1,3}\,\d{1,3}\)")
(def do-re #"do\(\)|don\'t\(\)")
(defn- parse-line [part-two?]
  (partial re-seq (re-pattern (str mul-re (when part-two? (str #"|" do-re))))))

(defn- mul [s]
  (->> s (re-matches #"mul\((\d{1,3})\,(\d{1,3})\)") rest (map edn/read-string) (apply *)))

(def part-one
  (comp (transmute (partial mapcat (partial map mul)))
        (constantly (merge day 3{:parser (parse-line nil)}))))


;; Day 3 - Part one; enable/disable mul based of do() and don't()
(def do-dont-mul
  (partial
   reduce
   (fn [{:keys [enabled?] :as m} match]
     (or (and (str/starts-with? match "d") (assoc m :enabled? (= match "do()")))
         (and enabled? (update m :result conj (mul match)))
         m))
   {:enabled? true}))

(def part-two
  (comp (transmute (partial mapcat #_mul2 (comp :result do-dont-mul)))
        (constantly (merge day3 {:parser (parse-line :part2)}))))
