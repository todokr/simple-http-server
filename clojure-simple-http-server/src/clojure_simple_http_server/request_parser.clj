(ns clojure-simple-http-server.request-parser)

(defn parse-request [req]
  {:method "GET"
   :path "/"
   :http-version "HTTP1.1"})
