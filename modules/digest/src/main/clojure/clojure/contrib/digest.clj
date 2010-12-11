;;; digest.clj: Digest Algorithms (SHA, MD5 ...)

;; by Miki Tebeka, http://mikitebela.com
;; December 10, 2010
;; by Teemu Antti-Poika (anttipoi@gmail.com) - decode
;; May 12, 2010

;; Copyright (c) Miki Tebeka, 2010. All rights reserved.  The use
;; and distribution terms for this software are covered by the Eclipse
;; Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl-v10.html at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.


(ns ^{:doc "Digest algorithms (SHA, MDA ...)"
       :author "Miki Tebeka"}
    clojure.contrib.digest
  (:use [clojure.string :only (split lower-case)])
  (:import (java.security MessageDigest Security)))

(defmulti digest
  "Returns hex digest (string) for message with given algorithm."
  (fn [algorithm message] (class message)))

; Code based on http://www.holygoat.co.uk/blog/entry/2009-03-26-1
(defmethod digest :default
  [algorithm messages]
  (let [algo (MessageDigest/getInstance algorithm)]
    (.reset algo)
    (dorun (map #(.update algo (.getBytes %)) messages))
    (.toString (BigInteger. 1 (.digest algo)) 16)))

(defmethod digest String [algorithm message]
  (digest algorithm [message]))

(defn algorithms []
  "List support digest algorithms."
  (let [providers (into [] (Security/getProviders))
        names (mapcat #(enumeration-seq (.keys %)) providers)
        digest-names (filter #(re-find #"MessageDigest\.[A-Z0-9-]+$" %) names)]
    (set (map #(last (split % #"\.")) digest-names))))

(defn- create-fns []
  "Create utility function for each digest algorithms.
   For example will create an md5 function for MD5 algorithm."
  (dorun (map #(intern 'clojure.contrib.digest 
                       (symbol (lower-case %)) (partial digest %))
              (algorithms))))

; Create utililty functions such as md5, sha-2 ...
(create-fns)
