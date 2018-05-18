(ns burton.plex
  "Drawing inspiration from TheBrain."
  (:require
    [re-frame.core :as rf])
  (:require-macros
    [burton.utils :as u]))

(defn plex-component
  []
  (let [*loaded-file (rf/subscribe [:loaded-file])]
    (if-not @*loaded-file
      [:div.jumbotron.jumpotron-fluid
       [:div.alert.alert-primary {:role :alert}
        [:div "No document has been loaded."]
        ;; Temporary:
        (into [:div]
              (u/forv [i (range 5)]
                      [:div (str "item #" i)]))]]
      ;; Temporary:
      [:strong @*loaded-file])))
