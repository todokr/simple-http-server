(ns clojure-simple-http-server.response-writer
  (:import (java.time.format DateTimeFormatter)
           (java.time OffsetDateTime ZoneOffset)))

(def sp " ")
(def crlf "\r\n")

(defn write [{:keys [status reason-phrase content-type body]} out]
  (let [header (str
                "HTTP/1.1" sp status sp reason-phrase crlf
                "Date:" (.format (OffsetDateTime/now ZoneOffset/UTC) DateTimeFormatter/RFC_1123_DATE_TIME)
                "Content-Length:" (count body) crlf
                "Content-Type:" content-type crlf
                "Connection: Close" crlf
                crlf)]
    (doto out
      (.write (.getBytes header))
      (.write body)
      (.write (.getBytes crlf))
      (.flush))))
