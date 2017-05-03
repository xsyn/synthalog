(ns synthalog.tutorials.pitchchords
  (:require [overtone.live :refer :all]))

(definst saw-wave [freq 440 attack 0.1 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (env-lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

;; Notes are created through a frequency in Hz
(saw-wave 440)
(saw-wave 523.25)
;; C4
(saw-wave 261.62)

;; We can also use midi note values
(saw-wave (midi->hz 69))
(saw-wave (midi->hz 72))
;;C4
(saw-wave (midi->hz 60))

(defn note->hz [music-note]
  (midi->hz (note music-note)))

(saw-wave (note->hz :C5))

;; Making it easier
(defn saw2 [music-note]
  (saw-wave (midi->hz (note music-note))))

(saw2 :A4)
(saw2 "C5")
(saw2 :C4)

(defn play-chord [a-chord]
  (doseq [note a-chord] (saw2 note)))

(play-chord (chord :C4 :major))

;; Chord progression
(defn chord-progression-time []
  (let [time (now)]
    (at time (play-chord (chord :C4 :major)))
    (at (+ 1000 time) (play-chord (chord :G3 :major)))
    (at (+ 2000 time) (play-chord (chord :F3 :sus4)))
    (at (+ 3300 time) (play-chord (chord :F3 :major)))
    (at (+ 4000 time) (play-chord (chord :G3 :major)))))

(chord-progression-time)

(defonce metro (metronome 120))
(metro)

(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :C4 :major)))
  (at (m (+ 4 beat-num)) (play-chord (chord :G3 :major)))
  (at (m (+ 8 beat-num)) (play-chord (chord :A3 :minor)))
  (at (m (+ 14 beat-num)) (play-chord (chord :F3 :major))))

(chord-progression-beat metro (metro))

(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :C4 :major)))
  (at (m (+ 4 beat-num)) (play-chord (chord :G3 :major)))
  (at (m (+ 8 beat-num)) (play-chord (chord :A3 :minor)))
  (at (m (+ 14 beat-num)) (play-chord (chord :F3 :major)))
  (apply-at (m (+ 16 beat-num)) chord-progression-beat m (+ 16 beat-num) []))

(chord-progression-beat metro (metro))
(stop-all)
