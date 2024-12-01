(ns aoc.day1-test
  (:require
   [clojure.test :refer [deftest is]]
   [aoc.core :as sut]))

(deftest part-one
  (is (= 2000468
        (->> "resources/day1input.txt"
          sut/read-lines
          (map sut/parse-line)
          sut/pivot-sort
          (apply map sut/distance)
          (reduce +)))))
  
(comment
  ;; Day 1 - Part 1 solution; Sum distances between left and right lists
  (->> "resources/day1input.txt" read-lines (map parse-line) pivot-sort (apply map distance) (reduce +)) ; => 2000468

  ;; Day 1 - Part 2 solution; Sum similarity between left and right lists
  (->> "resources/day1input.txt" read-lines (map parse-line) pivot-sort similarity) ; => 18567089
  )

