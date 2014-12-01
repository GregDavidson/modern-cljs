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

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2069"]
                 [compojure "1.1.6"]
                 [domina "1.0.3-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.8"]]

  ;; ring tasks configuration
  :ring {:handler modern-cljs.core/handler}

  :cljsbuild {
      :builds {
            :dev {
                   :source-paths ["src/cljs" "src/brepl"]
                   :compiler {
                       :output-to "resources/public/js/modern_dbg.js"
                       :optimizations :whitespace
                       :pretty-print true } }
            :pre-prod {
                    :source-paths ["src/cljs" "src/brepl"]
                    :compiler {
                        :output-to "resources/public/js/modern_pre.js"
                        :optimizations :simple
                                :pretty-print false } }
            :prod {
                    :source-paths ["src/cljs"]
                    :compiler {
                            :output-to "resources/public/js/modern.js"
                            :optimizations :advanced
                            :pretty-print false} } } } )
