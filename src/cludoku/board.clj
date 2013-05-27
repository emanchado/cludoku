(ns cludoku.board)
(require '[comb.template :as template])

(defn col [board-cells n] (map #(nth % n) board-cells))
(defn cols [board-cells]
  (map #(col board-cells %) (range (count (first board-cells)))))

(defn well-formed? [proto-board]
  (let [dimensions (* (:block-height proto-board) (:block-width proto-board))]
    (and (every? #(= (count %) dimensions) (:cells proto-board))
         (= (count (:cells proto-board)) dimensions)
         (every? (fn [x] (every? #(or (nil? %)
                                     (and (>= % 1)
                                          (<= % dimensions)))
                                x))
                 (:cells proto-board)))))

(defn create-board [proto-board]
  (if (well-formed? proto-board)
    proto-board
    (throw (IllegalArgumentException. "Inconsistent board cells!"))))

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
