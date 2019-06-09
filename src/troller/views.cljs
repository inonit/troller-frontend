(ns troller.views
  (:require [clojure.core.match :refer-macros [match]]
            [re-frame.core :as rf]
            [re-com.core :as rc]
            [troller.config]
            [troller.subs]
            [troller.views.chat :as views.chat]
            [troller.views.login :as views.login]
            [troller.views.ohnoes :as views.ohnoes]
            [troller.views.main :as views.main]
            [troller.views.messages :as views.messages]
            [troller.views.profile :as views.profile]
            [taoensso.timbre :as log]))


(defn index []
  (let [view (rf/subscribe [:troller/view])
        user-status (rf/subscribe [:user/status])]
    (fn []
      (match [@view @user-status]
             [_ :logged-out] [views.login/index]

             [:view/main _] [views.main/index]
             [:view/profile _] [views.profile/index]
             [:view/messages _] [views.messages/index]
             [:view/chat _] [views.chat/index]

             ;; if we're logged in, but haven't been able to catch anything else,
             ;; we default to ohnoes
             
             [_ _] [views.ohnoes/index]))))
