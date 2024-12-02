(ns aoc.day1-test
  (:require [clojure.test :refer [are deftest]]
            [aoc.day1 :as day1]))

(deftest day1
  (are [f result] (-> {:filename "resources/day1input.txt"} f (= result))
    day1/part-one 2000468
    day1/part-two 18567089))
