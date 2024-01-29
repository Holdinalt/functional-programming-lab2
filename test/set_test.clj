(ns set-test
  (:require [clojure.test :refer [deftest testing is]]
            [set :as set]))

(def filled (set/add "1" (set/add "2" (set/add "3" (set/add "4" (set/add "5" (set/add 2 (set/add 1 nil))))))))

(defn parseString [num]
  (cond
    (string? num) (read-string num)
    :else num))

(deftest linked-list-test
  (testing "add"
    (is (set/has "hello" (set/add "hello" nil)))

    (let [added (set/add "hello" (set/add "World" nil))]
      (is (=
           (set/has "hello" added)
           (set/has "World" added)
           (not (set/has "End" added))))))

  (testing "has"
    (is (=
         (set/has 3 nil)
         false))
    (is (=
         (set/has 10 filled)
         false))
    (is (=
         (set/has "3" filled)
         true)))

  (testing "delete"
    (is (=
         (set/delete 2 nil)
         nil))
    (is (not (set/has "3" (set/delete "3" filled)))))
  (testing "get-vector"
    (is (=
         (set/get-vector nil)
         []))
    (is (=
         (vec (sort #(> (parseString %1) (parseString %2)) (set/get-vector filled)))
         ["5" "4" "3" "2" 2 1 "1"]))
    (is (=
         (set/get-vector (set/add "hello" nil))
         ["hello"])))

  (testing "filter-set"
    (is (not (set/has "hello" (set/filter-set #(= "hello" %) filled))))

    (let [filtered (set/filter-set #(or (= "3" %) (= "2" %)) filled)]
      (is (=
           (set/has "3" filtered)
           (set/has "2" filtered)
           (not (set/has "5" filtered)))))

    (is (=
         (set/filter-set #(= "hello" %) nil)
         nil)))

  (testing "map-set"

    (let [listFilled (set/map-set #(or (= "3" %) (= "2" %)) filled)]
      (is
       (= (set/has true listFilled))
       (= (set/has false listFilled)))
      (let [list1 (set/delete true listFilled)]
        (is
         (= (not (set/has true list1)))
         (= (set/has false list1)))
        (let [list0 (set/get-vector (set/delete false list1))]
          (is (= [] list0)))))

    (is (=
         (set/map-set #(= "hello" %) nil)
         nil)))
  (testing "reduce-set"
    (is (=
         (set/reduce-set str "" filled)
         "3212415"))

    (is (=
         (set/reduce-set str "" nil)
         ""))))
