(ns linked-list)

(defn create-entry [key value] {:key key :value value :next nil})

(defn add-entry [entry new-key new-value]
  (cond
    entry (cond
            (= (entry :key) new-key) (cond
                                       (entry :next) (update entry :next (add-entry (entry :next :next) new-key new-value))
                                       :else (create-entry new-key new-value)
                                       )
            :else (update entry :next #(add-entry % new-key new-value))
            )
    :else (create-entry new-key new-value)
    )
  )

(defn contains [{:keys [key next]} to-key]
  (cond
    (= key to-key) true
    next (recur next to-key)
    :else false
    )
  )

(defn remove-entry [{:keys [next key] :as entry} to-key]
  (cond
    (nil? entry) nil
    (= key to-key) next
    :else (update entry :next #(remove-entry % to-key))
    )
  )

(add-entry nil 1 "hello")

(add-entry (add-entry nil 1 "hello") 2 "end")

(add-entry (add-entry nil 1 "hello") 1 "hello2")

(contains (add-entry nil 1 "hello") 1)

(contains (add-entry nil 1 "hello") 2)

(remove-entry (add-entry nil 1 'hello') 1)

(contains (add-entry (add-entry nil 1 "hello") 2 "end") 2)