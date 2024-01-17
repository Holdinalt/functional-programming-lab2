(ns linked-list)

(defn create-entry [value] {:value value :next nil})

(defn add-entry [entry new-value]
  (cond
    entry (cond
            (= (entry :value) new-value) (cond
                                       (entry :next) (update entry :next (add-entry (entry :next :next) new-value))
                                       :else (create-entry new-value)
                                       )
            :else (update entry :next #(add-entry % new-value))
            )
    :else (create-entry new-value)
    )
  )

(defn contains [{:keys [value next]} to-value]
  (cond
    (= value to-value) true
    next (recur next to-value)
    :else false
    )
  )

(defn remove-entry [{:keys [next value] :as entry} to-value]
  (cond
    (nil? entry) nil
    (= value to-value) next
    :else (update entry :next #(remove-entry % to-value))
    )
  )

(defn find-entry [{:keys [next value] :as entry} to-value]
  (cond
    (nil? entry) nil
    (= value to-value) entry
    :else (find-entry next to-value)
    )
  )