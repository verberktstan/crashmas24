(ns aoc.day1-test
  (:require
   [clojure.test :refer [deftest is]]
   [aoc.core :as sut]
   [utils :as u]))

(deftest part-one
  (is (= 2000468
        (u/rearseduce {:filename "resources/day1input.txt"
                        :parser sut/parse-line}
          #(->> %
            sut/pivot-sort
            (apply map sut/distance))))))
  
#_(deftest part-two
  (is (= 18567089
        (->> "resources/day1input.txt"
          sut/read-lines
          (map sut/parse-line)
          sut/pivot-sort
          sut/similarity))))