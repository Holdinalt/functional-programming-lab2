(ns linked-list)

(defn create-entry [value next] {:value value :next next})

(defn add-entry [new-value {:keys [next value] :as entry}]
  (cond
    entry (cond
            (= value new-value) (add-entry new-value next)
            :else (create-entry value (add-entry new-value next)))
    :else (create-entry new-value nil)))

(defn contains [to-value {:keys [value next]}]
  (cond
    (= value to-value) true
    next (recur to-value next)
    :else false))

(defn remove-entry [{:keys [next value] :as entry} to-value]
  (cond
    (nil? entry) nil
    (= value to-value) (remove-entry next to-value)
    :else (create-entry value (remove-entry next to-value))))

(defn find-entry [{:keys [next value] :as entry} to-value]
  (cond
    (nil? entry) nil
    (= value to-value) entry
    :else (find-entry next to-value)))

(defn get-vector [{:keys [next value] :as entry}]
  (cond
    entry (conj (get-vector next) value)
    :else []))

(defn filter-entry [{:keys [next value] :as entry} filterFn]
  (cond
    (nil? entry) nil
    (false? (filterFn value)) (cond
                                next (filter-entry next filterFn)
                                :else nil)
    :else (create-entry value (filter-entry next filterFn))))

(defn map-entry [{:keys [next value] :as entry} fun]
  (cond
    entry (add-entry (fun value) (map-entry next fun))
    :else nil))

(defn reduce-entry [fun accum {:keys [next value] :as entry}]
  (cond
    entry (reduce-entry
           fun
           (fun accum value)
           next)
    :else accum))

(defn count-entry [{:keys [next] :as entry}]
  (cond
    entry (+ 1 (count-entry next))
    :else 0))

;(defn comparison [{:keys [value next]} compare]
;  (let [comp-res (contains compare value)]
;    (cond
;      (nil? next) comp-res
;      :else (and comp-res (comparison next compare))
;      )
;    )
;  )
;
;(defn equal [main-entry compare-entry]
;  (cond
;    (= (count-entry main-entry) (count-entry compare-entry)) (comparison main-entry compare-entry)
;    :else false
;    )
;  )



