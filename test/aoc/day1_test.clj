(ns aoc.day1-test
  (:require
   [clojure.test :refer [deftest is]]
   [aoc.core :as sut]
   [aoc.utils :as u]))

(def props {:filename "resources/day1input.txt"})

(deftest part-one
  (is (= 2000468 (sut/part-one props))))
  
(deftest part-two
  (is (= 1856708 #_9 (sut/part-two props))))
