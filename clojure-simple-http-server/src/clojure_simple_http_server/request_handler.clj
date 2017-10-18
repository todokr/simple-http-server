(ns clojure-simple-http-server.request-handler)

(defn handle-request [req error-pages]
  {:status 200
   :reason-phrase "OK"
   :content-type "text/html"
   :body (.getBytes "Hello Server!")})
