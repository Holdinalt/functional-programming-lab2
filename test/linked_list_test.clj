(ns linked-list-test
  (:require [clojure.test :refer [deftest testing is]]
            [linked-list :as linked-list]))

(def one-list (linked-list/add-entry nil "hello"))
(def two-list (linked-list/add-entry one-list "World!"))

(deftest linked-list-test
  (testing "add-entry"
    (is (=
         one-list
         {:value "hello", :next nil}))
    (is (=
         two-list
         {:value "hello", :next {:value "World!", :next nil}}))
    (is (=
         (linked-list/add-entry one-list "hello")
         {:value "hello", :next nil})))

  (testing "remove-entry"
    (is (=
         (linked-list/remove-entry two-list "hello")
         {:value "World!", :next nil}))

    (is (=
         (linked-list/remove-entry two-list "End")
         {:value "hello", :next {:value "World!", :next nil}}))

    (is (=
         (linked-list/remove-entry one-list "hello")
         nil)))

  (testing "find-entry"
    (is (=
         (linked-list/find-entry nil "hello")
         nil))

    (is (=
         (linked-list/find-entry two-list "World!")
         {:value "World!", :next nil}))

    (is (=
         (linked-list/find-entry two-list "never")
         nil)))

  (testing "get-vector"
    (is (=
         (linked-list/get-vector two-list)
         ["World!" "hello"]))

    (is (=
         (linked-list/get-vector one-list)
         ["hello"]))

    (is (=
         (linked-list/get-vector nil)
         [])))

  (testing "filter-entry"
    (is (=
         (linked-list/filter-entry two-list #(= "hello" %))
         {:value "hello", :next nil}))

    (is (=
         (linked-list/filter-entry one-list #(= "hello" %))
         {:value "hello", :next nil}))

    (is (=
         (linked-list/filter-entry nil #(= "hello" %))
         nil)))

  (testing "map-entry"
    (is (=
         (linked-list/map-entry (linked-list/add-entry two-list "End") #(= "hello" %))
         {:value false, :next {:value true, :next nil}}))

    (is (=
         (linked-list/map-entry one-list #(= "hello" %))
         {:value true, :next nil}))

    (is (=
         (linked-list/map-entry nil #(= "hello" %))
         nil)))

  (testing "reduce-entry"
    (is (=
         (linked-list/reduce-entry str "" two-list)
         "helloWorld!"))

    (is (=
         (linked-list/reduce-entry str "" one-list)
         "hello"))

    (is (=
         (linked-list/reduce-entry str "" nil)
         ""))))


