(ns synthalog.bach.invention-1
  (:require [overtone.live :as overtone]
            [leipzig.live :as live]
            [leipzig.scale :as scale]
            [leipzig.melody :refer [all bpm is phrase tempo then times where with]]))

(overtone/definst beep [freq 440 dur 1.0]
  (-> freq
      overtone/saw
      (* (overtone/env-gen (overtone/perc 0.05 dur) :action overtone/FREE))))

(defn shift-pitch [m shift]
  (update m :pitch + shift))

(defn shift-phrase [p shift]
  (map #(shift-pitch % shift) p))

(def a
  (phrase (repeat 1)
          (into [] (range 0 4))))

(def b
  (phrase (repeat 1)
          [2 1 0 4]))

(def c
  (phrase (repeat 1)
          [7 nil 6 nil 7 nil 8 nil]))

(def s (then b a))

(defmethod live/play-note :default [{midi :pitch seconds :duration}]
  (-> midi
      overtone/midi->hz
      (beep seconds)))

(defmethod live/play-note :bass [{midi :pitch seconds :duration}]
  (-> midi
      overtone/midi->hz
      (/ 2)
      (beep seconds)))

(comment
  (->> s
       #_(then c)
       #_(then (with c (->> s (all :part :bass))))
       #_(then (shift-phrase s 4))
       #_(then (shift-phrase c 4))
       (tempo (bpm 120))
       (where :pitch (comp scale/C scale/major))
       live/play)

  )
