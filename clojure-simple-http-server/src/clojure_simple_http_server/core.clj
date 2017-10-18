(ns clojure-simple-http-server.core
  (:gen-class)
  (:require [clojure-simple-http-server.utils.config-loader :refer [load-config]]
            [clojure-simple-http-server.server :as server]))

(defn -main [& args]
  (let [config (load-config)]
    (server/run! handler config)
    (println "Hello, World!")))
