(ns bernoulli.impl.throttler-impl
  (:require [com.climate.claypoole :as threadpool])
  (:refer-clojure :exclude [delay]))

(defn now
  []
  (System/currentTimeMillis))

(defn delay
  [this]
  (let [n (now)
        last-call-time-ms @(:last-call-time-ms this)
        time-since-last-call-ms (- n last-call-time-ms)
        delay-time-ms (max 0 (- (:time-ms this) time-since-last-call-ms))]
    (if-not (compare-and-set! (:last-call-time-ms this) last-call-time-ms (+ n delay-time-ms))
      (recur this)
      (when (not= 0 delay-time-ms)
        (deref (threadpool/future (:pool this)
                                  (Thread/sleep delay-time-ms)))))))
