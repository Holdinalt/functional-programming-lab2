(ns set-test
  (:require [clojure.test :refer [deftest testing is]]
            [set :as set])
  )

(def filled (set/add (set/add (set/add (set/add (set/add (set/add (set/add nil 1) 2) "5") "4") "3") "2") "1"))

(deftest linked-list-test
  (testing "add"
    (is (=
         (set/add nil "hello")
         [nil nil nil {:value "hello", :next nil} nil nil nil nil]
          ))
    (is (=
          filled
          [nil
           {:value "3", :next nil}
           nil
           {:value "2", :next nil}
           {:value 1, :next {:value 2, :next {:value "4", :next nil}}}
           nil
           {:value "1", :next nil}
           {:value "5", :next nil}]
          ))
    )
  (testing "has"
    (is (=
          (set/has nil 3)
          false
          ))
    (is (=
          (set/has filled 10)
          false
          ))
    (is (=
          (set/has filled "3")
          true
          ))

    )
  (testing "delete"
    (is (=
          (set/delete nil 2)
          nil
          ))
    (is (=
          (set/delete filled "3")
          [nil nil nil
           {:value "2", :next nil}
           {:value 1, :next {:value 2, :next {:value "4", :next nil}}}
           nil
           {:value "1", :next nil}
           {:value "5", :next nil}]
          ))
    (is (=
          (set/delete filled 1)
          [nil
           {:value "3", :next nil}
           nil
           {:value "2", :next nil}
           {:value 2, :next {:value "4", :next nil}}
           nil
           {:value "1", :next nil}
           {:value "5", :next nil}]
          ))
    (is (=
          (set/delete filled "4")
          [nil
           {:value "3", :next nil}
           nil
           {:value "2", :next nil}
           {:value 1, :next {:value 2, :next nil}}
           nil
           {:value "1", :next nil}
           {:value "5", :next nil}]
          ))
    (is (=
          (set/delete filled 10)
          [nil
           {:value "3", :next nil}
           nil
           {:value "2", :next nil}
           {:value 1, :next {:value 2, :next {:value "4", :next nil}}}
           nil
           {:value "1", :next nil}
           {:value "5", :next nil}]
          ))
    )
  (testing "get-vector"
    (is (=
          (set/get-vector nil)
          []
          ))
    (is (=
          (set/get-vector filled)
          ["3" "2" "4" 2 1 "1" "5"]
          ))
    (is (=
          (set/get-vector (set/add nil "hello"))
          ["hello"]
          ))
    )
  )
