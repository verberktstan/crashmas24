(ns aoc.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def ^:private read-lines (comp line-seq io/reader))

(def ^:private default-parser (comp (partial mapv edn/read-string) #(str/split % #"\s+")))

;; Wrapper that reads, parses and finally reduces lines.
(defn transmute
  [& functions]
  (let [pipe-fn (some->> functions reverse (apply comp))]
    (fn transmutor
      [{:keys [filename parser reducer]
        :or   {parser  default-parser
               reducer +}}]
      (-> filename string? assert)
      (cond->> (read-lines filename)
        parser  (map parser)
        pipe-fn pipe-fn
        reducer (reduce reducer)))))

(defn ohne [f k]
  (-> f fn? assert)
  (-> k #{:reducer :parser} assert)
  (comp f (partial merge {k nil})))
