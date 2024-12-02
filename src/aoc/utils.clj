(ns aoc.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def read-lines (comp line-seq io/reader))

(defn transmute
  "Wrapper that reads, parses and finally reduces lines."
  [{:keys [filename parser reducer]
    :or {parser (comp (partial mapv edn/read-string)  #(str/split % #"\s+"))
         reducer +}} & fs]
  (-> filename string? assert)
  (let [f (some->> fs reverse (apply comp))]
    (cond->> filename
      :always read-lines
      parser  (map parser)
      f       f
      reducer (reduce reducer))))
