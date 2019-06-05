(ns troller.io
  (:require ["mqtt/dist/mqtt" :as mqtt]
            [com.stuartsierra.component :as component]
            [clojure.core.async :refer [<! put! chan close!] :refer-macros [go-loop]]
            [cljs.reader :refer [read-string]]
            [re-frame.core :as rf]
            [taoensso.timbre :as log]))


(defn start-channel [mqtt-opts topics c-data-in]
  (log/info "MQTT Client connecting...")
  (let [client (mqtt/connect (:uri mqtt-opts) (clj->js (:opts mqtt-opts)))]
    (when client
      (.on client
           "connect"
           (fn []
             (log/info "MQTT Client connected")
             (doseq [topic topics]
               (log/info "MQTT Client subscribing to topic" topic)
               (.subscribe client
                           (str topic)
                           (fn [error]
                             (if error
                               (log/error error))))))))
    (.on client
         "message"
         (fn [topic message]
           (try
             (put! c-data-in (js->clj (.parse js/JSON (.toString message "UTF-8")) :keywordize-keys true))
             (catch js/Error e
               (log/error e)))))
    client))

(defn stop-channel [client topic]
  (try
    (log/info "MQTT Client unsubscribing to topic" topic)
    (.unsubscribe client topic)
    (log/info "Disconnecting MQTT Client...")
    (.end client)
    (log/info "MQTT Client disconnected")
    (catch js/Error e
      (log/error e))))

(defn- handle-outgoing-data [uuid client c-data-out]
  (go-loop []
    (when-let [value (<! c-data-out)]
      (try
        (.publish client
                  "troller/chat"
                  (.stringify js/JSON (clj->js value))
                  #js {:qos 2})
        (catch js/Error e
          (log/error e)))
      (recur))))

(defprotocol IIO
  (push [io data]))

(defrecord IoManager [mqtt started? client c-data-in c-data-out uuid]
  component/Lifecycle
  (start [this]
    (if started?
      this
      (do (log/info "Starting IoManager")
          (let [c-data-in (chan 1024)
                c-data-out (chan 1024)
                uuid (random-uuid)
                client (start-channel mqtt #{"troller/chat"} c-data-in)
                new-this (assoc this
                                :c-data-in c-data-in
                                :c-data-out c-data-out
                                :client client
                                :started? true)]
            (handle-outgoing-data "troller/chat" client c-data-out)
            (rf/reg-event-fx :io/out (fn [_ [_ msg]]
                                       (push new-this msg)
                                       nil))
            new-this))))
  (stop [this]
    (if-not started?
      this
      (do (log/info "Stopping IoManager")
          (stop-channel client "troller/chat")
          (close! c-data-in)
          (close! c-data-out)
          (assoc this
                 :c-data-out nil
                 :c-data-in nil
                 :client nil
                 :started? false))))

  IIO
  (push [this data]
    (put! c-data-out data)))

(defn io-manager [settings]
  (map->IoManager settings))
