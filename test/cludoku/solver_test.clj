(ns cludoku.solver-test
  (:use clojure.test
        cludoku.board
        cludoku.solver))

(deftest solver
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
           {})))

  (testing "A candidate is chosen when it only appears in one cell"
    (is (= (single-cell-candidate {[0 0] #{3 4} [0 1] #{3 4 9} [0 2] #{8}
                                   [1 0] #{2 4 5} [1 1] #{4 5 9} [1 2] #{5 9}
                                   [2 0] #{6} [2 1] #{1 3 4 5} [2 2] #{7}})
           {[1 0] #{2} [2 1] #{1}}))))

