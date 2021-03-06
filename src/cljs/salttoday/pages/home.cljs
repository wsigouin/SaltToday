(ns salttoday.pages.home
  (:require [ajax.core :refer [GET PUT]]
            [reagent.core :as r]
            [salttoday.common :refer [display-comment]]
            [salttoday.pages.common :refer [content make-content make-navbar make-right-offset jumbotron]]))

(def state
  (r/atom {}))

(defn top-comments-handler [response]
  (js/console.log response)
  (reset! state response))

(GET "/top-comments"
  {:headers {"Accept" "application/transit"}
   :handler top-comments-handler})

(defn home-content [snapshot]
  (list [:div.row.justify-center.header-wrapper
         [:span.heading "Today"]]
        [:div.column.justify-center.comments-wrapper
         (for [comment (get snapshot "daily")]
           (display-comment comment))]
        [:div.row.justify-center.header-wrapper
         [:span.heading "All Time"]]
        [:div.column.justify-center.comments-wrapper
         (for [comment (get snapshot "all-time")]
           (display-comment comment))]))

(defn home-page []
  [:div.page-wrapper
   (make-navbar :home)
   (make-content :home (home-content @state))
   (make-right-offset)])
