(ns clojure-playground.roman-to-arabic.core
  (:gen-class))

(def digit-values {:I 1
                   :V 5
                   :X 10
                   :L 50
                   :C 100
                   :D 500
                   :M 1000})

(defn char->keyword [c] (-> c str keyword))

(defn compute-value [n1 n2]
  (let [v1 (n1 digit-values)
        v2 (n2 digit-values)]
    (if (>= v1 v2)
      (+ v1 v2)
      (- v2 v1))))

(defn roman->arabic
  ([roman] (roman->arabic roman 0))
  ([roman arabic]
   (cond
     (empty? roman) arabic
     (< (count roman) 2) (-> (first roman) char->keyword digit-values)
     :else (let [first-char (char->keyword (first roman))
                 second-char (char->keyword (second roman))
                 remaining-chars (rest (rest roman))]
             (recur remaining-chars
                    (+ arabic (compute-value first-char second-char)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (roman->arabic "MMCDIX")))
