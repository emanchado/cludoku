(ns cludoku.solver-test
  (:use clojure.test
        cludoku.solver))

(deftest solver
  (testing "Can fill in the last digit left in a row"
    (is (= (solve-step {:block-height 2
                        :block-width 2
                        :cells [[1   4 3   nil]
                                [2   3 nil nil]
                                [3   2 nil nil]
                                [4   1 nil nil]]})
           {:block-height 2
            :block-width 2
            :cells [[1   4 3   2]
                    [2   3 nil nil]
                    [3   2 nil nil]
                    [4   1 nil nil]]}))))
