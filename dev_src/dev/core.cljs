(ns dev.core
  (:require
    burton.ui
    [figwheel.client :as fw :include-macros true]))

(fw/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback (fn [] (print "reloaded")))

(print "dev.core loaded")
