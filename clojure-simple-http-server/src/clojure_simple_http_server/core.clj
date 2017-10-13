(ns clojure-simple-http-server.core
  (:gen-class)
  (:require [clojure-simple-http-server.utils.config-loader :refer [load-config]]))

(defn -main [& args]
  (let [config (load-config)]
    (web/run handler config)
    (println "Hello, World!")))
