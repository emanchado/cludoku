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
      (let [new-board
            (reduce (fn [acc-board rule]
                      (let [update (rule acc-board)]
                        (prn (export-board acc-board))
                        (with-open [w (clojure.java.io/writer (str "sudoku-" cnt ".html"))]
                          (.write w (print-board board)))
                        (board-update acc-board update)))
                    board
                    rules)]
        (if (and (not (solved? new-board))
                 (not= board new-board))
          (recur (inc cnt)
                 new-board))))))
