(ns aoc.utils
 (:require [clojure.java.io :as io]))

(def read-lines (comp line-seq io/reader))

(defn rearseduce
  "Wrapper that reads, parses and finally reduces lines."
  [{:keys [filename parser reducer] 
    :or {parser identity, reducer +}} & fs]
  (let [f (some->> fs reverse (apply comp))]
    (cond->> filename
      :always read-lines
      parser  (map parser)
      f       f
      reducer (reduce reducer))))