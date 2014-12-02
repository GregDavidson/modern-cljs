(ns modern-cljs.remotes (:require
                          [modern-cljs.core :refer [handler]]
                          [compojure.handler :refer [site]]
                          [shoreleave.middleware.rpc :refer [defremote wrap-rpc]] ))

(defremote calculate- [quantity price tax discount]
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))

(defremote calculate [quantity price tax discount]
  (prn "Received: " quantity price tax discount)
  (let [x (if (and
                (number? quantity)
                (number? price)
                (number? tax)
                (number? discount) )
            (calculate- quantity price tax discount) -999 )]
    (prn "Sending: " x)
    x ) )

(def app (-> (var handler) (wrap-rpc) (site)))
