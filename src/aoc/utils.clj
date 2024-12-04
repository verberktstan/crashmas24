(ns aoc.utils
  (:refer-clojure :rename {dissoc core-dissoc})
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
      [{:keys [filename test-coll parser reducer]
        :or   {parser  default-parser
               reducer +}}]
      ;; (-> filename string? assert)
      (cond->> (or test-coll (read-lines filename))
        parser  (map parser)
        pipe-fn pipe-fn
        reducer (reduce reducer)))))

(defn dissoc
  "clojure.core/dissoc on steroids"
  ([coll]
   (fn [i & _] (dissoc coll i)))
  ([coll i]
   (if (map? coll)
     (core-dissoc coll i)
     (->> (assoc (vec coll) i nil) (keep identity)))))
