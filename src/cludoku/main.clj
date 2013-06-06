(ns cludoku.main
  (:use cludoku.solver)
  (:use cludoku.board)
  (:gen-class))

(defn -main
  [& args]
  (let [b {:block-height 2
           :block-width 2
           :cells [[nil 1 2 3]
                   [nil nil nil 1]
                   [nil 2 nil 4]
                   [nil nil nil 2]]}
        board (create-board b)]
    (loop [cnt 0
           brd board]
      (with-open [w (clojure.java.io/writer (str "sudoku-" cnt ".html"))]
        (.write w (print-board brd))))))
