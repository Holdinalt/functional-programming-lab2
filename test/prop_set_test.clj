(ns prop-set-test
  (:require [clojure.test :refer [deftest testing is]]
            [set :as set]))

(defn getRandomNumByFunc [fun]
  (let [ran (rand-int 1000)]
    (cond
      (fun (hash ran)) ran
      :else (getRandomNumByFunc fun))))

(def raw
  {:0 [(getRandomNumByFunc #(= 0 (mod % 8))) (getRandomNumByFunc #(= 0 (mod % 8)))]
   :1 [(getRandomNumByFunc #(= 1 (mod % 8))) (getRandomNumByFunc #(= 1 (mod % 8)))]
   :2 [(getRandomNumByFunc #(= 2 (mod % 8))) (getRandomNumByFunc #(= 2 (mod % 8)))]
   :3 [(getRandomNumByFunc #(= 3 (mod % 8))) (getRandomNumByFunc #(= 3 (mod % 8)))]
   :4 [(getRandomNumByFunc #(= 4 (mod % 8))) (getRandomNumByFunc #(= 4 (mod % 8)))]
   :5 [(getRandomNumByFunc #(= 5 (mod % 8))) (getRandomNumByFunc #(= 5 (mod % 8)))]
   :6 [(getRandomNumByFunc #(= 6 (mod % 8))) (getRandomNumByFunc #(= 6 (mod % 8)))]
   :7 [(getRandomNumByFunc #(= 7 (mod % 8))) (getRandomNumByFunc #(= 7 (mod % 8)))]})

(def filled (reduce (fn [acc arr]
                      (reduce (fn [acc2 value2] (set/add value2 acc2)) acc (val arr)))
                    nil
                    raw))

(deftest prop-set-testing
  (testing "add"
    (is
     (=
      filled
      [{:value (first (raw :0)), :next {:value (second (raw :0)), :next nil}}
       {:value (first (raw :1)), :next {:value (second (raw :1)), :next nil}}
       {:value (first (raw :2)), :next {:value (second (raw :2)), :next nil}}
       {:value (first (raw :3)), :next {:value (second (raw :3)), :next nil}}
       {:value (first (raw :4)), :next {:value (second (raw :4)), :next nil}}
       {:value (first (raw :5)), :next {:value (second (raw :5)), :next nil}}
       {:value (first (raw :6)), :next {:value (second (raw :6)), :next nil}}
       {:value (first (raw :7)), :next {:value (second (raw :7)), :next nil}}])))

  (testing "delete"
    (is (=
         (reduce (fn [acc arr]
                   (set/delete (first (val arr)) acc))
                 filled raw)
         [{:value (second (raw :0)), :next nil}
          {:value (second (raw :1)), :next nil}
          {:value (second (raw :2)), :next nil}
          {:value (second (raw :3)), :next nil}
          {:value (second (raw :4)), :next nil}
          {:value (second (raw :5)), :next nil}
          {:value (second (raw :6)), :next nil}
          {:value (second (raw :7)), :next nil}])))

  (testing "reduce-set"
    (is (=
         (set/reduce-set + 0 filled)
         (reduce (fn [acc arr]
                   (reduce (fn [acc2 value2] (+ acc2 value2)) acc (val arr)))
                 0
                 raw)))))


