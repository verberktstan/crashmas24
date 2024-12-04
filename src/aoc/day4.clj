(ns aoc.day4
  (:require [aoc.utils :refer [transmute]]))

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
(defn- diagonal [range lines] (->> lines (pmap rotate range) pivot*))

(defn- pivot
  "Returns pivoted sequences (like permutations) based on the lines; horizontal, vertial and diagonal."
  [lines]
  {:horizontal (pmap seq lines)
   :vertical   (pivot* lines)
   :diagonal   (concat (diagonal (range 4) lines) (diagonal (range 0 -4 -1) lines))})

(def check* (memoize (comp seq (partial filter #{[\X \M \A \S] [\S \A \M \X]}))))

;; TODO: Reduce instead of map?
(defn- check [offset {:keys [horizontal vertical diagonal]}]
  {:horizontal-matches (some->> horizontal
                                (map-indexed (fn [i coll] {:index (+ i offset) :coll (partition 4 1 nil coll)}))
                                (pmap #(update % :coll check*))
                                (filter :coll)
                                (pmap (juxt :index (comp count :coll)))
                                (into {}))
   :vertical-matches (->> vertical check* count)
   :diagonal-matches (->> diagonal check* count)})

(defn- clean-horizontal-matches [{hm1 :horizontal-matches :as m1}
                                 {hm2 :horizontal-matches :as m2}]
  (-> (merge-with + m1 (select-keys m2 [:vertical-matches :diagonal-matches]))
      (assoc :horizontal-matches (merge hm1 hm2))))

(defn- sum-horizontal-matches [{:keys [horizontal-matches] :as m}]
  (assoc m :horizontal-matches
         (some->> horizontal-matches
                  vals
                  (reduce +))))

(def part-one
  (comp (transmute identity
                   (partial partition 4 1 nil)
                   (partial pmap pivot)
                   (partial map-indexed check)
                   (partial reduce clean-horizontal-matches nil)
                   (partial sum-horizontal-matches)
                   (partial vals))
        (partial merge {:parser nil :filename "resources/day4.txt"})))

;; (part-one nil) 
