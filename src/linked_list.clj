(ns linked-list)

(defn create-entry [value next] {:value value :next next})

(defn add-entry [{:keys [next value] :as entry} new-value]
  (cond
    entry (cond
            (= value new-value) (add-entry next new-value)
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

(defn map-entry [{:keys [next value] :as entry} fun]
  (cond
    entry (add-entry (map-entry next fun) (fun value))
    :else nil
    )
  )

(defn reduce-entry [fun accum {:keys [next value] :as entry}]
  (cond
    entry (reduce-entry fun (fun accum value) next)
    :else accum
    )
  )

