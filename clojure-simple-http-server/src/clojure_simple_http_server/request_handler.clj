(ns clojure-simple-http-server.request-handler
  (:import (java.time OffsetDateTime ZoneOffset)))

(defn handle-request [req error-pages]
  {:status 200
   :reason-phrase "OK"
   :date (. OffsetDateTime (now ZoneOffset/UTC))
   :last-modified (. OffsetDateTime (now ZoneOffset/UTC))
   :content-type "text/html"
   :body (.getBytes "Hello Server!")})
