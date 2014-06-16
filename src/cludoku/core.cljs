(ns cludoku.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cludoku.board :refer [create-board export-board dim]]))

(enable-console-print!)

(def easy-imported-board
  {:block-width 3
   :block-height 3
   :cells [[nil nil nil  1  nil  5  nil  6   8 ]
           [nil nil nil nil nil nil  7  nil  1 ]
           [ 9  nil  1  nil nil nil nil  3  nil]
           [nil nil  7  nil  2   6  nil nil nil]
           [ 5  nil nil nil nil nil nil nil  3 ]
           [nil nil nil  8   7  nil  4  nil nil]
           [nil  3  nil nil nil nil  8  nil  5 ]
           [ 1  nil  5  nil nil nil nil nil nil]
           [ 7   9  nil  4  nil  1  nil nil nil]]})
(def medium-imported-board
  {:block-width 3
   :block-height 3
   :cells [[ 2  nil nil nil  8  nil  7  nil  1 ]
           [nil  5  nil nil nil nil nil nil nil]
           [nil nil  6  nil nil nil  2  nil  4 ]
           [ 7  nil nil  1   2  nil nil nil nil]
           [nil  2  nil  5  nil  6  nil  3  nil]
           [nil nil nil nil  3   9  nil nil  8 ]
           [ 8  nil  2  nil nil nil  9  nil nil]
           [nil nil nil nil nil nil nil  6  nil]
           [ 3  nil  4  nil  1  nil nil nil  2 ]]})
(def app-state (atom {:board (create-board medium-imported-board)}))

(defn sudoku-cell-view [candidate-set owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/div #js {:className "sudoku-cell"}
             (if (= 1 (count candidate-set))
               (list (dom/span #js {:className "sudoku-number"}
                               (first candidate-set)))
               (map (fn [c]
                      (dom/div #js {:className (str "sudoku-candidate "
                                                    "sudoku-candidate-" c)}
                               c))
                    candidate-set))))))

(defn sudoku-board-view [board owner]
  (reify
    om/IRender
    (render [this]
      (let [cell-map (:cells board)
            board-range (range (dim board))]
        (apply dom/div #js {:className "sudoku-board"}
               (map (fn [row-n]
                      (apply dom/div #js {:className "sudoku-row"}
                             (map (fn [col-n]
                                    (om/build sudoku-cell-view
                                              (get cell-map [row-n col-n])))
                                  board-range)))
                    board-range))))))

(om/root sudoku-board-view (:board @app-state)
  {:target (. js/document (getElementById "sudoku-board"))})
