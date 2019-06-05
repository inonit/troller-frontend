(ns troller.views.main
  (:require [re-frame.core :as rf]
            [re-com.core :as rc]
            [troller.views.util :refer [wrapper]]))


(defn component []
  (let []
    (fn []
      [rc/v-box
       :children [[rc/title :label "Main" :level :level1]
                  [rc/alert-box
                   :heading "Inspirational quote goes here"]]])))

(defn index []
  [wrapper component])
