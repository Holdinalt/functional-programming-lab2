(ns linked-list-test
  (:require [clojure.test :refer [deftest testing is]]
            [linked-list :as linked-list])
  )

(def one-list (linked-list/add-entry nil "hello"))
(def two-list (linked-list/add-entry one-list "World!"))

(deftest linked-list-test
  (testing "add-entry"
    (is (=
          one-list
          {:value "hello", :next nil}
          ))
    (is (=
          two-list
          {:value "hello", :next {:value "World!", :next nil}}
          ))
    (is (=
          (linked-list/add-entry one-list "hello")
          {:value "hello", :next nil}
          ))
    )

  (testing "remove-entry"
    (is (=
          (linked-list/remove-entry two-list "hello")
          {:value "World!", :next nil}
          )
      )
    (is (=
          (linked-list/remove-entry two-list "End")
          {:value "hello", :next {:value "World!", :next nil}}
          )
      )
    (is (=
          (linked-list/remove-entry one-list "hello")
          nil
          )
        )
    )

  (testing "find-entry"
    (is (=
          (linked-list/find-entry nil "hello")
          nil
          )
      )
    (is (=
          (linked-list/find-entry two-list "World!")
          {:value "World!", :next nil}
          )
        )
    (is (=
          (linked-list/find-entry two-list "never")
          nil
          )
        )
    )
  )


