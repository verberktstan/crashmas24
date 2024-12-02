(ns aoc.day1-test
  (:require [clojure.test :refer [are deftest]]
            [aoc.day1 :as sut]))

(deftest day1
  (are [f result] (-> {:filename "resources/day1input.txt"} f (= result))
    sut/part-one 2000468
    sut/part-two 18567089))
