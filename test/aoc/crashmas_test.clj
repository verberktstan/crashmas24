(ns aoc.crashmas-test
  (:require [clojure.test :refer [are deftest]]
            [aoc.day1 :as day1]
            [aoc.day2 :as day2]))

(deftest day1
  (are [f result] (-> {:filename "resources/day1input.txt"} f (= result))
    day1/part-one 2000468
    day1/part-two 18567089))

(deftest day2
  (are [f result] (-> {:filename "resources/day2input.txt"} f (= result))
    day2/part-one 359
    day2/part-two 418))
