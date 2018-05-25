(ns burton.ui
  (:require
    [reagent.core :as reagent :refer [atom]]
    [burton.main :refer [*main-window]]
    [burton.plex :refer [plex-component]]
    [cljs.reader :as edn]
    [re-frame.core :as rf])
  (:require-macros [burton.utils :as u]))

(enable-console-print!)

(def electron (js/require "electron"))
(def fs (js/require "fs"))

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

(rf/reg-event-fx
  :load-file
  (fn [{:keys [db]} [_ file-name]]
    (js/console.log "Loading: " file-name)
    {:db (assoc db ::loaded-file file-name
                   ::model-data nil)
     ::read-and-parse-file {:path file-name
                            :on-success [::model-data-loaded]
                            :on-failure [:not-yet-implemented]}
     }))

;; Temporary:
(rf/reg-event-db
  ::unload-file
  (fn [db _]
    (dissoc db ::loaded-file ::model-data)))

(rf/reg-event-db
  ::model-data-loaded
  (fn [db [_ model-data :as event]]
    (assoc db ::model-data model-data)))

(rf/reg-sub :model-data
            (fn [db _]
              (::model-data db)))

(rf/reg-sub
  :loaded-file
  (fn [db _]
    (::loaded-file db)))

(rf/reg-fx
  ::read-and-parse-file
  (fn [options]
    (let [{:keys [path on-success on-failure]} options]
      (.readFile fs path
                 (fn [err data]
                   (rf/dispatch
                     (if err
                       (conj on-failure err)
                       ;; Putting a try here causes a Figwheel error!
                       ;; The data may be a string or a Node Buffer
                       (conj on-success (edn/read-string (str data))))))))))

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
                           (rf/dispatch [:load-file (first filename)]))))))

(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))

