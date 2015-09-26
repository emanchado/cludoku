(defproject cludoku "0.0.1-SNAPSHOT"
  :description "Simple Sudoku solver"
  :url "http://github.com/emanchado/cludoku"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.5.0"]]
  :plugins [[lein-cljsbuild "1.0.2"]
            [com.keminglabs/cljx "0.6.0"]]

  :main cludoku.main
  :source_paths ["src" "target/classes"]
  :cljsbuild {:builds [{:id "cljs"
                        :source-paths ["src" "target/generated/cljs"]
                        :compiler {:output-to "public/js/cljs.js"
                                   :output-dir "public/js/out"
                                   :optimizations :simple}}]}
  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :clj}
                  {:source-paths ["src/cljx"]
                   :output-path "target/generated/cljs"
                   :rules :cljs}]}

  :test-selectors {:default (fn [_] true)
                   :focus :focus})
