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
    (let [dim (* (:block-height proto-board)
                 (:block-width  proto-board))
          cells (:cells proto-board)]
      (conj proto-board
            [:cells (reduce
                     (fn [row-acc x]
                       (merge row-acc
                              (reduce
                               (fn [col-acc y]
                                 (let [number (nth (nth cells x) y)]
                                   (merge col-acc
                                       [[x y]
                                        (if (nil? number)
                                          (set (map #(+ % 1)
                                                    (range dim)))
                                          #{number})])))
                               {}
                               (range dim))))
                     {}
                     (range dim))]))
    (throw (IllegalArgumentException. "Inconsistent board cells!"))))

(defn consistent-sets? [numbers]
  (reduce (fn [acc val]
            (let [numbers (remove nil? val)]
              (and acc
                   (or (empty? numbers)
                       (apply distinct? numbers)))))
          true
          numbers))

(defn dim [board]
  (* (:block-height board) (:block-width board)))

(defn board-row [board row-number]
  (filter #(= (first (first %)) row-number) (:cells board)))

(defn board-col [board col-number]
  (filter #(= (second (first %)) col-number) (:cells board)))

(defn consistent? [board]
  (and (every?
        (fn [row-n]
          (let [final-numbers (map #(first %)
                                   (filter #(= (count %) 1)
                                           (map #(second %)
                                                (board-row board row-n))))]
            (or (empty? final-numbers)
                (apply distinct? final-numbers))))
        (range (dim board)))
       (every?
        (fn [col-n]
          (let [final-numbers (map #(first %)
                                   (filter #(= (count %) 1)
                                           (map #(second %)
                                                (board-col board col-n))))]
            (or (empty? final-numbers)
                (apply distinct? final-numbers))))
        (range (dim board)))))

(defn ^:export solved? [board]
  (if (consistent? board)
    (let [number-unknowns (count (filter (fn [coord-cand]
                                           (> (count (second coord-cand)) 1))
                                         (:cells board)))]
      (= number-unknowns 0))
    (throw (IllegalStateException. "Inconsistent board!"))))

(defn ^:export print-board [board]
  (template/eval (slurp "templates/board.eclj") {:board board}))
