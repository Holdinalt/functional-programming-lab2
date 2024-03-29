(ns set
  (:require [linked-list :as linked-list]))

(defn create-collection [n] (vec (repeat n nil)))
(defn get-hash [n] (hash n))
(defn get-bucket-num [new-value collection]
  (mod (get-hash new-value) (count collection)))

(defn update-bucket [bucket-num new-bucket collection]
  (assoc
   collection
   bucket-num
   new-bucket))

(defn get-bucket-by-val [new-value collection]
  (collection (get-bucket-num new-value collection)))

(defn add [value collection]
  (cond
    collection (update-bucket
                (get-bucket-num value collection)
                (->> collection (get-bucket-by-val value) (linked-list/add-entry value))
                collection)
    :else (recur value (create-collection 8))))

(defn restruct [collection]
  (cond
    collection (reduce
                (fn [acc bucket]
                  (linked-list/reduce-entry #(set/add %2 %1) acc bucket))
                (create-collection (count collection))
                collection)
    :else []))

(defn delete [value collection]
  (cond
    collection (update-bucket
                (get-bucket-num value collection)
                (linked-list/remove-entry (get-bucket-by-val value collection) value)
                collection)))

(defn has [value collection]
  (cond
    collection (->> collection
                    (get-bucket-by-val value)
                    (linked-list/contains value))
    :else false))

(defn get-vector [collection]
  (cond
    collection (into []
                     (->> collection
                          (map #(linked-list/get-vector %))
                          (reduce concat [])))

    :else []))

(defn conv-vec [par]
  (cond
    (nil? par) nil
    :else (vec par)))

(defn filter-set [filterFn collection]
  (cond
    collection (->> collection
                    (map #(linked-list/filter-entry % filterFn))
                    conv-vec)
    :else nil))

(defn map-set [fun collection]
  (cond
    collection (->> collection
                    (map #(linked-list/map-entry % fun))
                    restruct
                    conv-vec)

    :else nil))

(defn reduce-set [fun accum collection]
  (cond
    collection (reduce
                #(linked-list/reduce-entry fun %1 %2)
                accum
                collection)
    :else accum))

(defn len [collection]
  (reduce
   #(+ %1 (linked-list/count-entry %2))
   0
   collection))

(defn comparison [set1 set2]
  (set/reduce-set
   (fn [acc val]
     (and acc (set/has val set2)))
   true
   set1))

(defn equal [set1 set2]
  (cond
    (= (len set1) (len set2)) (comparison set1 set2)
    :else false))

(defn conjoin [set1 set2]
  (cond
    (nil? set2) set1
    (= 0 (count set2)) set1
    :else (reduce-set
           #(set/add %2 %1)
           set1
           set2)))