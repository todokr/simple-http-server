(defproject clojure-simple-http-server "0.1.0-SNAPSHOT"
  :description "Simple HTTP Server written in Clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/core.async "1.3.610"]]
  :main ^:skip-aot clojure-simple-http-server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
