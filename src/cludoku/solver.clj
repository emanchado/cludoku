(ns cludoku.solver
  (:use clojure.set))

(defn fill-in-last-digits [row]
  (let [existing-numbers (set (remove nil? row))
        row-size (count existing-numbers)
        possible-numbers (set (range 1 (inc row-size)))]
    (if (= row-size (dec (count row)))
      (let [missing-number (first (clojure.set/difference possible-numbers
                                                          existing-numbers))]
        (map #(if (nil? %) missing-number %) row))
      row)))

(defn ^:export solve-step [board]
  (conj board
        {:cells (map fill-in-last-digits
                     (:cells board))}))

(defn ^:export remove-doubles [cell-set]
  (let [possible-repeated-pair
        (first (first (filter #(= (nth % 1) 2)
                              (frequencies ((group-by #(count %)
                                                      (vals cell-set)) 2)))))]
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
