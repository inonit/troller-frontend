(ns troller.views.login
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [re-com.core :as rc]))


(defn index []
  (let [username (r/atom "")
        password (r/atom "")]
    (fn []
      [rc/v-box
       :justify :center
       :align :center
       :children [[:img {:src "/img/troll.png"
                         :style {:width "300px"
                                 :height "300px"}}]
                  [rc/title :label "Troller" :level :level1]
                  [rc/title :label "Dating for trolls" :level :level3]
                  [rc/box
                   :style {:margin-top "2em"}
                   :child [rc/v-box
                           :gap "0.5em"
                           :children [[rc/input-text
                                       :model username
                                       :on-change #(reset! username %)
                                       :placeholder "Username"]
                                      [rc/input-password
                                       :model password
                                       :on-change #(reset! password %)
                                       :placeholder "Password"]
                                      [rc/h-box
                                       :justify :between
                                       :children [[rc/button
                                                   :label "Login"
                                                   :class "btn-primary"
                                                   :on-click #(rf/dispatch [:data/push
                                                                            "/api/token-auth/"
                                                                            {:username @username
                                                                             :password @password}
                                                                            :user/login])]
                                                  [rc/button
                                                   :label "Sign up"
                                                   :class "btn-info"]]]]]]]])))
