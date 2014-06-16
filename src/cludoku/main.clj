(ns cludoku.main
  (:use cludoku.solver)
  (:use cludoku.board)
  (:gen-class)
  (:require [comb.template :as template]))

(defn print-board
  "Returns an HTML representation of the given board. The second,
   optional parameter is a map with options. Possible options are:

   :updates    - a cell set with the last update made to the board
   :step       - a number identifying the current step (1, 2, 3, ...)
   :final-step - a optional boolean indicating if this step is the
                 last one (defaults to false)
   :rule       - the name of the last applied rule (eg. 'Naked pairs')"
  ([board]
     (print-board board {}))
  ([board options]
     (template/eval (slurp "templates/board.eclj")
                    {:board board
                     :updates (get options :updates {})
                     :step (get options :step)
                     :final-step (get options :final-step false)
                     :rule (get options :rule)})))

(defn import-board [filename]
  "Returns a new board read from the given filename. The format of the
   file must be a first line with two space-separated numbers
   indicating the width and height of the block, and the rest of the
   lines must be a space-separated contents of each successive
   row. Each cell contents must be either a number or an underscore."
  (let [lines (clojure.string/split-lines (slurp filename))
        dimesions-spec (map read-string
                            (clojure.string/split (first lines) #" "))
        rows-spec (rest lines)]
    {:block-width (first dimesions-spec)
     :block-height (second dimesions-spec)
     :cells (mapv (fn [line]
                    (mapv (fn [cell]
                            (let [number (read-string cell)]
                              (if (number? number) number nil)))
                          (clojure.string/split line #" ")))
                  rows-spec)}))

(defn -main
  [& args]
  (let [b (import-board (first args))
        initial-board (create-board b)
        step-count (atom 1)]
    (with-open [w (clojure.java.io/writer (str "sudoku-0.html"))]
      (.write w (print-board initial-board {:step 0})))
    (loop [cnt 0
           board initial-board]
      (let [new-board
            (reduce (fn [acc-board {rule-name :name rule-function :function}]
                      (let [update (rule-function acc-board)
                            updated-board (update-board acc-board update)]
                        (when (not= update {})
                          (with-open [w (clojure.java.io/writer (str "sudoku-" @step-count ".html"))]
                            (.write w (print-board acc-board {:updates update
                                                              :step @step-count
                                                              :rule rule-name})))
                          (println (str "Applying rule '" rule-name "'"))
                          (swap! step-count inc))
                        updated-board))
                    board
                    cludoku.solver/rules)]
        (if (and (not (solved? new-board))
                 (not= board new-board))
          (recur (inc cnt)
                 new-board)
          (do
            (with-open [w (clojure.java.io/writer (str "sudoku-" @step-count ".html"))]
              (.write w (print-board new-board {:step @step-count
                                                :final-step true})))
            (if (solved? new-board)
              (println "Solved!")
              (println (str "Could not solve Sudoku, see sudoku-" @step-count ".html for the final state")))))))))
