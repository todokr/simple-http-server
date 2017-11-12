(ns clojure-simple-http-server.mime-detector
  (:require [clojure.edn :as edn]
            [clojure.string :as s]
            [clojure.java.io :as io]))

(defn detect [path]
  (let [ext (last (s/split (str path) #"\."))]
    (-> (io/resource "mimes.edn")
        (slurp)
        (edn/read-string)
        (get (keyword ext) "application/octet-stream"))))
