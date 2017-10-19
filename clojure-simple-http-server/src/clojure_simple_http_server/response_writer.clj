(ns clojure-simple-http-server.response-writer
  (:require [clojure-simple-http-server.utils.config-loader]
            [clojure-simple-http-server.utils.http-token :refer [CRLF SP]])
  (:import (java.time.format DateTimeFormatter)))

(defn write [{:keys [status reason-phrase date content-type last-modified body]} out]
  (let [header (str
                "HTTP/1.1" SP status SP reason-phrase CRLF
                "Date:" (.format date DateTimeFormatter/RFC_1123_DATE_TIME)
                "Content-Length:" (count body) CRLF
                "Content-Type:" content-type CRLF
                "Connection: Close" CRLF
                (when last-modified
                  "Last-Modified:" (.format last-modified DateTimeFormatter/RFC_1123_DATE_TIME))
                CRLF)]
    (.write out (.getBytes header))
    (.write out body)
    (.write out (.getBytes CRLF))))
