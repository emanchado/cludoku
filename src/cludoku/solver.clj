(ns cludoku.solver)

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
