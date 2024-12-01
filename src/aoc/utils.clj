(ns aoc.utils
 (:require [clojure.java.io :as io]))

(def read-lines (comp line-seq io/reader))

(defn rearseduce
  "Wrapper that reads, parses and finally reduces lnes."
  [{:keys [filename parser reducer] 
     :or {parser identity, reducer +}} f]
  (->> filename
    read-lines
    (map parser)
    f
    (reduce reducer)))