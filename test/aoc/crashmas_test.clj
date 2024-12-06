(ns aoc.crashmas-test
  (:require [clojure.test :refer [are deftest is]]
            [aoc.day1 :as day1]
            [aoc.day2 :as day2]
            [aoc.day3 :as day3]
            [aoc.day5 :as day5]))

(deftest crashmas-test
  (are [filename f result] (-> {:filename filename} f (= result))
    "resources/day1.txt" day1/part-one 2000468
    "resources/day1.txt" day1/part-two 18567089

    "resources/day2.txt" day2/part-one 359
    "resources/day2.txt" day2/part-two 418

    "resources/day3.txt" day3/part-one 189527826
    "resources/day3.txt" day3/part-two 69247082

    "resources/day5test.txt" day5/part-one 143
    "resources/day5.txt" day5/part-one 5275
    "resources/day5test.txt" day5/part-two 123
    "resources/day5.txt" day5/part-two 6191))
