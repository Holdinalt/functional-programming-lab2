(ns linked_list_test
  (:require [clojure.test :refer [deftest testing is]]
            [linked-list :as linked-list])
  )

(deftest linked-list-test
  (testing "add"
    (is (=
          (linked-list/add-entry nil 1 "hello")
          #linked_list.Entry{:key 1, :value "hello", :next nil}
          ))

    (def listed (linked-list/add-entry nil 1 "hello"))
    (constantly (linked-list/add-entry (list) 1 "hello2"))

    (is (=
          (constantly listed)
          #linked_list.Entry{:key 1, :value "hello", :next nil}
          ))
    )
  )


