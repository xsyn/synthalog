(ns synthalog.tutorials.row
  (:require [overtone.live :as overtone]
            [leipzig.live :as live]
            [leipzig.scale :as scale]
            [leipzig.melody :refer [all bpm is phrase tempo then times where with]]))

(def melody
  (phrase [3/3 3/3 2/3 1/3 3/3]
          [0     0   0   1   2]))

(overtone/definst beep [freq 440 dur 1.0]
  (-> freq
      overtone/saw
      (* (overtone/env-gen (overtone/perc 0.05 dur) :action overtone/FREE))))

(defmethod live/play-note :default [{midi :pitch seconds :duration}]
  (-> midi
      overtone/midi->hz
      (beep seconds)))

(comment

  (->> melody
       (tempo (bpm 90))
       (where :pitch (comp scale/C scale/major))
       live/play)

  (overtone/examples)
  (overtone/demo 10 (overtone/membrane-hexagon :mouse))

  )
