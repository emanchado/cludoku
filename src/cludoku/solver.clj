(ns cludoku.solver
  (:use clojure.set)
  (:use cludoku.board))

(defn naked-pairs [cell-set]
  (let [possible-repeated-pair
        (ffirst (filter #(= (nth % 1) 2)
                        (frequencies ((group-by #(count %)
                                                (vals cell-set)) 2))))]
    (if possible-repeated-pair
      (into {} (map (fn [cell-pair] [(first cell-pair)
                                    (if (= (second cell-pair)
                                           possible-repeated-pair)
                                      (second cell-pair)
                                      (into #{} (remove possible-repeated-pair
                                                        (second cell-pair))))])
                    (filter #(and (some possible-repeated-pair (second %))
                                  (not= possible-repeated-pair (second %))) cell-set)))
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

(defn skip-cells [to-skip cell-list]
  (into {} (remove (fn [row-cell]
                     (some #(= (first row-cell) %) to-skip))
                   cell-list)))

(defn drop-candidates [unwanted-cands cells]
  (map (fn [[pos cands]]
         [pos (set (remove #(contains? unwanted-cands %) cands))])
       (filter (fn [[_ cands]]
                 (some #(contains? cands %) unwanted-cands))
               cells)))

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
                                              (map #(first %)
                                                   (filter (fn [[_ cands]]
                                                             (contains? cands
                                                                        cand))
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
                                                 (into acc
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
                                                (into acc
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

(def rules [(region-rule naked-pairs)
            (region-rule single-cell-candidate)
            candidate-lines])
