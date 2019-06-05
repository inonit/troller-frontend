(ns troller.event.chat
  (:require [re-frame.core :as rf]))


(rf/reg-event-db :chat.messages/new (fn [{:keys [:chat/messages] :as db} [_ msg]]
                                      (assoc db :chat/messages (conj messages msg))))

