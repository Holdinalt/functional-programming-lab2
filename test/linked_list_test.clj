(ns linked-list-test
  (:require [clojure.test :refer [deftest testing is]]
            [linked-list :as linked-list])
  )

(deftest linked-list-test
  (testing "add-entry"
    (is (=
          (linked-list/add-entry nil 1 "hello")
          {:key 1, :value "hello", :next nil}
          ))
    (is (=
          (linked-list/add-entry (linked-list/add-entry nil 1 "hello") 2 "World!")
          {:key 1, :value "hello", :next {:key 2, :value "World!", :next nil}}
          ))
    (is (=
          (linked-list/add-entry (linked-list/add-entry nil 1 "hello") 1 "End")
          {:key 1, :value "End", :next nil}
          ))
    )
  )


