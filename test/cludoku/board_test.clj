(ns cludoku.board-test
  (:use clojure.test
        cludoku.board))

(deftest board
  (testing "Considers a board with an oversized row malformed"
    (is (not (well-formed? {:block-height 2
                            :block-width 2
                            :cells [[nil 1 2 3]
                                    [nil nil nil 1 nil]
                                    [nil 2 nil 1]
                                    [nil nil nil 1 nil]]}))))

  (testing "Considers a board with an oversized column malformed"
    (is (not (well-formed? {:block-height 2
                            :block-width 2
                            :cells [[nil 1 nil nil]
                                    [2 3 nil nil]
                                    [1 nil nil nil]
                                    [nil nil nil nil]
                                    [nil]]}))))

  (testing "Considers a board with too-high numbers malformed"
    (is (not (well-formed? {:block-height 2
                            :block-width 2
                            :cells [[nil 1 nil nil]
                                    [2 3 nil nil]
                                    [1 nil nil nil]
                                    [nil nil 5 nil]]}))))

  (testing "Considers a board with a nil unsolved"
    (is (not (solved? {:block-height 2
                       :block-width 2
                       :cells [[nil 1] [2 3]]}))))

  (testing "Considers a board with no nils solved"
    (is (solved? {:block-height 2
                  :block-width 2
                  :cells [[2 1] [1 2]]})))

  (testing "Considers a board with many nils not solved (but consistent)"
    (is (not (solved? {:block-height 2
                       :block-width 2
                       :cells [[2 nil] [nil nil]]}))))

  (testing "Throws exception boards with repeated columns"
    (is (thrown? IllegalStateException (solved? {:block-height 2
                                                 :block-width 2
                                                 :cells  [[1 2] [1 nil]]}))))

  (testing "Throws exception boards with repeated rows"
    (is (thrown? IllegalStateException (solved? {:block-height 2
                                                 :block-width 2
                                                 :cells [[1 1] [2 nil]]}))))

  (testing "Throws exception on finished board with repeated columns"
    (is (thrown? IllegalStateException (solved? {:block-height 2
                                                 :block-width 2
                                                 :cells [[1 2] [1 2]]}))))

  (testing "Throws exception on finished board with repeated rows"
    (is (thrown? IllegalStateException (solved? {:block-height 2
                                                 :block-width 2
                                                 :cells [[1 1] [2 2]]}))))

  (testing "A full real-life, solved sudoku is considered solved"
    (is (solved? {:block-height 3
                  :block-width 3
                  :cells [[5 3 4 6 7 8 9 1 2]
                          [6 7 2 1 9 5 3 4 8]
                          [1 9 8 3 4 2 5 6 7]
                          [8 5 9 7 6 1 4 2 3]
                          [4 2 6 8 5 3 7 9 1]
                          [7 1 3 9 2 4 8 5 6]
                          [9 6 1 5 3 7 2 8 4]
                          [2 8 7 4 1 9 6 3 5]
                          [3 4 5 2 8 6 1 7 9]]}))))
