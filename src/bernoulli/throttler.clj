(ns bernoulli.throttler
  (:require [bernoulli.impl.throttler-impl :as impl]
            [com.climate.claypoole :as threadpool])
  (:refer-clojure :exclude [delay]))

(def ^:private default-threadpool
  (threadpool/threadpool (threadpool/ncpus)))

(defprotocol ThrottlerInterface
  (delay [this]))

(defrecord Throttler
    [time-ms
     pool
     last-call-time-ms])

(extend Throttler
  ThrottlerInterface
  {:delay impl/delay})

(defn throttler
  [{:keys [time-ms thread-pool] :or {time-ms 1000 thread-pool default-threadpool}}]
  (map->Throttler {:time-ms time-ms
                   :pool thread-pool
                   :last-call-time-ms (atom 0)}))
