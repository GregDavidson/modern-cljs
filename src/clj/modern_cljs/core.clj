(ns modern-cljs.core
  (:use compojure.core)
  (:require [compojure.core :refer [defroutes GET]]
    [compojure.handler :refer [site]]
    [compojure.route :refer [resources not-found]]))

;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.
(defroutes app-routes
  ; to serve document root address
  (GET "/" [] "<p>Hello from compojure</p>")
  ; to server static pages saved in resources/public directory:
  (resources "/")
  (not-found "Page not found"))

;; site function create an handler suitable for a standard website,
;; adding a bunch of standard ring middleware to app-route:
(def handler
  (site app-routes))
