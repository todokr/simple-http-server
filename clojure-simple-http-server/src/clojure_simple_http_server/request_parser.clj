(ns clojure-simple-http-server.request-parser
  (:require [clojure.java.io :as io]))

(def pattern #"(.+) (.+) (.+)")

(defn from-input-stream [in]
  (when-let [line (.readLine (io/reader in))]
    (zipmap [:method :target-path :http-version]
            (rest (re-find pattern line)))))
