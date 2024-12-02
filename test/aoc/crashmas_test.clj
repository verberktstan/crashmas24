(ns aoc.crashmas-test
  (:require [clojure.test :refer [are deftest]]
            [aoc.day1 :as day1]
            [aoc.day2 :as day2]))

(deftest crashmas-test
  (are [filename f result] (-> {:filename filename} f (= result))
    "resources/day1.txt" day1/part-one 2000468
    "resources/day1.txt" day1/part-two 18567089

    "resources/day2.txt" day2/part-one 359
    "resources/day2.txt" day2/part-two 418))
