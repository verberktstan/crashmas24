(ns aoc.core
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.edn :as edn]))

(def read-lines (comp line-seq io/reader))

(def parse-line (comp (partial mapv edn/read-string)
                      #(str/split % #"\s+")))

;; TODO: Factor sort functionality out of this function..
(def pivot-sort (juxt (comp sort (partial map first))
                      (comp sort (partial map second))))

(def distance (comp abs -))

(defn- location-lookup [right]
  (fn [m location-id frequency]
    (let [similarity (some-> location-id right (* location-id frequency))]
      (cond-> m
        similarity (update location-id (fnil + 0) similarity)))))

(defn similarity [coll]
  (let [[left right] (map frequencies coll)]
    (->> left
         (reduce-kv (location-lookup right) nil)
         vals
         (reduce +))))