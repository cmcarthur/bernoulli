(ns bernoulli.impl.throttler-impl
  (:require [com.climate.claypoole :as threadpool]
            [clj-time.core :as time])
  (:refer-clojure :exclude [delay]))

(defn now
  []
  (System/currentTimeMillis))

(defn delay
  [this]
  (let [n (now)
        time-since-last-call-ms (- n @(:last-call-time-ms this))
        delay-time-ms (max 0 (- (:time-ms this) time-since-last-call-ms))]
    (reset! (:last-call-time-ms this) (+ n delay-time-ms))
    (when (not= 0 delay-time-ms)
      (deref (threadpool/future (:pool this)
                                (Thread/sleep delay-time-ms))))))
