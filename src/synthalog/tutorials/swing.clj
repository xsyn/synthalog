(ns synthalog.tutorials.swing
  (:require [overtone.live :as overtone]))

(def metro (overtone/metronome 120))

(overtone/definst c-hat [amp 0.8 t 0.04]
  (let [env   (overtone/env-gen (overtone/perc 0.001 t) 1 1 0 1 overtone/FREE)
        noise (overtone/white-noise)
        sqr   (* (overtone/env-gen (overtone/perc 0.01 0.04)) (overtone/pulse 880 02))
        filt  (overtone/bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(overtone/definst o-hat [amp 0.8 t 0.5]
  (let [env   (overtone/env-gen (overtone/perc 0.001 t) 1 1 0 1 overtone/FREE)
        noise (overtone/white-noise)
        sqr   (* (overtone/env-gen (overtone/perc 0.01 0.04)) (overtone/pulse 880 02))
        filt  (overtone/bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(defn swinger [beat]
  (overtone/at (metro beat) (o-hat))
  (overtone/at (metro (inc beat)) (c-hat))
  #_(overtone/at (metro (+ 1.65 beat)) (c-hat))
  (overtone/apply-at (metro (+ 2 beat)) #'swinger (+ 2 beat) []))

(swinger (metro))
(overtone/stop-all)
