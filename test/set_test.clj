(ns set-test
  (:require [clojure.test :refer [deftest testing is]]
            [set :as set])
  )

(def filled (set/add "1" (set/add "2" (set/add "3" (set/add "4" (set/add "5" (set/add 2 (set/add 1 nil))))))))
(def filledNums (set/add 1 (set/add 2 (set/add 3 (set/add 4 (set/add 5 (set/add 2 (set/add 1 nil))))))))

(deftest linked-list-test
  (testing "add"
    (is (=
         (set/add "hello" nil )
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
          (set/has 3 nil)
          false
          ))
    (is (=
          (set/has 10 filled)
          false
          ))
    (is (=
          (set/has "3" filled)
          true
          ))

    )
  (testing "delete"
    (is (=
          (set/delete 2 nil)
          nil
          ))
    (is (=
          (set/delete "3" filled)
          [nil nil nil
           {:value "2", :next nil}
           {:value 1, :next {:value 2, :next {:value "4", :next nil}}}
           nil
           {:value "1", :next nil}
           {:value "5", :next nil}]
          ))
    (is (=
          (set/delete 1 filled)
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
          (set/delete "4" filled)
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
          (set/delete 10 filled)
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
          (set/get-vector (set/add "hello" nil))
          ["hello"]
          ))
    )
  )
