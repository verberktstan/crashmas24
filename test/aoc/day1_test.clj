(ns aoc.day1-test
  (:require
   [clojure.test :refer [deftest is]]
   [aoc.core :as sut]
   [aoc.utils :as u]))

(deftest part-one
  (is (= 2000468
        (sut/distancex))))
  
#_(deftest part-two
  (is (= 18567089
        (->> "resources/day1input.txt"
          sut/read-lines
          (map sut/parse-line)
          sut/pivot-sort
          sut/similarity))))