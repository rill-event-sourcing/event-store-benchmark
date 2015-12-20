(defproject rill-event-sourcing/event-store-benchmark "0.2.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [rill-event-sourcing/rill.event_store.psql "0.2.1"]
                 [rill-event-sourcing/rill.event_store.memory "0.2.1"]]
  :main ^:skip-aot rill.event_store_benchmark
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
