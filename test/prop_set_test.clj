(ns prop-set-test
  (:require [clojure.test.check.generators :as gen]
            [set :as set]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]))

(defn add-vector->set [vector collection]
  (cond
    (= (count vector) 0) [nil]
    :else (reduce (fn [accum val] (set/add val accum)) collection vector)))

(defn delete-vector-from-set [list collection] (reduce (fn [accum val] (set/delete val accum)) collection list))

(declare set-add monoid-ident set-delete set-reduce monoid-assoc)

(defspec set-add 100
  (prop/for-all [v (gen/vector (gen/one-of [gen/large-integer gen/small-integer]))]
                (= (sort (set/get-vector (add-vector->set v nil))) (sort (vec (set v))))))

(defspec set-delete 100
  (prop/for-all [v (gen/vector (gen/one-of [gen/boolean gen/large-integer gen/double gen/string-alphanumeric]))]
                (=
                 (vec (set (delete-vector-from-set v (add-vector->set v nil))))
                 [nil])))

(defspec set-reduce 100
  (prop/for-all [v (gen/vector (gen/one-of [gen/boolean gen/large-integer gen/double gen/string-alphanumeric]))]
                (=
                 (sort
                  (set/reduce-set
                   (fn [accum val] (conj accum (hash val)))
                   '()
                   (add-vector->set v nil)))
                 (sort
                  (reduce
                   (fn [accum val] (conj accum (hash val)))
                   '()
                   (set v))))))

(defspec monoid-assoc 100
  (prop/for-all [v1 (gen/not-empty (gen/vector (gen/one-of [gen/boolean gen/large-integer gen/double])))
                 v2 (gen/not-empty (gen/vector (gen/one-of [gen/boolean gen/large-integer gen/double])))
                 v3 (gen/not-empty (gen/vector (gen/one-of [gen/boolean gen/large-integer gen/double])))]
                (let [a (add-vector->set v1 nil)
                      b (add-vector->set v2 nil)
                      c (add-vector->set v3 nil)]
                  (set/equal
                   (set/conjoin (set/conjoin a b) c)
                   (set/conjoin a (set/conjoin b c))))))

(defspec monoid-ident 100
  (prop/for-all [v1 (gen/not-empty (gen/vector gen/large-integer))]
                (let [set1 (add-vector->set v1 nil)]
                  (set/equal
                   set1
                   (set/conjoin set1 [])))))
