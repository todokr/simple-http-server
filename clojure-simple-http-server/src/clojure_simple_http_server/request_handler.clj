(ns clojure-simple-http-server.request-handler
  (:require [clojure.string :as s]
            [clojure.java.io :as io])
  (:import (java.time OffsetDateTime ZoneOffset)
           (java.nio.file Paths Path Files LinkOption)))

(defn- exists? [^Path path]
  (Files/exists path (into-array LinkOption nil)))

(defn- resolve-public [path-str]
  (Paths/get "public" (into-array [path-str])))

(defn- path->bytes [^Path path]
  (with-open [stream (io/input-stream (.toFile path))]
    (byte-array (take-while #(not= -1 %) (repeatedly #(.read stream))))))

(defn- ^Path normalize-path [^String url]
  (if-let [path-str url]
   (let [path (.normalize (resolve-public path-str))]
    (if (Files/isDirectory path (into-array LinkOption nil))
      (.resolve path "index.html")
      path))
   nil))

(def reason-phrases {200 "OK"
                     400 "BadRequest"
                     403 "Forbidden"
                     404 "NotFound"})

(defn- handle-ok [path]
  (let [body (path->bytes path)]
    {:status 200
     :reason-phrase (reason-phrases 200)
     :content-type "text/html"
     :content-length (count body)
     :body body}))

(defn- handle-not-ok [status]
  {:status status
   :reason-phrase (reason-phrases status)
   :content-type "text/html"
   :body (path->bytes (resolve-public (str status ".html")))})

(defn- resolve [path]
  (cond
    (nil? path) (handle-not-ok 400)
    (not (.startsWith path public-dir-path)) (handle-not-ok 403)
    (not (exists? path)) (handle-not-ok 404)
    :else (handle-ok path)))

(defn handle-request [req]
  (-> (:url req)
      (normalize-path)
      (resolve)))
