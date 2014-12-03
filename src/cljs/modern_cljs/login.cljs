(ns modern-cljs.login
  (:require-macros [hiccups.core :refer [html]])
  (:require
    [clojure.set :refer [subset?]]
    [domina :refer [by-id by-class value append! prepend! destroy! attr log sel add-class!]]
    [domina.events :refer [listen! prevent-default]]
    [hiccups.runtime :as hiccupsrt] ) )

(def field-ids ["email" "password"])

(def node-ids (conj field-ids "loginForm" "submit"))

(declare id-node) ;; defined in (init) below

(defn id-value [id] (value (id-node id)))

(defn validate-field [id]
  (destroy! (by-class id))
  (if (not (re-matches (re-pattern (attr id :pattern)) (id-value id)))
    (do
      (prepend! (id-node "loginForm") (html [:div.help (str "Bad " id)]))
      (add-class! (sel "#loginForm>div.help") id)
      false )
    true ) )

(defn validate-form [evt]
  (if (some #(empty? (id-value %)) field-ids)
    (do
      (prevent-default evt)
      (destroy! (by-class "help"))
      (append! (id-node "loginForm") (html [:div.help "Please complete the form"])) )
    (if (every? validate-field field-ids)
      true
      (prevent-default evt) ) ) )

(defn ^:export init []
  (if (not (and js/document (aget js/document "getElementById")))
    (js/alert "Expected document.getElementById")
    (do
      (assert (every? #(by-id %) node-ids)) ; check node-ids against document
      (def id-node (zipmap node-ids (map #(by-id %) node-ids)))
      (listen! (id-node "submit") :click validate-form)
      (map #(listen! (id-node %) :blur (fn [evt] (validate-field %))) field-ids) ) ) )
