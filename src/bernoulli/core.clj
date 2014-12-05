(ns bernoulli.core
  (:require [bernoulli.throttler]))

(defn throttler
  [{:keys [time-ms] :or {time-ms nil}}]
  {:pre [(not (nil? time-ms))]}
  )
