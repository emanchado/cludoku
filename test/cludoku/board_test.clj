(ns cludoku.board-test
  (:use clojure.test
        cludoku.board))

(deftest board-creation
  (testing "Considers a board with an oversized row malformed"
    (is (thrown? IllegalArgumentException
                 (create-board {:block-height 2
                                :block-width 2
                                :cells [[nil 1 2 3]
                                        [nil nil nil 1 nil]
                                        [nil 2 nil 1]
                                        [nil nil nil 1 nil]]}))))

  (testing "Considers a board with an oversized column malformed"
    (is (thrown? IllegalArgumentException
                 (create-board {:block-height 2
                                :block-width 2
                                :cells [[nil 1 nil nil]
                                        [2 3 nil nil]
                                        [1 nil nil nil]
                                        [nil nil nil nil]
                                        [nil]]}))))

  (testing "Considers a board with too-high numbers malformed"
    (is (thrown? IllegalArgumentException
                 (create-board {:block-height 2
                                :block-width 2
                                :cells [[nil 1 nil nil]
                                        [2 3 nil nil]
                                        [1 nil nil nil]
                                        [nil nil 5 nil]]})))))

(deftest board-import-export
  (testing "Can import a simple board definition"
    (is (= (import-board (slurp "test/fixtures/simple.board"))
           {:block-height 2
            :block-width 2
            :cells [[nil 1 2 3]
                    [nil nil nil 1]
                    [nil 2 nil 4]
                    [nil nil nil 2]]})))

  (testing "Can import a solved board definition"
    (is (= (import-board (slurp "test/fixtures/solved.board"))
           {:block-height 2
            :block-width 2
            :cells [[4 1 2 3]
                    [2 3 4 1]
                    [1 2 3 4]
                    [3 4 1 2]]})))

  (testing "Can import an uneven-block-shape board definition"
    (is (= (import-board (slurp "test/fixtures/uneven.board"))
           {:block-height 2
            :block-width 3
            :cells [[nil  6   4   3   2  nil]
                    [ 5   3  nil nil  6   4 ]
                    [ 4  nil nil nil nil  1 ]
                    [ 3  nil nil nil nil  2 ]
                    [ 6   4  nil nil  1   3 ]
                    [nil  1   3   4   5  nil]]})))

  (testing "Can export a board"
    (def board-def {:block-height 2
                    :block-width 2
                    :cells [[nil 1 2 3]
                            [nil nil nil 1]
                            [nil 2 nil 4]
                            [nil nil nil 2]]})
    (is (export-board (create-board board-def)) board-def)))

