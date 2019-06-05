(ns troller.core
  (:require [reagent.core :as reagent :refer [atom]]
            [troller.init]
            [troller.views]))

(defn start []
  (reagent/render-component [troller.views/index]
                            (. js/document (getElementById "app"))))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (troller.init/init)
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
