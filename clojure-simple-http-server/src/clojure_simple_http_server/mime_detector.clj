(ns clojure-simple-http-server.mime-detector
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(def mime-map
  (-> (io/resource "mimes.edn")
        (slurp)
        (edn/read-string)))

(defn detect [path]
  (let [ext (last (str/split (str path) #"\."))]
    (get mime-map ext "application/octet-stream")))
