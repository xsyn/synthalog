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

(defn terminate-trill [m] (-> (last m)
                              (update :duration / 1/4)))

(defn trill [note length]
  (cons note
        (for [x    (range 1 (+ 1 length))
              :let [start-note  (:pitch note)
                    arp-beat   (* (:duration note) 1/4)
                    time-beat  (:time note)
                    pitch      (if (odd? x) start-note (- start-note 1))
                    time (if (= x 1) (+ time-beat (:duration note)) (+ time-beat (* x arp-beat)))]]
          {:pitch pitch :time time :duration arp-beat})))

(comment
  (terminate-trill (trill (first a) 4)))

(def a
  (phrase (repeat 1/4)
          (into [] (range 0 4))))

(def b
  (phrase (repeat 1/4)
          [2 1 0 4]))

(def c
  (phrase (repeat 1/4)
          [7 6 7 8]))

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
       (then (with c (->> s (all :part :bass))))
       (then (shift-phrase s 4))
       (then (shift-phrase c 4))
       (tempo (bpm 84))
       (where :pitch (comp scale/C scale/major))
       live/play)

  (->> (phrase [1/4 1/4 1/4 1/4 1/16 1/16 1/16  1/16  1/4]
               [0 2 1 3 3 2 3 2 2 ])
       (tempo (bpm 84))
       (where :pitch (comp scale/C scale/major))
       live/play)

  )
