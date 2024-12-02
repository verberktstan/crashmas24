(ns aoc.day1
  (:require
   [aoc.utils :as u]
   [clojure.string :as str]
   [clojure.edn :as edn]))

(def parse-line (comp (partial mapv edn/read-string)
                      #(str/split % #"\s+")))

(def pivot (juxt (partial map first) (partial map second)))

(def distance (comp abs -))

(defn part-one [props]
  (u/transmute
    (assoc props :parser parse-line :reducer +)
    pivot
    (partial map sort)
    (partial apply map distance)))

(defn- location-lookup [right]
  (fn [m location-id frequency]
    (let [similarity (some-> location-id right (* location-id frequency))]
      (cond-> m
        similarity (update location-id (fnil + 0) similarity)))))

(defn- similarity [coll]
  (let [[left right] (map frequencies coll)]
    (->> left
         (reduce-kv (location-lookup right) nil)
         vals)))

(defn part-two [props]
  (u/transmute
    (assoc props :parser parse-line :reducer +)
    pivot
    similarity))
