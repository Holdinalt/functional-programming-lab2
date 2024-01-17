(ns set
  (:require [linked-list :as linked-list])
  )

(defn create-collection [n] (vec (repeat n nil)))
(defn get-hash [n] (hash n))
(defn get-bucket-num [collection new-value]
  (mod (get-hash new-value) (count collection))
  )

(defn update-bucket [collection bucket-num new-bucket]
  (assoc
    collection
    bucket-num
    new-bucket
    )
  )

(defn get-bucket-by-val [collection new-value]
  (collection (get-bucket-num collection new-value))
  )

(defn add [collection value]
  (cond
    collection (update-bucket
                 collection
                 (get-bucket-num collection value)
                 (linked-list/add-entry (get-bucket-by-val collection value) value)
                 )
    :else (recur (create-collection 8) value)
    )
  )

(defn delete [collection value]
  (cond
    collection (update-bucket
                 collection
                 (get-bucket-num collection value)
                 (linked-list/remove-entry (get-bucket-by-val collection value) value)
                 )
    )
  )

(defn has [collection value]
  (cond
    collection (linked-list/contains (get-bucket-by-val collection value) value)
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