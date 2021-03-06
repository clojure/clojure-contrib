;   Copyright (c) Chris Houser, April 2008. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

; System for filtering trees and nodes generated by zip.clj in
; general, and xml trees in particular.

(ns 
  ^{:author "Chris Houser",
     :doc "System for filtering trees and nodes generated by zip.clj in
general, and xml trees in particular.
"}
  clojure.contrib.zip-filter
  (:refer-clojure :exclude (descendants ancestors))
  (:require [clojure.zip :as zip]))

; This uses the negative form (no-auto) so that the result from any
; naive function, including user functions, defaults to "auto".
(defn auto
  [v x] (with-meta x ((if v dissoc assoc) (meta x) :zip-filter/no-auto? true)))

(defn auto?
  [x] (not (:zip-filter/no-auto? (meta x))))

(defn right-locs
  "Returns a lazy sequence of locations to the right of loc, starting with loc."
  [loc] (lazy-seq (when loc (cons (auto false loc) (right-locs (zip/right loc))))))

(defn left-locs
  "Returns a lazy sequence of locations to the left of loc, starting with loc."
  [loc] (lazy-seq (when loc (cons (auto false loc) (left-locs (zip/left loc))))))

(defn leftmost?
  "Returns true if there are no more nodes to the left of location loc."
  [loc] (nil? (zip/left loc)))

(defn rightmost?
  "Returns true if there are no more nodes to the right of location loc."
  [loc] (nil? (zip/right loc)))

(defn children
  "Returns a lazy sequence of all immediate children of location loc,
  left-to-right."
  [loc]
    (when (zip/branch? loc)
      (map #(auto false %) (right-locs (zip/down loc)))))

(defn children-auto
  "Returns a lazy sequence of all immediate children of location loc,
  left-to-right, marked so that a following tag= predicate will auto-descend."
  ^{:private true}
  [loc]
    (when (zip/branch? loc)
      (map #(auto true %) (right-locs (zip/down loc)))))

(defn descendants
  "Returns a lazy sequence of all descendants of location loc, in
  depth-first order, left-to-right, starting with loc."
  [loc] (lazy-seq (cons (auto false loc) (mapcat descendants (children loc)))))

(defn ancestors
  "Returns a lazy sequence of all ancestors of location loc, starting
  with loc and proceeding to loc's parent node and on through to the
  root of the tree."
  [loc] (lazy-seq (when loc (cons (auto false loc) (ancestors (zip/up loc))))))

(defn- fixup-apply
  "Calls (pred loc), and then converts the result to the 'appropriate'
  sequence."
  ^{:private true}
  [pred loc]
      (let [rtn (pred loc)]
        (cond (and (map? (meta rtn)) (:zip-filter/is-node? (meta rtn))) (list rtn)
              (= rtn true)                (list loc)
              (= rtn false)               nil
              (nil? rtn)                  nil
              (sequential? rtn)           rtn
              :else                       (list rtn))))

(defn mapcat-chain
  ^{:private true}
  [loc preds mkpred]
    (reduce (fn [prevseq expr]
                (mapcat #(fixup-apply (or (mkpred expr) expr) %) prevseq))
            (list (with-meta loc (assoc (meta loc) :zip-filter/is-node? true)))
            preds))

; see clojure.contrib.zip-filter.xml for examples
