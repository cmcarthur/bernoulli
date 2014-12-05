(ns bernoulli.test.throttler
  (:require [midje.sweet :refer :all]
            [bernoulli.throttler :as throttler]))

(facts "about `bernoulli.throttler`"
       (fact "throttles a single thread properly"
             (let [t (throttler/throttler {:time-ms 10})
                   start-time (System/currentTimeMillis)
                   work (doseq [x (range 6)]
                          (throttler/delay t))
                   end-time (System/currentTimeMillis)]
               (>= 70 (- end-time start-time) 50) => true))

       (fact "throttles multiple threads properly"
             (let [t (throttler/throttler {:time-ms 10})
                   start-time (System/currentTimeMillis)
                   work-a (future (doseq [x (range 3)]
                                    (throttler/delay t)))
                   work-b (future (doseq [x (range 3)]
                                    (throttler/delay t)))
                   work-done (dorun (map deref [work-a work-b]))
                   end-time (System/currentTimeMillis)]
               (>= 80 (- end-time start-time) 60) => true))

       (fact "does NOT average out rate limit speed after a slow burst"
             (let [t (throttler/throttler {:time-ms 10})
                   start-time (System/currentTimeMillis)
                   work-a (doseq [x (range 3)]
                            (throttler/delay t))
                   work-b (Thread/sleep 30)
                   work-c (doseq [x (range 3)]
                            (throttler/delay t))
                   end-time (System/currentTimeMillis)]
               (>= 90 (- end-time start-time) 70) => true)))
