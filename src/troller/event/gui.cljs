(ns troller.event.gui
  (:require [re-frame.core :as rf]
            [troller.user :refer [token]]))

(rf/reg-event-db :menu/status (fn [db [_ status]]
                                (assoc db :menu/status status)))
(rf/reg-event-db :menu.status/flip (fn [db _]
                                     (if (= :active (:menu/status db))
                                       (assoc db :menu/status :inactive)
                                       (assoc db :menu/status :active))))
(rf/reg-event-fx :troller/view (fn [{:keys [db]} [_ view]]
                                 (merge
                                  (if (= view :view/messages)
                                    {:dispatch [:data/pull "/api/messages/list" {} :data/messages]})
                                  {:db (assoc db :troller/view view)})))

(rf/reg-event-db :user/logout (fn [{:keys [db]} _]
                                (reset! token "")
                                (-> db
                                    (dissoc :user/profile
                                            :data/profiles
                                            :data/likes
                                            :data/messages)
                                    (assoc :user/status :logged-out))))
