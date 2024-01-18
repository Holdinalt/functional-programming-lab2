(ns set
  (:require [linked-list :as linked-list])
  )

(defn create-collection [n] (vec (repeat n nil)))
(defn get-hash [n] (hash n))
(defn get-bucket-num [new-value collection]
  (mod (get-hash new-value) (count collection))
  )

(defn update-bucket [bucket-num new-bucket collection]
  (assoc
    collection
    bucket-num
    new-bucket
    )
  )

(defn get-bucket-by-val [new-value collection]
  (collection (get-bucket-num new-value collection))
  )

(defn add [value collection]
  (cond
    collection (update-bucket
                 (get-bucket-num value collection)
                 (linked-list/add-entry (get-bucket-by-val value collection) value)
                 collection
                 )
    :else (recur value (create-collection 8))
    )
  )

(defn delete [value collection]
  (cond
    collection (update-bucket
                 (get-bucket-num value collection)
                 (linked-list/remove-entry (get-bucket-by-val value collection) value)
                 collection
                 )
    )
  )

(defn has [value collection]
  (cond
    collection (linked-list/contains (get-bucket-by-val value collection) value)
    :else false
    )
  )

(defn get-vector [collection]
  (cond
    collection (into [] (reduce
                          (fn [acc bucket]
                            (concat acc (linked-list/get-vector bucket))
                            )
                          []
                          collection
                          ))
    :else []
    )
  )

(defn filter-set [filterFn collection]
  (map #(linked-list/filter-entry % filterFn) collection)
  )

(defn map-set [fun collection]
  (map #(linked-list/map-entry % fun) collection)
  )

(defn reduce-set [fun accum collection]
  (reduce
    (fn [acc bucket]
      (linked-list/reduce-entry fun acc bucket)
      )
    accum
    collection)
  )