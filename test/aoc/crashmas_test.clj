(ns aoc.crashmas-test
  (:require [clojure.test :refer [are deftest]]
            [aoc.day1 :as day1]
            [aoc.day2 :as day2]
            [aoc.day3 :as day3]))

(deftest crashmas-test
  (are [f result] (-> (f) (= result))
    day1/part-one 2000468
    day1/part-two 18567089

    day2/part-one 359
    day2/part-two 418

    day3/part-one 189527826
    day3/part-two 69247082))
