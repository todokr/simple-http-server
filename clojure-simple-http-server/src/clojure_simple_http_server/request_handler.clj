(ns clojure-simple-http-server.request-handler
  (:require [clojure.string :as s]
            [clojure.java.io :as io])
  (:import (java.time OffsetDateTime ZoneOffset)
           (java.nio.file Paths Path Files LinkOption)))

; (def test-path (Paths/get "public/index.html" (into-array String nil)))

(defn- ^Path normalize-path [^String url]
  (if-let [path-str url]
   (let [path (.normalize (Paths/get "public" (into-array [url])))]
    (if (Files/isDirectory path (into-array LinkOption nil))
      (.resolve path "index.html")
      path))
   nil))

(defn- path->bytes [^Path path]
  (with-open [stream (io/input-stream (.toFile path))]
    (byte-array (take-while #(not= -1 %) (repeatedly #(.read stream))))))

(defn- handle-found [path]
  (let [body (path->bytes path)]
    {:status 200
     :content-type "text/html"
     :content-length (count body)
     :body body}))

(defn- resolve [path]
  (cond
    (nil? path) {:status 400}
    (not (.startsWith path public-dir-path)) {:status 403}
    (not (Files/exists path (into-array LinkOption nil))) {:status 404}
    :else (handle-found path)))

(def reason-phrases {200 "OK"
                     400 "BadRequest"
                     403 "Forbidden"
                     404 "NotFound"})

(defn- assoc-reason [res]
  (assoc res :reason-phrase (reason-phrases (:status res))))

(defn handle-request [req]
  (-> (:url req)
      (normalize-path)
      (resolve)
      (assoc-reason)))

; (handle-request {:url "/index.html"})
