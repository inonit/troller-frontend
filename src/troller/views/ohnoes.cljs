(ns troller.views.ohnoes
  (:require [re-com.core :as rc]))

(defn index []
  [rc/v-box
   :align :center
   :children [[:img {:src "/img/troll.png"
                     :style {:width "400px"
                             :height "400px"}}]
              [rc/title :label "OH NOES!" :level :level1]]])