(deftest board-cleanup
  (testing "Can cleanup a single candidate"
    (let [raw-board {:block-height 2
                     :block-width 2
                     :cells {[0 0] #{1}       [0 1] #{1 2 3 4} [0 2] #{1 2 3 4} [0 3] #{1 2 3 4}
                             [1 0] #{1 2 3 4} [1 1] #{1 2 3 4} [1 2] #{1 2 3 4} [1 3] #{1 2 3 4}
                             [2 0] #{1 2 3 4} [2 1] #{1 2 3 4} [2 2] #{1 2 3 4} [2 3] #{1 2 3 4}
                             [3 0] #{1 2 3 4} [3 1] #{1 2 3 4} [3 2] #{1 2 3 4} [3 3] #{1 2 3 4}}}]
      (is (= (:cells (remove-final-numbers raw-board
                                           (filter (fn [[_ cands]]
                                                     (= (count cands) 1))
                                                   (:cells raw-board))))
             {[0 0] #{1}     [0 1] #{2 3 4}   [0 2] #{2 3 4}   [0 3] #{2 3 4}
              [1 0] #{2 3 4} [1 1] #{2 3 4}   [1 2] #{1 2 3 4} [1 3] #{1 2 3 4}
              [2 0] #{2 3 4} [2 1] #{1 2 3 4} [2 2] #{1 2 3 4} [2 3] #{1 2 3 4}
              [3 0] #{2 3 4} [3 1] #{1 2 3 4} [3 2] #{1 2 3 4} [3 3] #{1 2 3 4}}))))

  (testing "Can cleanup two candidates with common to-clean-up cells"
    (let [raw-board {:block-height 2
                     :block-width 2
                     :cells {[0 0] #{1}       [0 1] #{1 2 3 4} [0 2] #{1 2 3 4} [0 3] #{1 2 3 4}
                             [1 0] #{1 2 3 4} [1 1] #{1 2 3 4} [1 2] #{1 2 3 4} [1 3] #{1 2 3 4}
                             [2 0] #{1 2 3 4} [2 1] #{1 2 3 4} [2 2] #{2}       [2 3] #{1 2 3 4}
                             [3 0] #{1 2 3 4} [3 1] #{1 2 3 4} [3 2] #{1 2 3 4} [3 3] #{1 2 3 4}}}]
      (is (= {}
             (cell-set-diff (:cells (remove-final-numbers raw-board
                                                          (filter (fn [[_ cands]]
                                                                    (= (count cands) 1))
                                                                  (:cells raw-board))))
                            {[0 0] #{1}     [0 1] #{2 3 4}   [0 2] #{3 4}   [0 3] #{2 3 4}
                             [1 0] #{2 3 4} [1 1] #{2 3 4}   [1 2] #{1 3 4} [1 3] #{1 2 3 4}
                             [2 0] #{3 4}   [2 1] #{1 3 4}   [2 2] #{2}     [2 3] #{1 3 4}
                             [3 0] #{2 3 4} [3 1] #{1 2 3 4} [3 2] #{1 3 4} [3 3] #{1 3 4}})))))

  (testing "Newly created boards have clean candidates"
    (is (= (:cells (create-board {:block-height 2
                                  :block-width 2
                                  :cells [[ 1  nil nil nil]
                                          [nil nil nil nil]
                                          [nil nil nil nil]
                                          [nil nil nil nil]]}))
           {[0 0] #{1}     [0 1] #{2 3 4}   [0 2] #{2 3 4}   [0 3] #{2 3 4}
            [1 0] #{2 3 4} [1 1] #{2 3 4}   [1 2] #{1 2 3 4} [1 3] #{1 2 3 4}
            [2 0] #{2 3 4} [2 1] #{1 2 3 4} [2 2] #{1 2 3 4} [2 3] #{1 2 3 4}
            [3 0] #{2 3 4} [3 1] #{1 2 3 4} [3 2] #{1 2 3 4} [3 3] #{1 2 3 4}}))))

(deftest board-parts
  (testing "Can return an arbitrary row of a board"
    (is (= (set (keys (board-row (create-board {:block-height 2
                                                :block-width 2
                                                :cells [[nil  1   2   3 ]
                                                        [ 2   3   1   4 ]
                                                        [ 1  nil nil  2 ]
                                                        [ 3  nil nil  1 ]]})
                                 2)))
           #{[2 0] [2 1] [2 2] [2 3]})))

  (testing "Can return an arbitrary column of a board"
    (is (= (set (keys (board-col (create-board {:block-height 2
                                                :block-width 2
                                                :cells [[nil  1   2  nil]
                                                        [ 2   3  nil  4 ]
                                                        [ 1  nil nil nil]
                                                        [ 3   2   4   1 ]]})
                                 2)))
           #{[0 2] [1 2] [2 2] [3 2]})))

  (testing "Can return an arbitrary block of a board"
    (is (= (set (keys (board-block (create-board {:block-height 2
                                                  :block-width 2
                                                  :cells [[nil  1   2   3 ]
                                                          [ 2   3   1   4 ]
                                                          [ 1  nil nil  2 ]
                                                          [ 3  nil  4   1 ]]})
                                   2)))
           #{[2 0] [2 1] [3 0] [3 1]})))

  (testing "Can return the last block of a board"
    (is (= (set (keys (board-block
                       (create-board {:block-height 2
                                      :block-width 3
                                      :cells [[nil  4   6  nil nil  2 ]
                                              [nil nil nil  1  nil nil]
                                              [ 3  nil nil nil nil nil]
                                              [nil nil nil  4  nil nil]
                                              [ 3  nil nil nil  1  nil]
                                              [ 4   1  nil nil  2   3 ]]})
                                   5)))
        #{[4 3] [4 4] [4 5] [5 3] [5 4] [5 5]})))

  (testing "Can return the block a cell belongs to"
    (def board (create-board {:block-height 2
                              :block-width 3
                              :cells [[nil  4   6  nil nil  2 ]
                                      [nil nil nil  1  nil nil]
                                      [ 3  nil nil nil nil nil]
                                      [nil nil nil  4  nil nil]
                                      [ 3  nil nil nil  1  nil]
                                      [ 4   1  nil nil  2   3 ]]}))
    (is (= (set (keys (block-for-cell board [2 3])))
           #{[2 3] [2 4] [2 5] [3 3] [3 4] [3 5]}))

    (is (= (set (keys (block-for-cell board [4 1])))
           #{[4 0] [4 1] [4 2] [5 0] [5 1] [5 2]}))))

(deftest board-update-algorithm
  (testing "Can apply a simple update to an empty board"
    (is (= (:cells (update-board (create-board {:block-height 2
                                                :block-width 2
                                                :cells [[nil nil nil nil]
                                                        [nil nil nil nil]
                                                        [nil nil nil nil]
                                                        [nil nil nil nil]]})
                                 {[0 0] #{1}}))
           {[0 0] #{1}     [0 1] #{2 3 4}   [0 2] #{2 3 4}   [0 3] #{2 3 4}
            [1 0] #{2 3 4} [1 1] #{2 3 4}   [1 2] #{1 2 3 4} [1 3] #{1 2 3 4}
            [2 0] #{2 3 4} [2 1] #{1 2 3 4} [2 2] #{1 2 3 4} [2 3] #{1 2 3 4}
            [3 0] #{2 3 4} [3 1] #{1 2 3 4} [3 2] #{1 2 3 4} [3 3] #{1 2 3 4}})))

  (testing "Cleans up candidates recursively"
    (is (= (:cells (update-board {:block-height 2
                                  :block-width 2
                                  :cells {[0 0] #{1}       [0 1] #{1 2 3 4} [0 2] #{1 2 3 4} [0 3] #{1 2 3 4}
                                          [1 0] #{1 2 3 4} [1 1] #{1 2}     [1 2] #{1 2 3 4} [1 3] #{1 2 3 4}
                                          [2 0] #{1 2 3 4} [2 1] #{1 2 3 4} [2 2] #{1 2 3 4} [2 3] #{1 2 3 4}
                                          [3 0] #{1 2 3 4} [3 1] #{1 2 3 4} [3 2] #{1 2 3 4} [3 3] #{1 2 3 4}}}
                                 {[0 0] #{1}}))
           {[0 0] #{1}     [0 1] #{3 4}   [0 2] #{2 3 4}   [0 3] #{2 3 4}
            [1 0] #{3 4}   [1 1] #{2}     [1 2] #{1 3 4}   [1 3] #{1 3 4}
            [2 0] #{2 3 4} [2 1] #{1 3 4} [2 2] #{1 2 3 4} [2 3] #{1 2 3 4}
            [3 0] #{2 3 4} [3 1] #{1 3 4} [3 2] #{1 2 3 4} [3 3] #{1 2 3 4}}))))

(deftest board-solved
  (testing "Considers a board with no nils solved"
    (is (solved? (create-board {:block-height 2
                                :block-width 2
                                :cells [[2 1 3 4]
                                        [4 3 1 2]
                                        [3 4 2 1]
                                        [1 2 4 3]]}))))

  (testing "Considers a board with nils not solved (but consistent)"
    (is (not (solved? (create-board {:block-height 2
                                     :block-width 2
                                     :cells [[ 2  nil nil  4 ]
                                             [nil nil nil  2 ]
                                             [ 3   4   2   1 ]
                                             [ 1   2   4   3 ]]})))))

  (testing "Throws exception boards with repeated numbers in columns"
    (is (thrown? IllegalStateException
                 (solved? (create-board {:block-height 2
                                         :block-width 2
                                         :cells  [[ 1   2   3   4 ]
                                                  [ 1  nil nil nil]
                                                  [nil nil nil nil]
                                                  [nil nil nil nil]]})))))

  (testing "Throws exception boards with repeated numbers in rows"
    (is (thrown? IllegalStateException
                 (solved? (create-board {:block-height 2
                                         :block-width 2
                                         :cells [[ 1   1  3 4]
                                                 [ 3  nil 1 2]
                                                 [nil  2  4 3]
                                                 [nil nil 2 1]]})))))

  (testing "Throws exception boards with repeated numbers in blocks"
    (is (thrown? IllegalStateException
                 (solved? (create-board {:block-height 2
                                         :block-width 2
                                         :cells [[ 1   2  3 nil]
                                                 [ 2   1  4 nil]
                                                 [nil nil 1 nil]
                                                 [nil nil 2 nil]]})))))

  (testing "Throws exception on finished board with repeated columns"
    (is (thrown? IllegalStateException
                 (solved? (create-board {:block-height 2
                                         :block-width 2
                                         :cells [[1  2  3 4]
                                                 [1 nil 2 nil]
                                                 [2  3  4 1]
                                                 [4 nil 1 3]]})))))

  (testing "Throws exception on finished board with repeated rows"
    (is (thrown? IllegalStateException
                 (solved? (create-board {:block-height 2
                                         :block-width 2
                                         :cells [[1 nil nil nil]
                                                 [2  2  nil nil]
                                                 [3 nil nil nil]
                                                 [4 nil nil nil]]})))))

  (testing "A full real-life, solved sudoku is considered solved"
    (is (solved? (create-board {:block-height 3
                                :block-width 3
                                :cells [[5 3 4 6 7 8 9 1 2]
                                        [6 7 2 1 9 5 3 4 8]
                                        [1 9 8 3 4 2 5 6 7]
                                        [8 5 9 7 6 1 4 2 3]
                                        [4 2 6 8 5 3 7 9 1]
                                        [7 1 3 9 2 4 8 5 6]
                                        [9 6 1 5 3 7 2 8 4]
                                        [2 8 7 4 1 9 6 3 5]
                                        [3 4 5 2 8 6 1 7 9]]})))))
