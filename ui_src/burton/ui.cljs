(ns burton.ui
  (:require
    [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defn root-component []
  [:div
   [:div.logos
    [:img.electron {:src "img/electron-logo.png"}]
    [:img.cljs {:src "img/cljs-logo.svg"}]
    [:img.reagent {:src "img/reagent-logo.png"}]]
   [:pre "Versions:"
    [:p (str "Node     " js/process.version)]
    [:p (str "Electron " ((js->clj js/process.versions) "electron"))]
    [:p (str "Chromium " ((js->clj js/process.versions) "chrome"))]]])


(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))
