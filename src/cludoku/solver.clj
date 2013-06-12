(ns cludoku.solver
  (:use clojure.set)
  (:use cludoku.board))

(defn remove-final-numbers [cell-set]
  (let [existing-numbers (set (map first (filter #(= (count %) 1)
                                                 (map second cell-set))))]
    (into {} (map (fn [cell]
                    [(first cell)
                     (set (remove existing-numbers (second cell)))])
                  (filter (fn [cell]
                            (and (some #(contains? (second cell) %)
                                       existing-numbers)
                                 (> (count (second cell)) 1)))
                          cell-set)))))

(defn remove-doubles [cell-set]
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

(def rules [(region-rule remove-final-numbers)
            (region-rule remove-doubles)
            (region-rule single-cell-candidate)])
