(ns synthalog.tutorials.scales
  (:require [overtone.live :refer :all]))

;; Scales take a root note and type of scale as arguments

(scale :C3 :major)

(def scale-degrees [:i :ii :iii :iv :v :vi :vii])

(degrees->pitches scale-degrees :dorian :E3)

;; Scale dgrees can be augmented by either + or - to denote octave above or below the root.
;; They can be flattend or shrpened using # or b
;; e.g.:ib+ would equal Cb4 in major scale starting at C3

;; :_ denotes rests

(def scale-degrees [:vi :vii :i+ :_ :vii :_ :i+ :vii :vi :_ :vii :_])
(def pitches (degrees->pitches scale-degrees :dorian :C4))

(defn play [time notes sep]
  (let [note (first notes)]
    (when note
      (at time (saw (midi->hz note))))
    (let [next-time (+ time sep)]
      (apply-at next-time play [next-time (rest notes) sep]))))

(play 0 pitches 10)
