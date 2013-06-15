(ns cludoku.board)
(require '[comb.template :as template])

(defn dim [board]
  (* (:block-height board) (:block-width board)))

(defn board-row [board row-number]
  (merge {} (filter #(= (first (first %)) row-number) (:cells board))))

(defn board-col [board col-number]
  (merge {} (filter #(= (second (first %)) col-number) (:cells board))))

(defn board-block [board block-number]
  (let [first-row (* (quot block-number (:block-height board))
                     (:block-height board))
        last-row  (dec (+ first-row (:block-height board)))
        first-col (mod (* block-number (:block-width board))
                       (dim board))
        last-col  (dec (+ first-col (:block-width board)))]
    (merge {} (filter #(and (>= (first (first %)) first-row)
                            (<= (first (first %)) last-row)
                            (>= (second (first %)) first-col)
                            (<= (second (first %)) last-col))
                      (:cells board)))))

(defn block-for-cell [board cell-pos]
  (let [block-row (quot (first cell-pos) (:block-height board))
        block-col (quot (second cell-pos) (:block-width board))
        block-number (+ (* (:block-height board) block-row) block-col)]
    (board-block board block-number)))

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

(defn consistent-sets? [board set-function]
  (every?
   (fn [n]
     (let [final-numbers (map #(first %)
                              (filter #(= (count %) 1)
                                      (map #(second %)
                                           (set-function board n))))]
       (or (empty? final-numbers)
           (apply distinct? final-numbers))))
   (range (dim board))))

(defn consistent? [board]
  (and (consistent-sets? board board-row)
       (consistent-sets? board board-col)
       (consistent-sets? board board-block)))

(defn ^:export solved? [board]
  (if (consistent? board)
    (let [number-unknowns (count (filter (fn [coord-cand]
                                           (> (count (second coord-cand)) 1))
                                         (:cells board)))]
      (= number-unknowns 0))
    (throw (IllegalStateException. "Inconsistent board!"))))

(defn ^:export print-board [board]
  (template/eval (slurp "templates/board.eclj") {:board board}))

(defn ^:export export-board [board]
  (let [index-range (range (* (:block-height board)
                              (:block-width board)))]
    (merge board
           {:cells (vec (map (fn [nrow]
                               (vec (map (fn [ncol]
                                           (let [cell-contents
                                                 (second (find (:cells board)
                                                               [nrow ncol]))]
                                             (if (= (count cell-contents) 1)
                                               (first cell-contents) nil)))
                                         index-range)))
                             index-range))})))

(defn ^:export import-board [filename]
  (let [lines (clojure.string/split-lines (slurp filename))
        dimesions-spec (map read-string
                            (clojure.string/split (first lines) #" "))
        rows-spec (rest lines)]
    {:block-width (first dimesions-spec)
     :block-height (second dimesions-spec)
     :cells (vec (map (fn [line]
                        (vec (map (fn [cell]
                                    (let [number (read-string cell)]
                                      (if (number? number) number nil)))
                                  (clojure.string/split line #" "))))
                      rows-spec))}))
