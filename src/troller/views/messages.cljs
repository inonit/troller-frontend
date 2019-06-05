(ns troller.views.messages
  (:require [re-frame.core :as rf]
            [re-com.core :as rc]
            [troller.views.util :refer [wrapper]]))

(defn list-messages []
  (let [messages (rf/subscribe [:messages/all 2])]
    (fn []
      [:ul
       (for [{:keys [id message]} @messages]
         ^{:key (rand-int 10000)}
         [:li {:style {:margin-bottom "8px"}}
          [:span.bg-primary {:style {:padding "3px 5px"
                                     :margin-right "10px"
                                     :border-radius "2px"}}
           id]
          (str (subs message 0 40) "...")])])))

(defn component []
  [rc/v-box
   :children [[rc/alert-box
               :heading "Messages"]
              [list-messages]]])

(defn index []
  [wrapper component])
