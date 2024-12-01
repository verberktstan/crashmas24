(ns aoc.utils
 (:require [clojure.java.io :as io]))

(def read-lines (comp line-seq io/reader))

(defn rearseduce
  "Wrapper that reads, parses and finally reduces lines."
  [{:keys [filename parser reducer] 
    :or {parser identity, reducer +}} & fs]
  (let [f (apply comp fs)]
    (->> filename
      read-lines
      (map parser)
      f
      (reduce reducer))))