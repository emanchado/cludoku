(ns cludoku.solver
  (:require [clojure.set :as set]
            [cludoku.board :refer [board-block board-row board-col dim]]))

(defn skip-cells [to-skip cell-list]
  "Given a list of coordinates and a cell set, return a new cell set like
   the given one but skipping any cells in the given coordinates."
  (into {} (remove (fn [[coords cands]]
                     (some #(= coords %) to-skip))
                   cell-list)))

(defn with-candidate [cand]
  "Given a number 'cand', returns a function that receives a cell and
   checks if the cell has 'cand' among its candidates. The returned
   function is very useful in combination with filter and similar
   functions."
  (fn [[_ cands]]
    (contains? cands cand)))

(defn drop-candidates [unwanted-cands cells]
  "Returns a cell set like the given one, but without any of the given
   unwanted candidates."
  (into {} (vec (map (fn [[pos cands]]
                       [pos (set (remove unwanted-cands cands))])
                     (filter (fn [[_ cands]]
                               (some #(contains? cands %) unwanted-cands))
                             cells)))))

(defn unsolved-cells [cell-set]
  "Returns a cell set that only contains the unsolved cells of the given set"
  (filter (fn [[_ cands]]
            (not= (count cands) 1))
          cell-set))


;; Rules ---------------------------------------------------------------------

(defn naked-pairs [cell-set]
  (let [cells-with-two-cands ((group-by count
                                        (vals cell-set)) 2)
        repeated-pair (ffirst (filter #(= (nth % 1) 2)
                                      (frequencies cells-with-two-cands)))]
    (if repeated-pair
      (drop-candidates repeated-pair
                       (filter (fn [[_ cands]] (not= cands repeated-pair))
                               cell-set))
      {})))

(defn single-cell-candidate [cell-set]
  (let [unsolved-cells (unsolved-cells cell-set)
        unsolved-cand-pos (reduce (fn [acc [pos cands]]
                                    (conj acc
                                          (reduce
                                           (fn [acc2 cand]
                                             (conj acc2
                                                   [cand (conj (get acc cand ())
                                                               pos)]))
                                           {}
                                           cands)))
                                  {}
                                  unsolved-cells)]
    (reduce (fn [acc [cand pos-list]]
              (if (= (count pos-list) 1)
                (conj acc [(first pos-list) #{cand}])
                acc))
            {}
            unsolved-cand-pos)))

(defn candidate-lines [board]
  (reduce (fn [acc-changes blockn]
            (let [block (board-block board blockn)
                  unsolved-cells (unsolved-cells block)
                  all-cands (reduce (fn [acc [_ cands]]
                                      (set/union acc cands))
                                    #{}
                                    unsolved-cells)
                  cands-pos (reduce (fn [acc cand]
                                      (merge acc
                                             {cand
                                              (map first
                                                   (filter (with-candidate cand)
                                                           unsolved-cells))}))
                                    {}
                                    all-cands)
                  cand-horiz-lines (filter (fn [[cand pos-list]]
                                             (= (count (frequencies (map first pos-list))) 1))
                                           cands-pos)
                  cand-vert-lines (filter (fn [[cand pos-list]]
                                            (= (count (frequencies (map second pos-list))) 1))
                                          cands-pos)
                  horiz-line-updates (reduce (fn [acc [cand list-coords]]
                                               (let [rown (ffirst list-coords)
                                                     row (board-row board
                                                                    rown)]
                                                 (merge acc
                                                        (drop-candidates
                                                         #{cand}
                                                         (skip-cells list-coords
                                                                     row)))))
                                             {}
                                             cand-horiz-lines)
                  vert-line-updates (reduce (fn [acc [cand list-coords]]
                                              (let [coln (second (first
                                                                  list-coords))
                                                    col (board-col board coln)]
                                                (merge acc
                                                       (drop-candidates
                                                        #{cand}
                                                        (skip-cells list-coords
                                                                    col)))))
                                            {}
                                            cand-vert-lines)]
              (merge acc-changes
                     horiz-line-updates
                     vert-line-updates)))
          {}
          (range (dim board))))

(defn x-wing [board]
  (let [dim-range (range (dim board))]
    (reduce (fn [acc cand]
              (let [coords-with-cand (map first (filter (with-candidate cand)
                                                        (:cells board)))
                    by-rows (reduce (fn [acc [x y]]
                                      (conj acc [x (conj (get acc x #{}) y)]))
                                    {}
                                    coords-with-cand)
                    rows-with-two-cands (filter (fn [[_ ys]]
                                                  (= (count ys) 2))
                                                by-rows)
                    two-cand-rows-by-column (reduce
                                             (fn [acc [x ys]]
                                               (conj acc
                                                     [ys
                                                      (conj (get acc ys #{})
                                                            x)]))
                                             {}
                                             rows-with-two-cands)
                    x-wing-coords (first (filter (fn [[_ row-set]]
                                                   (= (count row-set) 2))
                                                 two-cand-rows-by-column))]
                ;; Find two rows that have the candidate in only two
                ;; columns (AND LATER, also two columns that have the
                ;; candidate in only two rows)
                (if (pos? (count x-wing-coords))
                  (let [[[y1 y2] [x1 x2]] (map seq x-wing-coords)
                        cell1 [x1 y1]
                        cell2 [x1 y2]
                        cell3 [x2 y1]
                        cell4 [x2 y2]
                        x-wing-cells [cell1 cell2 cell3 cell4]]
                    (merge acc
                           (drop-candidates #{cand}
                                            (skip-cells x-wing-cells
                                                        (board-row board x1)))
                           (drop-candidates #{cand}
                                            (skip-cells x-wing-cells
                                                        (board-row board x2)))
                           (drop-candidates #{cand}
                                            (skip-cells x-wing-cells
                                                        (board-col board y1)))
                           (drop-candidates #{cand}
                                            (skip-cells x-wing-cells
                                                        (board-col board y2)))))
                  acc)))
            {}
            dim-range)))


(defn region-rule [f]
  "Given a region rule function (ie. a rule function that operates on
   a cell set instead of the whole board), returns a regular rule
   function that will apply the region rule to all rows, columns and
   blocks."
  (fn [board]
    (let [dim (dim board)
          row-updates (reduce (fn [acc i] (merge acc (f (board-row board i))))
                              {}
                              (range dim))
          col-updates (reduce (fn [acc i] (merge acc (f (board-col board i))))
                              {}
                              (range dim))
          block-updates (reduce (fn [acc i]
                                  (merge acc (f (board-block board i))))
                                {}
                                (range dim))]
      (merge row-updates col-updates block-updates))))


(def rules [{:name "Naked pairs"
             :function (region-rule naked-pairs)}
            {:name "Candidate in a single cell"
             :function (region-rule single-cell-candidate)}
            {:name "Candidate lines"
             :function candidate-lines}
            {:name "X-Wing"
             :function x-wing}])

