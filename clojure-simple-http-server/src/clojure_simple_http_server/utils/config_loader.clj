(ns clojure-simple-http-server.utils.config-loader
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn load-config []
  (edn/read-string (slurp (io/resource "config.edn"))))
