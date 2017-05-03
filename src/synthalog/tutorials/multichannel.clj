(ns synthalog.tutorials.multichannel
  (:require [overtone.live :refer :all]))

(defsynth sin1 [freq 440]
  (out 0 (sin-osc freq)))

(defsynth sin2 [freq 440]
  (out 0 (sin-osc freq))
  (out 1 (sin-osc freq)))

(defsynth sin-square2 [freq 440]
  (out 0 (* [0.5 0.5] (+ (square (* 0.5 freq)) (sin-osc freq)))))

(comment
  (sin1)
  (sin2)

  (stop-all)

  )
