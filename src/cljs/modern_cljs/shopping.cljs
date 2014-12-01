(ns modern-cljs.shopping
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
    [domina.events :as ev]
    [hiccups.runtime :as hiccupsrt]
    [shoreleave.remotes.http-rpc :refer [remote-callback]]
    [cljs.reader :refer [read-string]] ) )


(defn num-value [id] (js/parseFloat (dom/value (dom/by-id id))))

(defn calculate []
  (remote-callback :calculate
    (map num-value ["quantity" "price" "tax" "discount"])
    #(dom/set-value!
       (dom/by-id "total")
       (let [x %] (if (number? x) (.toFixed % 2) "No Sale!")) ) ) )

(defn add-help []
  (dom/append! (dom/by-id "shoppingForm")
               (h/html [:div.help "Click to calculate"])))

(defn remove-help []
  (dom/destroy! (dom/by-class "help")))

(defn ^:export init []
  (when (and js/document (aget js/document "getElementById"))
    (ev/listen! (dom/by-id "calc") :click calculate)
    (ev/listen! (dom/by-id "calc") :mouseover add-help)
    (ev/listen! (dom/by-id "calc") :mouseout remove-help)))
