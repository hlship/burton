(defproject burton "0.1.0-SNAPSHOT"
  :source-paths ["src/dev"
                 "src/script"
                 "src/tools"
                 "src/main"
                 "src/ui"]
  :description "Explore dependencies visually."
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [figwheel-sidecar "0.5.15"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.5"]
                 [figwheel "0.5.15"]
                 [ring/ring-core "1.6.3"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.15"]]

  :clean-targets ^{:protect false} ["resources/main.js"
                                    "resources/public/js/electron-dev"
                                    "resources/public/js/ui.*"
                                    "resources/public/js/ui-out"]
  :cljsbuild
  {:builds
   [{:source-paths ["src/main" "src/script"]
     :id "electron-dev"
     :compiler {:output-to "resources/main.js"
                :output-dir "resources/public/js/electron-dev"
                :optimizations :simple
                :pretty-print true
                :cache-analysis true}}
    {:source-paths ["src/ui" "src/dev" ]
     :id "frontend-dev"
     :compiler {:output-to "resources/public/js/ui.js"
                :output-dir "resources/public/js/ui-out"
                :source-map true
                :asset-path "js/ui-out"
                :optimizations :none
                :cache-analysis true
                :main "dev.core"}}
    {:source-paths ["src/main"]
     :id "electron-release"
     :compiler {:output-to "resources/main.js"
                :output-dir "resources/public/js/electron-release"
                :optimizations :advanced
                :pretty-print true
                :cache-analysis true
                :infer-externs true}}
    {:source-paths ["src/ui"]
     :id "frontend-release"
     :compiler {:output-to "resources/public/js/ui.js"
                :output-dir "resources/public/js/ui-release-out"
                :source-map "resources/public/js/ui.js.map"
                :optimizations :advanced
                :cache-analysis true
                :infer-externs true
                :main "burton.ui"}}]}
  :figwheel {:http-server-root "public"
             :css-dirs ["resources/public/css"]
             :ring-handler tools.figwheel-middleware/app
             :server-port 3449})
