(ns burton.utils
  (:require
    [re-frame.core :as rf]))

(defmacro forv
  "Strict version of clojure.core/for, returning a vector."
  [& body]
  `(into [] (for ~@body)))

(defmacro dispatch
  "A macro which expands to a standard event handler function.

  The event expression generates the event vector, which is dispatched."
  [event]
  `(fn [e#]
     (.preventDefault e#)
     (rf/dispatch ~event)))
