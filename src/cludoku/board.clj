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

(defn block-for-cell [board [row-n col-n]]
  (let [block-row (quot row-n (:block-height board))
        block-col (quot col-n (:block-width board))
        block-number (+ (* (:block-height board) block-row) block-col)]
    (board-block board block-number)))

(defn cell-set-diff [cell-set1 cell-set2]
  (reduce (fn [acc coord]
            (let [cands1 (get cell-set1 coord)
                  cands2 (get cell-set2 coord)]
              (if (not= cands1 cands2)
                (into acc {coord [cands1 cands2]})
                acc)))
          {}
          (keys cell-set1)))

(defn well-formed? [proto-board]
  (let [dimensions (* (:block-height proto-board) (:block-width proto-board))]
    (and (every? #(= (count %) dimensions) (:cells proto-board))
         (= (count (:cells proto-board)) dimensions)
         (every? (fn [x] (every? #(or (nil? %)
                                     (and (>= % 1)
                                          (<= % dimensions)))
                                x))
                 (:cells proto-board)))))

(defn remove-candidate [number cell-set]
  (let [result (reduce (fn [update [cell-pos cell-cands]]
                         (into update (if (and (> (count cell-cands) 1)
                                               (contains? cell-cands number))
                                        [[cell-pos (set (remove #(= number %)
                                                                cell-cands))]])))
                       {}
                       cell-set)]
    result))

(defn remove-final-numbers [board new-final-numbers]
  (let [update
        (reduce (fn [acc-cells final-number]
                  (let [[pos one-element-set] final-number
                        number (first one-element-set)
                        acc-board (merge board {:cells (merge (:cells board)
                                                              acc-cells)})
                        row-wo-final (remove-candidate
                                      number
                                      (board-row acc-board (first pos)))
                        col-wo-final (remove-candidate
                                      number
                                      (board-col acc-board (second pos)))
                        block-wo-final (remove-candidate
                                        number
                                        (block-for-cell acc-board pos))]
                    (merge acc-cells
                           row-wo-final
                           col-wo-final
                           block-wo-final)))
                {}
                new-final-numbers)
        updated-board (merge board {:cells (merge (:cells board) update)})
        new-finals (filter (fn [[_ new-cand-set]]
                             (= (count new-cand-set) 1))
                           update)]
    (if (> (count new-finals) 0)
      (remove-final-numbers updated-board new-finals)
      updated-board)))

(defn create-board [proto-board]
  (if (well-formed? proto-board)
    (let [dim (* (:block-height proto-board)
                 (:block-width  proto-board))
          cells (:cells proto-board)
          raw-board (conj proto-board
                          [:cells
                           (reduce
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
                            (range dim))])]
      (remove-final-numbers raw-board
                            (filter (fn [[pos cands]]
                                      (= (count cands) 1))
                                    (:cells raw-board))))
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

(defn board-update [board update]
  (let [final-numbers (filter #(= (count (second %)) 1)
                              update)
        updated-board (merge board {:cells (merge (:cells board) update)})]
    (remove-final-numbers updated-board final-numbers)))

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
