(ns rill.event-store-benchmark
  (:require [clojure.tools.cli :as cli]
            [rill.event-store :refer [append-events]]
            [rill.event-store.memory :refer [memory-store]]
            [rill.event-store.psql :refer [psql-event-store]]
            [rill.event-store.psql.tools :as psql-tools]
            [rill.message :refer [defmessage]]
            [schema.core :as s])
  (:gen-class))

(def cli-options
  [["-e" "--events num-events" "Number of events to generate per stream"
    :default 10 :parse-fn #(Long/parseLong %)]
   ["-s" "--streams num-streams" "Number of streams to create"
    :default 100 :parse-fn #(Long/parseLong %)]
   ["-U" "--user username" "Username for postgres"]
   ["-P" "--password password" "Password for postgres"]
   ["-h" "--host hostname" "Hostname for postgres"
    :default "localhost"]
   ["-p" "--port port" "Port number for postgres"
    :default "5432"]
   ["-d" "--database dbname" "Database name for postgres. Any data in this db will be deleted!"]])

(defmessage Tick
  :stream-number s/Int
  :event-number s/Int)

(defn get-postgres-store
  [])

(defn get-store
  [connector config]
  (case connector
    "memory" (memory-store)
    "postgres" (do (psql-tools/load-schema! config)
                   (psql-event-store (psql-tools/connection config)))))

(defn run-benchmark
  [store {:keys [events streams]}]
  (time (doseq [s (range streams) e (range events)]
          (assert (append-events store (str s) (dec e) [(tick s e)])))))

(defn -main
  [connector & args]
  (let [{:keys [errors options summary]} (cli/parse-opts args cli-options)]
    (when errors
      (print errors)
      (print summary)
      (System/exit 1))
    (run-benchmark (get-store connector options) options)))



