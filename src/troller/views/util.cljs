(ns troller.views.util
  (:require [re-frame.core :as rf]
            [re-com.core :as rc]))

(defn menu []
  (let [menu? (rf/subscribe [:menu/status :active])]
    (fn []
      (if @menu?
        [:ul.list-unstyled.menu
         (for [[view title] [[:view/main "Main"]
                             [:view/profile "Profile"]
                             [:view/messages "Messages"]
                             [:view/chat "Chat"]
                             ]]
           ^{:key view} [:li {:on-click #(do (rf/dispatch [:troller/view view])
                                             (rf/dispatch [:menu/status :inactive]))} title])
         [:li {:on-click #(rf/dispatch [:user/logout])} "Log out"]]))))

(defn wrapper [body]
  (let [menu-open? (rf/subscribe [:menu/status :active])]
    (fn [body]
      (let [icon-menu (if @menu-open?
                        "zmdi-close"
                        "zmdi-menu")]
       [rc/h-box
        :justify :start
        :children [[menu]
                   [rc/v-box
                    :gap "1em"
                    :children [[rc/h-box
                                :align :center
                                :gap "0.5em"
                                :children [[rc/md-icon-button
                                            :md-icon-name icon-menu
                                            :size :larger
                                            :on-click #(rf/dispatch [:menu.status/flip])]
                                           [:img {:src "/img/troll.png" :style {:max-height "10em"
                                                                                :max-width "10em"
                                                                                :margin-right "2em"}}]
                                           [rc/title :label "Troller" :level :level1]
                                           [rc/title :label "Dating for trolls" :level :level3]]]
                               [body]]]]]))))

