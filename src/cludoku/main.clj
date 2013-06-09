(ns cludoku.main
  (:use cludoku.solver)
  (:use cludoku.board)
  (:gen-class))

(defn -main
  [& args]
  (let [b (import-board (first args))
        initial-board (create-board b)
        rules cludoku.solver/rules]
    (loop [cnt 0
           board initial-board]
      (let [new-state
            (reduce (fn [acc rule]
                      (let [acc-board (merge board {:cells acc})
                            update (rule acc-board)]
                        (prn (export-board acc-board))
                        (with-open [w (clojure.java.io/writer (str "sudoku-" cnt ".html"))]
                          (.write w (print-board board)))
                        (merge acc update)))
                    (:cells board)
                    rules)]
        (if (and (not (solved? board))
                 (not= (:cells board) new-state))
          (recur (inc cnt)
                 (merge board {:cells new-state})))))))
