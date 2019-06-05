(ns troller.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub :troller/view (fn [db _]
                            (:troller/view db)))

(rf/reg-sub :user/status (fn [db _]
                           (:user/status db)))

(rf/reg-sub :user/profile (fn [db _]
                            (:user/profile db)))

(rf/reg-sub :profiles/all (fn [db _]
                            (->> (:data/profiles db)
                                 vals
                                 (sort-by :username))))
(rf/reg-sub :profiles/by-id (fn [db [_ id]]
                              (get (:data/profiles db) id)))

(rf/reg-sub :likes/by-id (fn [db [_ id]]
                           (->> (:data/likes db)
                                vals
                                (filter (fn [like] (= (:a like) id)))
                                first)))
(rf/reg-sub :messages/all (fn [db [_ id]]
                            (->> (:data/messages db)
                                 vals
                                 (filter (fn [msg] (= (:s msg) id)))
                                 (sort-by :id)
                                 (reverse))))
(rf/reg-sub :messages/by-id (fn [db [_ id]]
                              (get (:chat/messages db) id)))

(rf/reg-sub :menu/status (fn [db [_ status]]
                           (= (:menu/status db) status)))

(rf/reg-sub :chat/all (fn [db _]
                        (:chat/messages db)))

