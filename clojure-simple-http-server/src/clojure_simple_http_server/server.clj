(ns clojure-simple-http-server.server
  (:require [clojure.java.io :as io])
  (:import (java.net InetSocketAddress ServerSocket)))

(defn run!
  "Runs `handler` with given `config`"
  [handler config]
  (let [conf (select-keys config [:host :port :backlog :error-pages])
        address (new InetSocketAddress (:host conf))
        server-socket (new ServerSocket)]
    (.bind server-socket address backlog)
    (prn (str "Server Started: " (.getInetAddress server-socket) ":" (.getLocalPort server-socket)))
    (prn {:config config})
    (while true
      (let [socket (.accept server-socket)]
        (thread
          (with-open [out (io/output-stream (.getOutputStream))
                      in (io/reader (.getInputStream))]
            (-> (parser/parse-request (slurp in))
                (handler/handle-request (:error-pages conf))
                (write out))))))))
