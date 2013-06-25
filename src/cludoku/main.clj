(ns cludoku.main
  (:use cludoku.solver)
  (:use cludoku.board)
  (:gen-class))

(defn -main
  [& args]
  (let [b (import-board (first args))
        initial-board (create-board b)
        step-count (atom 1)]
    (prn (export-board initial-board))
    (with-open [w (clojure.java.io/writer (str "sudoku-0.html"))]
      (.write w (print-board initial-board {:step @step-count })))
    (loop [cnt 0
           board initial-board]
      (let [new-board
            (reduce (fn [acc-board {rule-name :name rule-function :function}]
                      (let [update (rule-function acc-board)
                            updated-board (board-update acc-board update)]
                        (when (not= update {})
                          (with-open [w (clojure.java.io/writer (str "sudoku-" @step-count ".html"))]
                            (.write w (print-board acc-board {:updates update
                                                              :step @step-count
                                                              :rule rule-name})))
                          (prn (export-board updated-board))
                          (swap! step-count inc))
                        updated-board))
                    board
                    cludoku.solver/rules)]
        (if (and (not (solved? new-board))
                 (not= board new-board))
          (recur (inc cnt)
                 new-board)
          (with-open [w (clojure.java.io/writer (str "sudoku-" @step-count ".html"))]
            (.write w (print-board new-board {:step @step-count
                                              :final-step true}))))))))
