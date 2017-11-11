(ns clojure-simple-http-server.request-handler
  (:require [clojure.string :as s]
            [clojure.java.io :as io])
  (:import (java.time OffsetDateTime ZoneOffset)
           (java.nio.file Paths Files)))

(defn normalize-path [str]
  (let [path (-> (Paths/get "public" (into-array [str]))
                 (.normalize)
                 (io/as-file))]
    (if (.isDirectory path)
      (io/file path "index.html")
      path)))

(defn dispatch [req]
  (cond
    (nil? req) ::bad-request
    (not (.startsWith (normalize-path (:url req)) public-dir-path)) ::forbidden
    :else ::found))

(defmulti handle-request dispatch)

(defmethod handle-request ::forbidden [req]
  (str "forbidden: " req))

(defmethod handle-request ::not-found [req]
  (str "not-found: " req))

(defn handle-request [req error-pages]
  {:status 200
   :reason-phrase "OK"
   :date (. OffsetDateTime (now ZoneOffset/UTC))
   :last-modified (. OffsetDateTime (now ZoneOffset/UTC))
   :content-type "text/html"
   :body (.getBytes "Hello Server!")})
