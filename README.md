# cludoku

A simple Sudoku solver written in Clojure. The goal of this is having
some simple project to learn Clojure.

It has a simple command-line interface that runs on the JVM (and
outputs HTML files with the different steps) and a web interface
that runs on the browser, written in ClojureScript with React/Om.

## Running cludoku (command-line version)

To run the JVM version, simply type:

    lein run examples/medium-9x9.board

## Running cludoku (web version)

To run it, compile it first by typing:

    lein cljx once
    lein cljsbuild once

and then open `public/index.html` in your browser of choice. Note that
it's kind of slow and it needs design work.

## License

Copyright © 2012-2014 Esteban Manchado Velázquez

Distributed under the Eclipse Public License, the same as Clojure.
