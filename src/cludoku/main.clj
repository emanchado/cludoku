(ns cludoku.main
  (:use cludoku.solver)
  (:use cludoku.board)
  (:gen-class))

(defn -main
  [& args]
  (prn "OH HAI Cludoku")
  (with-open [w (clojure.java.io/writer "sudoku-0.html")]
    (.write w (print-board {:cells [[#{1 2 3} #{3 4} #{4 2}]]
                            :block-width 3
                            :block-height 3}))))
