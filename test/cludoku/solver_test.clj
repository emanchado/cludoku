(ns cludoku.solver-test
  (:use clojure.test
        cludoku.board
        cludoku.solver))

(deftest solver
  (testing "Can fill in the last digit left in a row"
    (is (= (solve-step (create-board {:block-height 2
                                      :block-width 2
                                      :cells [[1   4 3   nil]
                                              [2   3 nil nil]
                                              [3   2 nil nil]
                                              [4   1 nil nil]]}))
           (create-board {:block-height 2
                          :block-width 2
                          :cells [[1   4 3   2]
                                  [2   3 nil nil]
                                  [3   2 nil nil]
                                  [4   1 nil nil]]}))))

  (testing "Can fill in the last digit left in a row (last digit)"
    (is (= (solve-step (create-board {:block-height 2
                                      :block-width 2
                                      :cells [[1   2 3   nil]
                                              [4   3 nil nil]
                                              [3   4 nil nil]
                                              [2   1 nil nil]]}))
           (create-board {:block-height 2
                          :block-width 2
                          :cells [[1   2 3   4]
                                  [4   3 nil nil]
                                  [3   4 nil nil]
                                  [2   1 nil nil]]}))))

  (testing "Can fill in the last digit left in a column"
    (is (= (solve-step (create-board {:block-height 2
                                      :block-width 2
                                      :cells [[1   4   3   2]
                                              [2   3   nil nil]
                                              [3   nil nil nil]
                                              [4   1   nil nil]]}))
           (create-board {:block-height 2
                          :block-width 2
                          :cells [[1   4 3   2]
                                  [2   3 nil nil]
                                  [3   2 nil nil]
                                  [4   1 nil nil]]}))))

  (testing "Can fill in the last digit left in a block"
    (is (= (solve-step (create-board {:block-height 2
                                      :block-width 2
                                      :cells [[nil 4   nil   2]
                                              [nil nil nil nil]
                                              [3   nil nil nil]
                                              [4   1   nil nil]]}))
           (create-board {:block-height 2
                          :block-width 2
                          :cells [[nil 4   nil   2]
                                  [nil nil nil nil]
                                  [3   2   nil nil]
                                  [4   1   nil nil]]}))))

  (testing "Can remove doubles from a set of cells"
    (is (= (remove-doubles {[0 0] #{2 4}, [0 1] #{1 2 3 4 5},
                            [0 2] #{2 4}, [0 3] #{7 8 9},
                            [0 4] #{1 3 5}, [0 5] #{6 8},
                            [0 6] #{5 7 9}, [0 7] #{3 4 5 6},
                            [0 8] #{3 5 6 7}})
           {[0 1] #{1 3 5} [0 7] #{3 5 6}})))

  (testing "Doesn't try to remove doubles if there are no real doubles"
    (is (= (remove-doubles {[0 0] #{2 4}, [0 1] #{1 2 3 4 5},
                            [0 2] #{2 4 5}, [0 3] #{7 8 9},
                            [0 4] #{1 3 5}, [0 5] #{6 8},
                            [0 6] #{5 7 9}, [0 7] #{3 4 5 6},
                            [0 8] #{3 5 6 7}})
           {}))))
