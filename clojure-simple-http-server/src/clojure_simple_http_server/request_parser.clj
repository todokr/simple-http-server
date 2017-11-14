(ns clojure-simple-http-server.request-parser
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn from-input-stream [in]
  (let [[method target-path http-version]
        (when-let [line (.readLine (io/reader in))]
          (s/split line #"\s"))]
    {:method method
     :target-path target-path
     :http-version http-version}))
