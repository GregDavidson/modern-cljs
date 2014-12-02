(ns modern-cljs.login
  (:require [domina :refer [by-id value]]
            [domina.events :refer [listen! prevent-default]]))

(defn validate-form [e]
  (let [ email (value (by-id "email"))
         password (value (by-id "password")) ]
    (if (or (empty? email) (empty? password))
      (do
        (prevent-default e)
        (js/alert "Please, complete the form!") )
      true ) ) )

;; define the function to attach validate-form to onsubmit event of
;;the form
(defn ^:export init []
  ;; verify that js/document exists and that it has a getElementById
  ;; property
  (if (and js/document (aget js/document "getElementById"))
    ;; get loginForm by element id and set its onsubmit property to
    ;; our validate-form function
    (listen! (by-id "submit") :click validate-form) ) )
