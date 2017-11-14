(ns clojure-simple-http-server.request-parser
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn from-input-stream [in]
  (let [[method target-path http-version]
        (-> (io/reader in)
            (.readLine)
            (s/split #"\s"))]
    {:method method
     :target-path target-path
     :http-version http-version}))
