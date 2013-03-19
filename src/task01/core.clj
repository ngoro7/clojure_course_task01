(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))


(defn vec-with-r-class? [v]
  "Test if argument is a vector that has the second element which is map
   having :class element with 'r' string value.
   See  tagsoup  documentation"
  (and
     (vector? v)
     (map? (second v))
     (= "r" (:class (second v)))))
    

(defn flat-vects [v]
  "Filter complex nested structure  generating one-level vector of vectors" 
  (tree-seq vector? identity v))

(defn get-links []
" 1) Find all elements containing {:class \"r\"}.

Example:
[:h3 {:class \"r\"} [:a {:shape \"rect\", :class \"l\",
                         :href \"https://github.com/clojure/clojure\",
                         :onmousedown \"return rwt(this,'','','','4','AFQjCNFlSngH8Q4cB8TMqb710dD6ZkDSJg','','0CFYQFjAD','','',event)\"}
                     [:em {} \"clojure\"] \"/\" [:em {} \"clojure\"] \" · GitHub\"]]

   2) Extract href from the element :a.

The link from the example above is 'https://github.com/clojure/clojure'.

  3) Return vector of all 10 links.

Example: ['https://github.com/clojure/clojure', 'http://clojure.com/', . . .]
"
  (let [data (parse "clojure_google.html")]
     (->> (flat-vects data)
       (filter vec-with-r-class?)
       (map #(get-in % [2 1 :href])))))

(defn -main []
  (println (str "Found " (count (get-links)) " links!")))


