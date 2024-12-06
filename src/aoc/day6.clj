(ns aoc.day6
  (:require [aoc.utils :refer [transmute]]))

;; Day 6 - Part one; Patrolling guard's path
(defn- parse-line [s]
  (let [parse-char {\. :void \# :obstruction \^ :guard/up \> :guard/right \v :guard/down \< :guard/left}]
    (->> s
         (map-indexed (fn [x char] {:pos/x x :whats/here (parse-char char)})))))

(defn- enrich-y-position [coll]
  (map-indexed
   (fn [y row] (map #(assoc % :pos/y y) row))
   coll))

(defn- create-lookup
  [coll]
  (reduce into {} (map (partial map (juxt (juxt :pos/x :pos/y) identity)) coll)))

(defn- project [{:pos/keys [x y] guard :whats/here}]
  (let [offset (get #:guard{:up [0 -1] :right [1 0] :down [0 1] :left [-1 0]} guard)]
    (mapv + [x y] offset)))

;; (project {:pos/x 1 :pos/y 2 :whats/here \^})
(def GUARD #:guard{:up :guard/right, :right :guard/down, :down :guard/left, :left :guard/up})

(defn- turn [{:whats/keys [here] :as m}]
  (update m :whats/here GUARD))

(defn- walk* [lookup]
  (let [position (juxt :pos/x :pos/y)
        guard (->> lookup vals (filter (comp GUARD :whats/here)) first)
        next-pos (project guard)
        whats-there (-> next-pos lookup :whats/here)]
    (case whats-there
      :guards/mark (-> lookup
                       (assoc-in [(position guard) :whats/here] :guards/mark)
                       (assoc next-pos (merge guard (zipmap [:pos/x :pos/y] next-pos))))
      :void (-> lookup
                (assoc-in [(position guard) :whats/here] :guards/mark)
                (assoc next-pos (merge guard (zipmap [:pos/x :pos/y] next-pos))))
      :obstruction (-> lookup (update (position guard) turn))
      (assoc lookup :guard/off-the-grid? true))))

(defn- walk [lookup]
  (loop [i 55, lookup' lookup]
    (if (or (:guard/off-the-grid? lookup') (zero? i))
      lookup'
      (recur (dec i) (walk* lookup')))))

(defn- guard-marks [lookup]
  (->> lookup vals (keep (comp (into #{:guards/mark} (keys GUARD)) :whats/here))
       (map #(and % 1))))

(def part-one
  (comp (transmute enrich-y-position create-lookup walk guard-marks)
        (partial merge {:parser parse-line :reducer +})))

(comment
  (part-one {:filename "resources/day6test.txt"}))
