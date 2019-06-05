(ns troller.views.profile
  (:require [re-frame.core :as rf]
            [re-com.core :as rc]
            [troller.views.util :refer [wrapper]]))

(defmulti get-data (fn [profile k] k))
(defmethod get-data :image [profile k]
  [:img {:src (str "http://127.0.0.1:8000" (get profile k))}])
(defmethod get-data :bio [profile k]
  [:pre (get profile k)])
(defmethod get-data :default [profile k]
  (get profile k))

(defn component []
  (let [profile @(rf/subscribe [:user/profile])]
    [rc/box
     :child [:table.table.table-striped>tbody
             (for [[title k] [["Username" :username]
                              ["First name" :first_name]
                              ["Last name" :last_name]
                              ["Profile image" :image]
                              
                              ["Bio" :bio]]]
               ^{:key title}
               [:tr
                [:th title]
                [:td (get-data profile k)]])]]))


(defn index []
  [wrapper component])
