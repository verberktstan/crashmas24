(ns aoc.day4
  (:require [aoc.utils :refer [transmute]]
            [clojure.edn :as edn]
            [clojure.string :as str]))

;; Day 4 - Part one;
(defn- rotate
  "Returns coll rotated n steps, doesn't wrap but injects nil when rotate would wrap."
  [n coll]
  (if (zero? n)
    (seq coll)
    (let [negative? (neg? n)
          cnt       (count coll)
          postfix   (when (pos? n) (repeat n nil))
          prefix    (when negative? (repeat (abs n) nil))]
      (as-> ((if negative? drop-last drop) (abs n) coll) coll
        (take cnt coll)
        (concat prefix coll postfix)))))

(def pivot* (memoize (partial apply mapv vector)))

(defn- pivot [lines]
  {:horizontal     (pmap seq lines)
   :vertical       (pivot* lines)
   :diagonal-right (->> lines (pmap rotate (range 4)) pivot*)
   :diagonal-left  (->> lines (pmap rotate (range 0 -4 -1)) pivot*)})

(def check* (memoize (comp seq (partial filter #{[\X \M \A \S] [\S \A \M \X]}))))

(defn- check [offset {:keys [horizontal vertical diagonal-right diagonal-left] :as m}]

  {:horizontal-matches (some->> horizontal
                                (map-indexed (fn [i coll] {:index (+ i offset) :coll (drop-last 1 (partition 4 1 coll))}))
                                (pmap #(update % :coll check*))
                                (filter :coll))
   :vertical-matches (some->> vertical check*)
   :diagonal-right-matches (some->> diagonal-right check*)
   :diagonal-left-matches (some->> diagonal-left check*)})

(defn- clean-horizontal-matches [{hm1 :horizontal-matches :as m1}
                                 {:keys [horizontal-matches] :as m2}]
  (-> (merge-with concat m1 (select-keys m2 [:vertical-matches :diagonal-left-matches :diagonal-right-matches]))
      (assoc :horizontal-matches (into (or hm1 {}) (pmap (juxt :index :coll) horizontal-matches)))))

(defn- sum-horizontal-matches [{:keys [horizontal-matches] :as m}]
  (assoc m :horizontal-matches
         (some->> horizontal-matches
                  vals
                  (mapcat identity)
                  seq)))

(def part-one
  (comp (transmute (partial partition 4 1)
                   (partial drop-last 1)
                   (partial pmap pivot)
                   (partial map-indexed check)
                   (partial reduce clean-horizontal-matches nil)
                   (partial sum-horizontal-matches)
                   (partial vals)
                   (partial pmap count))
        (partial merge {:parser nil :filename "resources/day4.txt"})))

(comment
  (let [lines ["MMMSXXMASM"
               "MSAMXMSMSA"
               "AMXSXMAAMM"
               "MSAMASMSMX"
               "XMASAMXAMM"
               "XXAMMXXAMA"
               "SMSMSASXSS"
               "SAXAMASAAA"
               "MAMMMXMMMM"
               "MXMXAXMASX"]]
    (->> lines
         (partition 4 1 lines)
         (drop-last 1)
         (pmap pivot)
         (map-indexed check)
         (reduce clean-horizontal-matches nil)
         sum-horizontal-matches
         vals
         (pmap count)
         (reduce +))))
