(ns troller.views.chat
  (:require [re-frame.core :as rf]
            [re-com.core :as rc]
            [troller.views.util :refer [wrapper]]))


(defn component []
  (let [messages (rf/subscribe [:chat/all])]
    (fn []
      [:<>
       [rc/title :label "Chat" :level :level1]
       [rc/v-box
        :gap "1em"
        :children
        (doall
         (for [msg @messages]
           (let [{:keys [image] :as profile} @(rf/subscribe [:profiles/by-id (:s msg)])]
             [rc/h-box
              :class "chat-message"
              :children [[:img.chat-image {:src image}]
                         [:div.message (:message msg)]]])
           ))
        ]])))


(defn index []
  [wrapper component])
