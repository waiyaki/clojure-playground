;; Balanced Parens
;; Write a function that accepts a string and returns a boolean value indicating
;; whether or not the parentheses in that string are "balanced," meaning that
;; for every opening parenthesis there is a matching closing parenthesis in the
;; correct order.
;;
;; Example Usage
;; (balanced-parens "(") ;;=> false
;; (balanced-parens "()") ;;=> true
;; (balanced-parens ")(") ;;=> false
;; (balanced-parens "(())") ;;=> true
;;
;; Advanced Content
;; Extend your balanced-parens function to work on parenthesis, square brackets,
;; and curly brackets!
;;
;; (balanced-parens "[](){}") ;;=> true
;; (balanced-parens "[({})]") ;;=> true
;; (balanced-parens "[(]{)}") ;;=> false

(ns clojure-playground.balanced-parens.core
  (:gen-class))

(def opening-parens (sorted-set \( \{ \[))
(def closing-parens (sorted-set \) \} \]))
(def paren-pairs (zipmap opening-parens closing-parens))

(def opening-paren? (partial contains? opening-parens))
(def closing-paren? (partial contains? closing-parens))

(defn matching-parens? [opening-paren match]
  (= (get paren-pairs opening-paren) match))

(defn match-parens
  ([input] (match-parens input nil))
  ([input stack]
   (let [currently-open (peek stack)
         next-paren (first input)
         remaining-parens (rest input)]
     (cond
       (and (empty? input) (empty? stack)) true
       (and (empty? input) (not-empty stack)) false
       (and (empty? stack) (closing-paren? next-paren)) false
       (closing-paren? next-paren) (if (matching-parens? currently-open next-paren)
                                     (recur remaining-parens (pop stack))
                                     false)
       :else (recur remaining-parens (conj stack next-paren))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (doseq [parens ["(" "()" ")(" "()" "(())" "((()))"
                  "[](){}" "[({})]" "[(]{)}"]]
    (prn :parens parens :matches (match-parens parens))))
