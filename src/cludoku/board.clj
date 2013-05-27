(ns cludoku.board)
(require '[comb.template :as template])

(defn col [board n] (map #(nth % n) board))
(defn cols [board] (map #(col board %) (range (count (first board)))))

(defn well-formed? [board]
  (let [dimensions (* (:block-height board) (:block-width board))]
    (and (every? #(= (count %) dimensions) (:cells board))
         (= (count (:cells board)) dimensions)
         (every? (fn [x] (every? #(or (nil? %)
                                     (and (>= % 1)
                                          (<= % dimensions)))
                                x))
                 (:cells board)))))

(defn consistent-sets? [numbers]
  (reduce (fn [acc val]
            (let [numbers (remove nil? val)]
              (and acc
                   (or (empty? numbers)
                       (apply distinct? numbers)))))
          true
          numbers))

(defn consistent? [board]
  (and (consistent-sets? (:cells board))
       (consistent-sets? (cols (:cells board)))))

(defn ^:export solved? [board]
  (if (consistent? board)
    (let [number-unknowns (reduce (fn [acc val]
                                    (+ acc (reduce #(+ % (if (nil? %2) 1 0))
                                                   0
                                                   val)))
                                  0
                                  (:cells board))]
      (= number-unknowns 0))
    (throw (IllegalStateException. "Inconsistent board!"))))

(defn ^:export print-board [board]
  (template/eval (slurp "templates/board.eclj") {:board board}))
