(ns linked-list-test
  (:require [clojure.test :refer [deftest testing is]]
            [linked-list :as linked-list]))

(def one-list (linked-list/add-entry nil "hello"))
(def two-list (linked-list/add-entry one-list "World!"))

(deftest linked-list-test
  (testing "add-entry"
    (is (linked-list/contains one-list "hello"))
    (is (linked-list/contains two-list "hello"))
    (is (linked-list/contains two-list "World!"))
    (is (not (linked-list/contains one-list "No!"))))
  (testing "remove-entry"
    (is
      (not (linked-list/contains (linked-list/remove-entry two-list "hello") "hello"))
        )

    (is (linked-list/contains (linked-list/remove-entry two-list "hello") "World!")
        )

    (is (linked-list/contains (linked-list/remove-entry two-list nil) "hello")
        )

    (is (=
         (linked-list/remove-entry nil "hello")
         nil))

    (is (=
         (linked-list/remove-entry one-list "hello")
         nil)))

  (testing "find-entry"
    (is (=
         (linked-list/find-entry nil "hello")
         nil))

    (is (linked-list/contains (linked-list/find-entry two-list "World!") "World!"))

    (is (= (linked-list/find-entry two-list "never") nil)))
  (is (linked-list/contains (linked-list/find-entry one-list "World!") nil))

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
         (linked-list/get-vector (linked-list/filter-entry two-list #(= "hello" %)))
         ["hello"]))

    (is (=
         (linked-list/get-vector (linked-list/filter-entry one-list #(= "hello" %)))
         ["hello"]))

    (is (=
         (linked-list/filter-entry nil #(= "hello" %))
         nil)))

  (testing "map-entry"
    (is (let [list (linked-list/map-entry (linked-list/add-entry two-list "End") #(= "hello" %))]
          (=
           (linked-list/contains list false)
           (linked-list/contains list true))))

    (is (let [list (linked-list/map-entry one-list #(= "hello" %))]
          (=
           (not (linked-list/contains list false))
           (linked-list/contains list true)))

        (is (=
             (linked-list/map-entry nil #(= "hello" %))
             nil))))

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


