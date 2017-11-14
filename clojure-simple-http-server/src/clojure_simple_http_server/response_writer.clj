(ns clojure-simple-http-server.response-writer
  (:import (java.time.format DateTimeFormatter)
           (java.time OffsetDateTime ZoneOffset)))

(def SP " ")
(def CRLF "\r\n")

(defn write [{:keys [status reason-phrase content-type body]} out]
  (let [header (str
                "HTTP/1.1" SP status SP reason-phrase CRLF
                "Date:" (.format (OffsetDateTime/now ZoneOffset/UTC) DateTimeFormatter/RFC_1123_DATE_TIME)
                "Content-Length:" (count body) CRLF
                "Content-Type:" content-type CRLF
                "Connection: Close" CRLF
                CRLF)]
    (.write out (.getBytes header))
    (.write out body)
    (.write out (.getBytes CRLF))
    (.flush out)))
