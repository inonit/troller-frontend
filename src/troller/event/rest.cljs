(ns troller.event.rest
  (:require [re-frame.core :as rf]
            [troller.user :refer [token]]))

(rf/reg-event-db :troller/view (fn [db [_ view]]
                                 (assoc db :troller/view view)))

(rf/reg-event-db :user/status (fn [db [_ status]]
                                (assoc db :user/status status)))

(rf/reg-event-db :user/profile (fn [db [_ profile]]
                                 (assoc db :user/profile profile)))

(defn- into-map [values id]
  (->> values
       (map (fn [data]
              [(get data id) data]))
       (into {})))

(rf/reg-event-db :data/profiles (fn [db [_ profiles]]
                                  (assoc db
                                         :data/profiles (into-map profiles :id))))

(rf/reg-event-db :data/likes (fn [db [_ likes]]
                                  (assoc db
                                         :data/likes (into-map likes :id))))

(rf/reg-event-db :data/messages (fn [db [_ messages]]
                                  (assoc db
                                         :data/messages (into-map messages :id))))
