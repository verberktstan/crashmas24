(ns aoc.day4
  (:require [aoc.utils :refer [transmute]]))

;; Day 4 - Part one; XMAS word search
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

(defn- rotate2 [n coll]
  {:position/x (some-> n #{-1 1}) :coll (rotate n coll)})

(def pivot* (memoize (partial apply mapv vector)))

(defn pivot** [index args]
  (->> args
       (map :coll)
       pivot*
       (map-indexed
        (fn [i triplet]
          {:triplet triplet
           :position/x (->> args (keep :position/x) first (+ i))
           :position/y index}))))

(defn- diagonal
  ([range lines] (->> lines (pmap rotate range) pivot*))
  ([index range lines] (->> lines (pmap rotate2 range) (pivot** index))))

(defn- pivot
  "Returns pivoted sequences (like permutations) based on the lines; horizontal, vertial and diagonal."
  ([lines]
   {:horizontal (pmap seq lines)
    :vertical   (pivot* lines)
    :diagonal   (concat (diagonal (range 4) lines) (diagonal (range 0 -4 -1) lines))})
  ([index lines] ; This one just checks X's of diagonals of 3 characters
   {:diagonal1 (diagonal index (range 3) lines)
    :diagonal2 (diagonal index (range 0 -3 -1) lines)}))

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
                   vals)
        (partial merge {:parser nil :filename "resources/day4.txt"})))

;; Day 4 - Part two; X-MAS word search

(let [mas? #{[\M \A \S] [\S \A \M]}]
  (defn- x-mas? [coll]
    (and (->> coll (take 3) mas?) (->> coll (drop 3) mas?) 1)))

(let [key-by-position #(->> % (map (juxt (juxt :position/y :position/x) :triplet)) (into {}))]
  (def merge-triplets
    (partial
     reduce
     (fn [coll {:keys [diagonal1 diagonal2]}]
       (merge coll (merge-with concat (key-by-position diagonal1) (key-by-position diagonal2))))
     nil)))

(def part-two
  (comp (transmute
         (partial partition 3 1 nil)
         (partial map-indexed pivot)
         merge-triplets
         (partial keep (comp x-mas? val)))
        (partial merge {:parser nil :filename "resources/day4.txt"})))
