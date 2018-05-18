(ns burton.ui
  (:require
    [reagent.core :as reagent :refer [atom]]
    [burton.main :refer [*main-window]]
    [burton.plex :refer [plex-component]]
    [re-frame.core :as rf])
  (:require-macros [burton.utils :as u]))

(enable-console-print!)

(def electron (js/require "electron"))


(defn root-component
  []
  (let [*loaded-file (rf/subscribe [:loaded-file])]
    [:div.container-fluid
     [:nav.navbar.navbar-expand-lg.navbar-dark.bg-primary
      [:a.navbar-brand {:href "#"} "Burton"]
      (when-let [path @*loaded-file]
        path)
      [:form.form-inline
       [:button.btn {:type :submit
                     :on-click (u/dispatch [:select-file])} "Load"]]]
     [plex-component]
     ;; Temporary:
     (when @*loaded-file
       [:a.btn.btn-primary.btn-block {:on-click (u/dispatch [::unload-file])} "Reset"])]))

(rf/reg-event-db :load-file
                 (fn [db [_ file-name]]
                   (assoc db ::loaded-file file-name)))

;; Temporary:
(rf/reg-event-db ::unload-file
                 (fn [db _]
                   (dissoc db ::loaded-file)))

(rf/reg-sub
  :loaded-file
  (fn [db _]
    (::loaded-file db)))

(rf/reg-event-fx
  :select-file
  (fn [_ _]
    (-> electron
        .-remote
        .-dialog
        (.showOpenDialog @*main-window
                         (clj->js {:title "Open Dependency File"
                                   :filters [{:name "EDN" :extensions ["edn"]}]
                                   :properties ["openFile" "showHiddenFiles"]})
                         (fn [filename]
                           (rf/dispatch [:load-file filename]))))))

(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))

