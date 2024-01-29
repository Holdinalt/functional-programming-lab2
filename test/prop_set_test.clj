(ns prop-set-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.test.check.generators :as gen]
            [set :as set]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]))

(defn add-list [list collection]
  (cond
    (= (count list) 0) [nil]
    :else (reduce (fn [accum val] (set/add val accum)) collection list)))

(defn delete-list [list collection] (reduce (fn [accum val] (set/delete val accum)) collection list))

(defspec set-add 100
  (prop/for-all [v (gen/vector (gen/one-of [gen/large-integer gen/small-integer]))]
                (= (sort (set/get-vector (add-list v nil))) (sort (vec (set v))))))

(defspec set-delete 100
  (prop/for-all [v (gen/vector (gen/one-of [gen/boolean gen/large-integer gen/double gen/string-alphanumeric]))]
                (=
                 (vec (set (delete-list v (add-list v nil))))
                 [nil])))

(defspec set-reduce 100
  (prop/for-all [v (gen/vector (gen/one-of [gen/boolean gen/large-integer gen/double gen/string-alphanumeric]))]
                (=
                 (sort
                  (set/reduce-set
                   (fn [accum val] (conj accum (hash val)))
                   '()
                   (add-list v nil)))

                 (sort
                  (reduce
                   (fn [accum val] (conj accum (hash val)))
                   '()
                   (set v))))))