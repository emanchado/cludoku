(ns cludoku.core
  (:require [cludoku.board :refer [create-board update-board dim solved?
                                   import-board]]
            [cludoku.solver :as solver]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def default-board "3 3\n2 _ _ _ 8 _ 7 _ 1\n_ 5 _ _ _ _ _ _ _\n_ _ 6 _ _ _ 2 _ 4\n7 _ _ 1 2 _ _ _ _\n_ 2 _ 5 _ 6 _ 3 _\n_ _ _ _ 3 9 _ _ 8\n8 _ 2 _ _ _ 9 _ _\n_ _ _ _ _ _ _ 6 _\n3 _ 4 _ 1 _ _ _ 2")
(def app-state (atom {}))

(defn reset-game [board-name board-str]
  (reset! app-state {:name board-name
                     :states [{:board (create-board (import-board board-str))
                               :applied-rule "<Initial state>"}]
                     :current-state 0}))

(reset-game "*default*" default-board)

(defn sudoku-cell-view [{:keys [cands update]} owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/div #js {:className "sudoku-cell"}
             (if (= 1 (count cands))
               (list (dom/span #js {:className "sudoku-number"}
                               (first cands)))
               (map (fn [c]
                      (let [extra-class (if (and update
                                                 (not (contains? update c)))
                                          " sudoku-candidate-dropped"
                                          "")]
                        (dom/div #js {:className (str "sudoku-candidate "
                                                      "sudoku-candidate-" c
                                                      extra-class)}
                                 c)))
                    cands))))))

(defn sudoku-board-view [app owner]
  (reify
    om/IRender
    (render [this]
      (let [state (nth (:states app)
                       (:current-state app))
            board (:board state)
            updates (:applied-updates state)
            cell-map (:cells board)
            board-range (range (dim board))]
        (apply dom/div #js {:className "sudoku-board"}
               (map (fn [row-n]
                      (apply dom/div #js {:className "sudoku-row"}
                             (map (fn [col-n]
                                    (let [coords [row-n col-n]]
                                      (om/build sudoku-cell-view
                                                {:cands (get cell-map coords)
                                                 :update (get updates
                                                              coords)})))
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
              (-> app
                  (update-in [:current-state] inc)
                  (update-in [:states]
                             #(conj % {:board current-board
                                       :applied-updates update
                                       :applied-rule rule-name}))
                  (update-in [:states]
                             #(conj % {:board updated-board
                                       :applied-updates #{}
                                       :applied-rule "Cleanup"
                                       :solved? (solved? updated-board)
                                       :finished? (solved? updated-board)})))
              (if (> (count pending-rules) 1)
                (recur (rest pending-rules))
                (-> app
                    (assoc-in [:states (:current-state app) :finished?] true)
                    (assoc-in [:states (:current-state app) :solved?]
                              (solved? current-board)))))))))))

(defn sudoku-controls-view [app owner]
  (reify
    om/IRender
    (render [_]
      (dom/div nil
               (dom/button #js {:accessKey "p"
                                :onClick (fn [e] (om/transact! app prev-step))
                                :disabled (zero? (:current-state app))}
                           "Previous step")
               (dom/button #js {:accessKey "n"
                                :onClick (fn [e] (om/transact! app next-step))
                                :disabled (if (get-in app [:states (:current-state app) :finished?])
                                            "disabled")}
                           "Next step")))))

(defn sudoku-info-view [app owner]
  (reify
    om/IRender
    (render [_]
      (let [board-name (:name app)
            current-state (:current-state app)
            last-state (-> app :states count dec)
            current-rule (get-in app [:states current-state :applied-rule])]
        (dom/div nil
                 (dom/h2 #js {:className "board-name"} board-name)
                 (dom/span #js {:className "last-change"}
                           (cond
                            (get-in app [:states (:current-state app) :solved?]) "Solved!"
                            (get-in app [:states (:current-state app) :finished?]) "Could not solve sudoku."
                            (= current-state 0) "<Initial state>"
                            :else (str "Applied \"" current-rule "\"."))))))))

(om/root sudoku-board-view app-state
  {:target (. js/document (getElementById "sudoku-board"))})

(om/root sudoku-controls-view app-state
  {:target (. js/document (getElementById "sudoku-controls"))})

(om/root sudoku-info-view app-state
  {:target (. js/document (getElementById "sudoku-info"))})
