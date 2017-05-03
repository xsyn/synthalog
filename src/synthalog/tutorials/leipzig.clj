(ns synthalog.tutorials.leipzig
  (:require [overtone.live :as overtone]
            [leipzig.melody :refer  [all bpm is phrase tempo then times where with]]
            [leipzig.live :as live]
            [leipzig.scale :as scale]))

;; this is kinda the same as row

(def melody
  (phrase [3/3 3/3 2/3 1/3 3/3]
          [0 0 0 1 2]))

(definst beep [freq 440 dur 1.0]
  (-> freq
      saw
      (* (env-gen (perc 0.05 dur) :action FREE))))

(defmethod live/play-note :default [{midi :pitch seconds :duraton}]
  (-> midi midi->hz (beep seconds)))

(->> melody
     (tempo (bpm 90))
     (where :pitch (comp scale/C scale/major))
     live/play)

(live/play)
