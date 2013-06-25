(ns cludoku.main
  (:use cludoku.solver)
  (:use cludoku.board)
  (:gen-class))

; Stolen from http://stackoverflow.com/questions/4830900/how-do-i-find-the-index-of-an-item-in-a-vector
(defn indexed
  "Returns a lazy sequence of [index, item] pairs, where items come
  from 's' and indexes count up from zero.

  (indexed '(a b c d))  =>  ([0 a] [1 b] [2 c] [3 d])"
  [s]
  (map vector (iterate inc 0) s))

(defn -main
  [& args]
  (let [b (import-board (first args))
        initial-board (create-board b)
        rules cludoku.solver/rules
        step-count (atom 0)]
    (prn (export-board initial-board))
    (loop [cnt 0
           board initial-board]
      (let [nrules (count rules)
            new-board
            (reduce (fn [acc-board [nrule rule]]
                      (let [update (rule acc-board)
                            updated-board (board-update acc-board update)]
                        (when (not= update {})
                          (with-open [w (clojure.java.io/writer (str "sudoku-" @step-count ".html"))]
                            (.write w (print-board acc-board {:updates update
                                                              :step @step-count })))
                          (prn (export-board updated-board))
                          (swap! step-count inc))
                        updated-board))
                    board
                    (indexed rules))]
        (if (and (not (solved? new-board))
                 (not= board new-board))
          (recur (inc cnt)
                 new-board)
          (with-open [w (clojure.java.io/writer (str "sudoku-" @step-count ".html"))]
            (.write w (print-board new-board {:step @step-count
                                              :final-step true}))))))))
