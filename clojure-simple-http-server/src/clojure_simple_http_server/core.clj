(ns clojure-simple-http-server.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async]
            [clojure-simple-http-server.request-parser :as request-parser]
            [clojure-simple-http-server.request-handler :as request-handler]
            [clojure-simple-http-server.response-writer :as response-writer])
  (:import (java.net ServerSocket)))

(def port 8080)

(defn -main [& args]
  (let [server-socket (new ServerSocket port)]
    (prn (str "HTTP Server Start! Listening at " port "!"))
    (while true
      (let [socket (.accept server-socket)]
        (async/thread
          (with-open [s socket
                      in (io/input-stream (.getInputStream s))
                      out (io/output-stream (.getOutputStream s))]
            (-> (request-parser/from-input-stream in)
                (request-handler/handle-request)
                (response-writer/write out))))))))
