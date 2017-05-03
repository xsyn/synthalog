(ns synthalog.bach.invention-1
  (:require [overtone.live :as overtone]
            [leipzig.live :as live]
            [leipzig.scale :as scale]
            [leipzig.melody :refer [all bpm is phrase tempo then times where with]]))

(overtone/definst beep [freq 440 dur 1.0]
  (-> freq
      overtone/saw
      (* (overtone/env-gen (overtone/perc 0.05 dur) :action overtone/FREE))))

(def a
  (phrase [3/3 3/3 3/3 3/3]
          [0 1 2 3]))

(def b
  (phrase [3/3 3/3 3/3 3/3]
          [2 1 0 4]))

(def long-a
  (phrase [3/3 3/3 3/3 3/3 3/3 3/3 3/3 3/3]
          [0 1 2 3 2 1 0 4]) )

(defmethod live/play-note :default [{midi :pitch seconds :duration}]
  (-> midi
      overtone/midi->hz
      (beep seconds)))

(def melody
  (into a b))

(comment

  (->> #_melody
       long-a
       (tempo (bpm 120))
       (where :pitch (comp scale/C scale/major))
       live/play)

  )
