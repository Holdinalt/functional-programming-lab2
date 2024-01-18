(ns linked-list)

(defn create-entry [value next] {:value value :next next})

(defn add-entry [{:keys [next value] :as entry} new-value]
  (cond
    entry (cond
            (= (entry :value) new-value) (cond
                                       (entry :next) (create-entry value (add-entry (entry :next :next) new-value))
                                       :else (create-entry new-value nil)
                                       )
            :else (create-entry value (add-entry next new-value))
            )
    :else (create-entry new-value nil)
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
    (= value to-value) (remove-entry next to-value)
    :else (create-entry value (remove-entry next to-value))
    )
  )

(defn find-entry [{:keys [next value] :as entry} to-value]
  (cond
    (nil? entry) nil
    (= value to-value) entry
    :else (find-entry next to-value)
    )
  )

(defn get-vector [{:keys [next value] :as entry}]
  (cond
    entry (conj (get-vector next) value)
    :else []
    )
  )

(defn filter-entry [{:keys [next value] :as entry} filterFn]
  (cond
    (nil? entry) nil
    (false? (filterFn value)) (cond
                                next (filter-entry next filterFn)
                                :else nil
                                )
    :else (create-entry value (filter-entry next filterFn))
    )
  )

(defn map-entry [fun {:keys [next value] :as entry}]
  (cond
    entry (create-entry (fun value) (map-entry fun next))
    :else nil
    )
  )