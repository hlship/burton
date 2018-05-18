(ns burton.ui
  (:require
    [reagent.core :as reagent :refer [atom]]
    [burton.plex :refer [plex-component]]
    [re-frame.core :as rf])
  (:require-macros [burton.utils :as u]))

(enable-console-print!)

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
                     :on-click (u/dispatch [:load-file])} "Load"]]]
     [plex-component]
     ;; Temporary:
     (when @*loaded-file
       [:a.btn.btn-primary.btn-block {:on-click (u/dispatch [::unload-file])} "Reset"])]))

(rf/reg-event-fx :load-file
                 []
                 (fn [{:keys [db]} _ [_]]
                   {:db (assoc db ::loaded-file "placeholder.edn")}))

;; Temporary:
(rf/reg-event-db ::unload-file
                 (fn [db _]
                   (dissoc db ::loaded-file)))

(rf/reg-sub
  :loaded-file
  (fn [db _]
    (::loaded-file db)))

(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))

