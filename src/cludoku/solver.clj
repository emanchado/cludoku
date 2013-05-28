(ns cludoku.solver
  (:use clojure.set)
  (:use cludoku.board))

(defn fill-in-last-digits [cell-set]
  (let [existing-numbers (set (map first (filter #(= (count %) 1)
                                                 (map second cell-set))))
        possible-numbers (set (range 1 (inc (count cell-set))))]
    (if (= (count existing-numbers)
           (dec (count cell-set)))
      (let [missing-number (first (clojure.set/difference possible-numbers
                                                          existing-numbers))
            unsolved-cell-coords (ffirst (filter #(not= (count (second %)) 1)
                                                 cell-set))]
        [unsolved-cell-coords #{missing-number}]))))

(defn ^:export solve-step [board]
  (let [updates (reduce
                 (fn [acc number]
                   (merge acc
                          (fill-in-last-digits (board-row board number))
                          (fill-in-last-digits (board-col board number))
                          (fill-in-last-digits (board-block board number))))
                 {}
                 (range (dim board)))]
    (conj board
          {:cells (merge (:cells board) updates)})))

(defn ^:export remove-doubles [cell-set]
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
