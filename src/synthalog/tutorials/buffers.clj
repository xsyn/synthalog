(ns synthalog.tutorials.buffers
  (:require [overtone.live :refer :all]))

(def shaboob (sample "resources/noises/shabooba.wav"))

(shaboob)

;; Loading samples from buffers

(def shab-buf (load-sample "resources/noises/shabooba.wav"))

(defsynth reverb-on-left []
  (let [dry (play-buf 1 shab-buf)
        wet (free-verb dry 1)]
    (out 0 [wet dry])))

(reverb-on-left)

;; Samples can be downloaded directly from freesound

(def snare (freesound 26903))
(snare)
