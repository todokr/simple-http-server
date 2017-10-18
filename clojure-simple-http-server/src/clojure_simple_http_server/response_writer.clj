(ns clojure-simple-http-server.response-writer
  (:require [clojure-simple-http-server.utils.config-loader]
            [clojure-simple-http-server.utils.http-token :refer [CRLF]]))

(defn write [res out]
  (let [header (str
                "HTTP/1.1 200 OK" CRLF
                "Content-Length: " (count (:body res)) CRLF
                "Content-Type: " (:mime (:content-type res)) CRLF
                "Connection: " (or (:connection res) "Close") CRLF
                CRLF)]
    (.write out (.getBytes header))
    (.write out (:body res))
    (.write out (.getBytes CRLF))))
