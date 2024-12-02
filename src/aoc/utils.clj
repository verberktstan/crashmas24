(ns aoc.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def read-lines (comp line-seq io/reader))

(def default-parser (comp (partial mapv edn/read-string) #(str/split % #"\s+")))

;; Wrapper that reads, parses and finally reduces lines.
(defn transmute
  [& functions]
  (let [pipe-fn (some->> functions reverse (apply comp))]
    (partial
     apply
     (fn transmutor
       [{:keys [filename parser reducer]
         :or   {parser  default-parser
                reducer +}}]
       (-> filename string? assert)
       (cond->> (read-lines filename)
         parser  (map parser)
         pipe-fn pipe-fn
         reducer (reduce reducer))))))
