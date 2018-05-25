(ns burton.plex
  "Drawing inspiration from TheBrain."
  (:require
    [re-frame.core :as rf])
  (:require-macros
    [burton.utils :as u]))

(defn plex-component
  []
  (let [*model-data (rf/subscribe [:model-data])]
    (if-not @*model-data
      [:div.jumbotron.jumpotron-fluid
       [:div.alert.alert-primary {:role :alert}
        [:div "No document has been loaded."]]]
      ;; The good stuff
      [:svg.plex
       [:text {:x 0 :y 20}
        (pr-str @*model-data)]
       ])))
