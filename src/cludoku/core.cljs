(ns cludoku.core
  (:require [cludoku.board :refer [create-board update-board dim]]
            [cludoku.solver :as solver]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

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
(def app-state (atom {:states [{:board (create-board medium-imported-board)
                                :applied-rule nil}]
                      :current-state 0}))

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

(defn sudoku-board-view [app owner]
  (reify
    om/IRender
    (render [this]
      (let [board (:board (nth (:states app)
                               (:current-state app)))
            cell-map (:cells board)
            board-range (range (dim board))]
        (apply dom/div #js {:className "sudoku-board"}
               (map (fn [row-n]
                      (apply dom/div #js {:className "sudoku-row"}
                             (map (fn [col-n]
                                    (om/build sudoku-cell-view
                                              (get cell-map [row-n col-n])))
                                  board-range)))
                    board-range))))))

(defn prev-step [app]
  (if (> (:current-state app) 0)
    (update-in app [:current-state] dec)
    app))

(defn next-step [app]
  (let [states (:states app)
        current-state (:current-state app)
        last-state (-> states count dec)
        current-board (:board (get states current-state))]
    (if (< current-state last-state)
      (update-in app [:current-state] inc)
      (if (:finished? app)
        app
        (loop [pending-rules solver/rules]
          (let [{rule-name :name rule-function :function} (first pending-rules)
                update (rule-function current-board)
                updated-board (update-board current-board update)]
            (if (not= current-board updated-board)
              (update-in (update-in app [:current-state] inc)
                         [:states]
                         #(conj % {:board updated-board
                                   :applied-updated update
                                   :applied-rule rule-name}))
              (if (> (count pending-rules) 1)
                (recur (rest pending-rules))
                (assoc app :finished? true)))))))))

(defn sudoku-controls-view [app owner]
  (reify
    om/IRender
    (render [_]
      (dom/div nil
               (dom/button #js {:onClick (fn [e] (om/transact! app next-step))}
                           "Next step")
               (dom/button #js {:onClick (fn [e] (om/transact! app prev-step))}
                           "Previous step")))))

(om/root sudoku-board-view app-state
  {:target (. js/document (getElementById "sudoku-board"))})

(om/root sudoku-controls-view app-state
  {:target (. js/document (getElementById "sudoku-controls"))})
