(ns modern-cljs.login
  (:require-macros [hiccups.core :refer [html]])
  (:require
    [clojure.set :refer [subset?]]
    [domina :refer [by-id by-class value append! prepend! destroy! log sel add-class!]]
    [hiccups.runtime :as hiccupsrt]
    [domina.events :refer [listen! prevent-default]] ) )

(def node-ids ["email" "password" "loginForm" "submit"])

(declare id-node) ;; defined in (init) below

(defn id-value [id] (value (id-node id)))

(def id-re {
  "password" #"^(?=.*\d).{4,8}$" ;;; 4 to 8, at least one numeric digit
  "email"  #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$" })

(assert (subset? (set (keys id-re)) (set node-ids)))
  
(defn validate-field [id]
  (destroy! (by-class id))
  (if (not (re-matches (id-re id) (id-value id)))
    (do
      (prepend! (id-node "loginForm") (html [:div.help (str "Bad " id)]))
      (add-class! (sel "#loginForm>div.help") id)
      false )
    true ) )

(defn validate-form [evt]
  (let [ email (id-value "email")
        password (id-value "password") ]
    (if (or (empty? email) (empty? password))
      (do
        (prevent-default evt)
        (destroy! (by-class "help"))
        (append! (id-node "loginForm") (html [:div.help "Please complete the form"])) )
      (if (and
            (validate-field "email")
            (validate-field "password") )
        true
        (prevent-default evt) ) ) ) )

(defn ^:export init []
  (if (not (and js/document (aget js/document "getElementById")))
    (js/alert "Expected document.getElementById")
    (do
      (assert (every? #(by-id %) node-ids)) ; check node-ids against document
      (def id-node (zipmap node-ids (map #(by-id %) node-ids)))
      (listen! (id-node "submit") :click validate-form)
      (listen! (id-node "email") :blur (fn [evt] (validate-field "email")))
      (listen! (id-node "password") :blur (fn [evt] (validate-field "password"))) ) ) )
