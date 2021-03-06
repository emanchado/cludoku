(ns cludoku.board
  #+cljs (:require [cljs.reader :refer [read-string]]
                   clojure.string))

(defrecord Board [block-width block-height cells])

(defn dim [board]
  "Returns the board side size, ie. the number of different symbols to
   be used in the board."
  (* (:block-height board) (:block-width board)))

(defn board-row [board row-number]
  "Returns a cell set with the row identified by the given row
   number."
  (merge {} (filter #(= (ffirst %) row-number) (:cells board))))

(defn board-col [board col-number]
  "Returns a cell set with the column identified by the given column
   number."
  (merge {} (filter #(= (second (first %)) col-number) (:cells board))))

(defn board-block [board block-number]
  "Returns a cell set with the block identified by the given block number."
  (let [first-row (* (quot block-number (:block-height board))
                     (:block-height board))
        last-row  (dec (+ first-row (:block-height board)))
        first-col (mod (* block-number (:block-width board))
                       (dim board))
        last-col  (dec (+ first-col (:block-width board)))]
    (merge {} (filter #(and (>= (ffirst %) first-row)
                            (<= (ffirst %) last-row)
                            (>= (second (first %)) first-col)
                            (<= (second (first %)) last-col))
                      (:cells board)))))

(defn block-for-cell [board [row-n col-n]]
  "Returns a cell set with the entire block that contains the given
   set of coordinates."
  (let [block-row (quot row-n (:block-height board))
        block-col (quot col-n (:block-width board))
        block-number (+ (* (:block-height board) block-row) block-col)]
    (board-block board block-number)))

(defn cell-set-diff [cell-set1 cell-set2]
  "Returns a map of differences between two cell sets where keys are
   coordinates that had different candidates in both cell sets, and
   values are two-element vectors with the candidates for each cell
   set. Used to get better error messages in tests."
  (reduce (fn [acc coord]
            (let [cands1 (get cell-set1 coord)
                  cands2 (get cell-set2 coord)]
              (if (not= cands1 cands2)
                (into acc {coord [cands1 cands2]})
                acc)))
          {}
          (keys cell-set1)))

(defn well-formed? [proto-board]
  "Returns true if the given proto-board is properly formed, meaning
   it has the right number of rows and columns, and all cells contain
   either nil or a number in the appropriate range."
  (let [dimensions (* (:block-height proto-board) (:block-width proto-board))]
    (and (every? #(= (count %) dimensions) (:cells proto-board))
         (= (count (:cells proto-board)) dimensions)
         (every? (fn [x] (every? #(or (nil? %)
                                     (and (>= % 1)
                                          (<= % dimensions)))
                                x))
                 (:cells proto-board)))))

(defn remove-candidate [number cell-set]
  "Given a number and cell set, it returns the cell set with the
   number removed from its candidates."
  (let [result (reduce (fn [update [cell-pos cell-cands]]
                         (into update (if (and (> (count cell-cands) 1)
                                               (contains? cell-cands number))
                                        [[cell-pos (set (remove #(= number %)
                                                                cell-cands))]])))
                       {}
                       cell-set)]
    result))

(defn remove-final-numbers [board new-final-numbers]
  "Given a board and a list of numbers, it returns a new board
   removing the given numbers from the candidates (except when one of
   those numbers is the only candidate)."
  (let [update
        (reduce (fn [acc-cells final-number]
                  (let [[pos one-element-set] final-number
                        number (first one-element-set)
                        acc-board (update-in board [:cells] merge acc-cells)
                        row-wo-final (remove-candidate
                                      number
                                      (board-row acc-board (first pos)))
                        col-wo-final (remove-candidate
                                      number
                                      (board-col acc-board (second pos)))
                        block-wo-final (remove-candidate
                                        number
                                        (block-for-cell acc-board pos))]
                    (merge acc-cells
                           row-wo-final
                           col-wo-final
                           block-wo-final)))
                {}
                new-final-numbers)
        updated-board (update-in board [:cells] merge update)
        new-finals (filter (fn [[_ new-cand-set]]
                             (= (count new-cand-set) 1))
                           update)]
    (if (pos? (count new-finals))
      (remove-final-numbers updated-board new-finals)
      updated-board)))

(defn create-board [proto-board]
  "Returns a board given a proto-board. A proto-board is a map with
   keys :block-height, :block-width and :cells. The first two are the
   height and width of a block (ie. 3 in a normal sudoku) and the last
   is a list of lists with the contents of the board (either nil or a
   number)."
  (if (well-formed? proto-board)
    (let [range-dim (range (* (:block-height proto-board)
                              (:block-width  proto-board)))
          proto-cells (:cells proto-board)
          final-cells (reduce (fn [row-acc x]
                                (merge row-acc
                                       (reduce
                                        (fn [col-acc y]
                                          (let [row-x (nth proto-cells x)
                                                cell-contents (nth row-x y)
                                                cands (if (nil? cell-contents)
                                                        (set (map inc
                                                                  range-dim))
                                                        #{cell-contents})]
                                            (merge col-acc [[x y] cands])))
                                        {}
                                        range-dim)))
                              {}
                              range-dim)
          raw-board (map->Board {:block-width (:block-width proto-board)
                                 :block-height (:block-height proto-board)
                                 :cells final-cells})]
      (remove-final-numbers raw-board
                            (filter (fn [[pos cands]]
                                      (= (count cands) 1))
                                    (:cells raw-board))))
    (throw (#+clj IllegalArgumentException. #+cljs js/Error
                  "Inconsistent board cells!"))))

(defn consistent-sets? [board set-function]
  "Given a board and a function to extract cell sets off a board, this
   function returns true if none of the cell sets returned by the
   set-function has repeated final numbers. The set-function will be
   called dim times, with the given board and all numbers from 0 to
   dim-1, where dim is the board size as returned by the 'dim'
   function."
  (every?
   (fn [n]
     (let [final-numbers (map first
                              (filter #(= (count %) 1)
                                      (map second
                                           (set-function board n))))]
       (or (empty? final-numbers)
           (apply distinct? final-numbers))))
   (range (dim board))))

(defn consistent? [board]
  "Returns true if the given board doesn't have any final numbers
   repeated, row-wise, col-wise or block-wise."
  (and (consistent-sets? board board-row)
       (consistent-sets? board board-col)
       (consistent-sets? board board-block)))

(defn update-board [board update]
  "Given a board and a cell set, returns a new board with the cell set
   applied as an update."
  (let [final-numbers (filter #(= (count (second %)) 1)
                              update)
        updated-board (update-in board [:cells] merge update)]
    (remove-final-numbers updated-board final-numbers)))

(defn solved? [board]
  "Returns true if the given board is solved (all cells contain final
   numbers). Throws an exception if the board is inconsistent, ie. if
   there are any repeated final numbers."
  (if (consistent? board)
    (let [number-unknowns (count (filter (fn [coord-cand]
                                           (> (count (second coord-cand)) 1))
                                         (:cells board)))]
      (zero? number-unknowns))
    (throw (#+clj IllegalStateException. #+cljs js/Error
                  "Inconsistent board!"))))

(defn import-board [board-string]
  "Returns a new board from the given string. The format of the string
   must be a first line with two space-separated numbers indicating
   the width and height of the block, and the rest of the lines must
   be a space-separated contents of each successive row. Each cell
   contents must be either a number or an underscore."
  (let [lines (clojure.string/split-lines board-string)
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

(defn export-board [board]
  "Returns a proto-board of the given board. Useful to eg. print an
   ASCII version of the board."
  (let [index-range (range (* (:block-height board)
                              (:block-width board)))]
    (merge board
           {:cells (vec (map (fn [nrow]
                               (vec (map (fn [ncol]
                                           (let [cell-contents
                                                 (second (find (:cells board)
                                                               [nrow ncol]))]
                                             (if (= (count cell-contents) 1)
                                               (first cell-contents) nil)))
                                         index-range)))
                             index-range))})))
