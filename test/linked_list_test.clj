(ns linked-list-test
  (:require [clojure.test :refer [deftest testing is]]
            [linked-list :as linked-list]))

(def one-list (linked-list/add-entry "hello" nil))
(def two-list (linked-list/add-entry "World!" one-list))

(deftest linked-list-test
  (testing "add-entry"
    (is (linked-list/contains "hello" one-list))
    (is (linked-list/contains "hello" two-list))
    (is (linked-list/contains "World!" two-list))
    (is (not (linked-list/contains "No!" one-list))))
  (testing "remove-entry"
    (is
     (not (linked-list/contains "hello" (linked-list/remove-entry two-list "hello"))))

    (is (linked-list/contains "World!" (linked-list/remove-entry two-list "hello")))

    (is (linked-list/contains "hello" (linked-list/remove-entry two-list nil)))

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

    (is (linked-list/contains "World!" (linked-list/find-entry two-list "World!")))

    (is (= (linked-list/find-entry two-list "never") nil)))
  (is (linked-list/contains nil (linked-list/find-entry one-list "World!")))

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
    (is (let [list (linked-list/map-entry (linked-list/add-entry "End" two-list) #(= "hello" %))]
          (=
           (linked-list/contains false list)
           (linked-list/contains true list))))

    (is (let [list (linked-list/map-entry one-list #(= "hello" %))]
          (=
           (not (linked-list/contains false list))
           (linked-list/contains true list)))

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
         "")))

  ;(testing "equal"
  ;  (is (=
  ;
  ;        true
  ;        ))
  ;  )
  )



