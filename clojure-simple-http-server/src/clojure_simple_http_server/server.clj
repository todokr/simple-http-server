(ns clojure-simple-http-server.server
  (:require [clojure.java.io :as io]
            [clojure.core.async :refer [thread]]
            [clojure-simple-http-server.request-parser :as parser]
            [clojure-simple-http-server.request-handler :as handler]
            [clojure-simple-http-server.response-writer :as writer])
  (:import (java.net InetSocketAddress ServerSocket)))

(defn run!
  "Runs `handler` with given `config`"
  [handler {:keys [host port backlog error-pages] :as config}]
  (let [address (new InetSocketAddress host)
        server-socket (new ServerSocket)]
    (.bind server-socket address backlog)
    (prn (str "Server Started: " (.getInetAddress server-socket) ":" (.getLocalPort server-socket)))
    (prn {:config config})
    (while true
      (let [socket (.accept server-socket)]
        (thread
          (with-open [out (io/output-stream (.getOutputStream socket))
                      in (io/input-stream (.getInputStream socket))]
            (-> (parser/from-input-stream in)
                (handler/handle-request)
                (writer/write out))))))))
