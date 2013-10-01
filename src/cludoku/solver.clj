(ns cludoku.solver
  (:use clojure.set)
  (:use cludoku.board))

(defn skip-cells [to-skip cell-list]
  (into {} (remove (fn [row-cell]
                     (some #(= (first row-cell) %) to-skip))
                   cell-list)))

(defn with-candidate [cand]
  (fn [[_ cands]]
    (contains? cands cand)))

(defn drop-candidates [unwanted-cands cells]
  (into {} (vec (map (fn [[pos cands]]
                       [pos (into #{} (remove unwanted-cands cands))])
                     (filter (fn [[_ cands]]
                               (some #(contains? cands %) unwanted-cands))
                             cells)))))

(defn naked-pairs [cell-set]
  (let [repeated-pair
        (ffirst (filter #(= (nth % 1) 2)
                        (frequencies ((group-by #(count %)
                                                (vals cell-set)) 2))))]
    (if repeated-pair
      (drop-candidates repeated-pair
                       (filter (fn [[_ cands]] (not= cands repeated-pair))
                               cell-set))
      {})))

(defn single-cell-candidate [cell-set]
  (let [unsolved-cells (remove (fn [[a b]] (= (count b) 1)) cell-set)
        unsolved-cands (reduce (fn [acc [pos b]]
                                 (conj acc
                                       (reduce
                                        (fn [acc2 cand]
                                          (conj acc2
                                                [cand (conj (get acc cand ())
                                                            pos)]))
                                        {}
                                        b)))
                               {}
                               unsolved-cells)]
    (reduce (fn [acc [cand pos-list]]
              (conj acc [(first pos-list) #{cand}]))
            {}
            (remove (fn [[cand pos-list]] (not= (count pos-list) 1))
                    unsolved-cands))))

(defn candidate-lines [board]
  (reduce (fn [acc-changes blockn]
            (let [block (board-block board blockn)
                  unsolved-cells (filter (fn [[_ cands]] (> (count cands) 1))
                                         block)
                  all-cands (reduce (fn [acc [_ cands]]
                                      (clojure.set/union acc cands))
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
                    rows-with-two-cands (filter (fn [[_ y]]
                                                  (= (count y) 2))
                                                by-rows)
                    columns-with-two-cand-rows (reduce (fn [acc [x ys]]
                                                         (conj acc [ys
                                                                    (conj (get acc ys #{}) x)]))
                                                       {}
                                                       rows-with-two-cands)
                    x-wing-coords (first (filter (fn [[_ row-set]]
                                                   (= (count row-set) 2))
                                                 columns-with-two-cand-rows))]
                ;; Find two rows that have the candidate in only two
                ;; columns (AND LATER, also two columns that have the
                ;; candidate in only two rows)
                (if (> (count x-wing-coords) 0)
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
