# Лабораторная работа #2

## Дисциплина
Функциональное программирование

## Работу выполнял

Студент группы P33102 Елохов Даниил

## Цель:

Освоиться с построением пользовательских типов данных, полиморфизмом, рекурсивными алгоритмами и средствами тестирования (unit testing, property-based testing).

## Требования:

1. Функции:
    - добавление и удаление элементов;
    - фильтрация;
    - отображение (map);
    - свертки (левая и правая);
    - структура должна быть [моноидом](https://ru.m.wikipedia.org/wiki/Моноид).
2. Структуры данных должны быть неизменяемыми.
3. Библиотека должна быть протестирована в рамках unit testing.
4. Библиотека должна быть протестирована в рамках property-based тестирования (как минимум 3 свойства, включая свойства моноида).
5. Структура должна быть полиморфной.
6. Требуется использовать идиоматичный для технологии стиль программирования. Примечание: некоторые языки позволяют получить большую часть API через реализацию небольшого интерфейса. Так как лабораторная работа про ФП, а не про экосистему языка -- необходимо реализовать их вручную и по возможности -- обеспечить совместимость.

## Вариант

Separate Chaining Hash Map Set

## Ход работы

### Linked List

```clojure
; Функция создания элемента листа
(defn create-entry [value next] {:value value :next next})

; Функция добавления элмента в лист
(defn add-entry [{:keys [next value] :as entry} new-value]
  (cond
    entry (cond
            (= value new-value) (add-entry next new-value)
            :else (create-entry value (add-entry next new-value))
            )
    :else (create-entry new-value nil)
    )
  )

; Функция проверка на вхождение в лист
(defn contains [{:keys [value next]} to-value]
  (cond
    (= value to-value) true
    next (recur next to-value)
    :else false
    )
  )

; Функция удаления элемента
(defn remove-entry [{:keys [next value] :as entry} to-value]
  (cond
    (nil? entry) nil
    (= value to-value) (remove-entry next to-value)
    :else (create-entry value (remove-entry next to-value))
    )
  )

; Функция поиска элемента
(defn find-entry [{:keys [next value] :as entry} to-value]
  (cond
    (nil? entry) nil
    (= value to-value) entry
    :else (find-entry next to-value)
    )
  )

; Функция получения вектора всех элементов
(defn get-vector [{:keys [next value] :as entry}]
  (cond
    entry (conj (get-vector next) value)
    :else []
    )
  )

; Функция реализиующая фильтрацию
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

; Функция реализиующая отображение
(defn map-entry [{:keys [next value] :as entry} fun]
  (cond
    entry (add-entry (map-entry next fun) (fun value))
    :else nil
    )
  )

; Функция реализиующая свертку
(defn reduce-entry [fun accum {:keys [next value] :as entry}]
  (cond
    entry (reduce-entry fun (fun accum value) next)
    :else accum
    )
  )

; Функция подсчитывающая количество элементов в листе
(defn count-entry [{:keys [next] :as entry}]
  (cond
    entry (+ 1 (count-entry next))
    :else 0
    )
  )
```

### Hash Map Set

```clojure
; Функция реализующая начальное состояние коллекции
(defn create-collection [n] (vec (repeat n nil)))

; Функция получения хеш функции от элемента
(defn get-hash [n] (hash n))

; Функция получения нужного ведра относительно элемента
(defn get-bucket-num [new-value collection]
  (mod (get-hash new-value) (count collection))
  )

; Функция обновления бакетов
(defn update-bucket [bucket-num new-bucket collection]
  (assoc
    collection
    bucket-num
    new-bucket
    )
  )

; Функция получения бакета по айди
(defn get-bucket-by-val [new-value collection]
  (collection (get-bucket-num new-value collection))
  )

; Функция добавления элемента в коллекцию
(defn add [value collection]
  (cond
    collection (update-bucket
                 (get-bucket-num value collection)
                 (linked-list/add-entry (get-bucket-by-val value collection) value)
                 collection
                 )
    :else (recur value (create-collection 8))
    )
  )

; Функция реструктуризации коллекции
(defn restruct [collection buckets-n]
  (cond
    collection (reduce
                 (fn [acc bucket]
                   (linked-list/reduce-entry (fn [acc2 val] (set/add val acc2)) acc bucket)
                   )
                 (create-collection buckets-n)
                 collection
                 )
    :else []
    )
  )

; Функция удаления элемента из коллекции
(defn delete [value collection]
  (cond
    collection (update-bucket
                 (get-bucket-num value collection)
                 (linked-list/remove-entry (get-bucket-by-val value collection) value)
                 collection
                 )
    )
  )

; Функция проверки нахождения элемента в коллекции
(defn has [value collection]
  (cond
    collection (linked-list/contains (get-bucket-by-val value collection) value)
    :else false
    )
  )

; Функция получения вектора всей коллекции
(defn get-vector [collection]
  (cond
    collection (into [] (reduce
                          (fn [acc bucket]
                            (concat acc (linked-list/get-vector bucket))
                            )
                          []
                          collection
                          ))
    :else []
    )
  )

; Вспомогательная функция
(defn conv-vec [par]
  (cond
    (nil? par) nil
    :else (vec par)
    )
  )

; Функция фильтрации коллекции
(defn filter-set [filterFn collection]
  (cond
    collection (conv-vec (map #(linked-list/filter-entry % filterFn) collection))
    :else nil
    )
  )

; Функция отображения коллекции
(defn map-set [fun collection]
  (cond
    collection (conv-vec (restruct (map #(linked-list/map-entry % fun) collection) (count collection)))
    :else nil
    )
  )

; Функция свертки коллекции
(defn reduce-set [fun accum collection]
  (cond
    collection (reduce
                 (fn [acc bucket]
                   (linked-list/reduce-entry fun acc bucket)
                   )
                 accum
                 collection)
    :else accum
    )
  )
```

## Вывод

Во время работы над лабораторной работы я немного осволися с построением пользовательских коллекций, еще лучше научился писать на Clojure и дастаточно неплохо освоился с рекурсивными алгоритмами

Также, вспомнил базовые реализации коллекций и удивился тому, что все еще умею писать код не только на JS