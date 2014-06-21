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

Note that the web version is work in progress. It's kind of slow, it
needs design work and it doesn't mark yet which cells and candidates
have changed recently.

If you're still curious to run it, compile it first by typing:

    lein cljx once
    lein cljsbuild once

and then open `public/index.html` in your browser of choice.

## License

Copyright © 2012-2013 Esteban Manchado Velázquez

Distributed under the Eclipse Public License, the same as Clojure.
