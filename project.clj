(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "A series of tutorials on ClojureScript"
  :url "https://github.com/magomimmo/modern-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :pom-addition [:developers [:developer
                              [:id "magomimmo"]
                              [:name "Mimmo Cosenza"]
                              [:url "https://github.com/magomimmo"]
                              [:email "mimmo.cosenza@gmail.com"]
                              [:timezone "+2"]]]

  :min-lein-version "2.1.2"

  ;; clojure source code path
  :source-paths ["src/clj" "src/cljs"]

  :dependencies [
                  [org.clojure/clojure "1.5.1"]
                  [compojure "1.1.6"]
                  [org.clojure/clojurescript "0.0-2069"]
                  [domina "1.0.3-SNAPSHOT"] ]

  :plugins [
             [lein-cljsbuild "1.0.0"]
             [lein-ring "0.8.8"] ]

  ;; ring tasks configuration
  :ring {:handler modern-cljs.core/handler}

  ;; cljsbuild tasks configuration
  :cljsbuild {:builds
               ;; login.js build
               {:login
                 {:source-paths ["src/cljs/login"]
                   :compiler
                   {:output-to "resources/public/js/login.js"
                     :optimizations :whitespace
                     :pretty-print true}}
                 ;; shopping.js build
                 :shopping
                 {:source-paths ["src/cljs/shopping"]
                   :compiler
                   {:output-to "resources/public/js/shopping.js"
                     :optimizations :whitespace
                     :pretty-print true } } } } )
