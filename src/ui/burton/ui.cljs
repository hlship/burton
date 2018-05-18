(ns burton.ui
  (:require
    [reagent.core :as reagent :refer [atom]]
    [burton.plex :refer [plex-component]]
    [re-frame.core :as rf]))

(enable-console-print!)

(defn root-component []
  [:div.container-fluid
   [:nav.navbar.navbar-expand-lg.navbar-dark.bg-primary
    [:a.navbar-brand {:href "#"} "Burton"]
    [:form.form-inline
     [:button.btn {:type :submit} "Load"]]]
   (plex-component)])

(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))

(rf/reg-event-db :initialize (fn [_ _]
                               {:file-path nil}))
(rf/dispatch-sync [:initialize])

