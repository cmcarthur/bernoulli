(defproject bernoulli "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.8.0"]
                 [com.climate/claypoole "0.3.3"]]
  :profiles {:test {:plugins [[lein-midje "3.1.3"]]
                    :dependencies [[midje "1.6.3"]]}}
  :aliases {"midje" ["with-profile" "test" "midje" ":print-facts"]}
  :main bernoulli.throttler)
