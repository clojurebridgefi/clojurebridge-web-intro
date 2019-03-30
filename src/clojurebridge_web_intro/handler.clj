(ns clojurebridge-web-intro.handler
  (:require [compojure.core :refer :all]
            [hiccup.core :refer [html]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.resource :refer [wrap-resource]]))

(defn- get-style [color]
  (str "color: rgb(" color "," (- 255 color) ",0)"))

(defn frontpage [title]
  (html [:html
         [:head
          [:title title]
          [:style "body { font-family: Helvetica, Arial, sans-serif; }"]]
         [:body
          [:h1 title]
          [:ul
           (for [c (range 0 256 51)]
             [:li {:style (get-style c)}
              c])]
          [:p
           [:a {:href "/date"}
            "Date"]]
          [:p
           [:img {:src "/images/logo.png"}]]]]))

(defroutes app-routes
  (GET "/" [] (frontpage "ClojureBridge Web Example"))
  (GET "/date" [] (str (java.util.Date.)))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
    (wrap-defaults site-defaults)
    (wrap-resource "public")))
