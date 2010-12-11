;   Copyright (c) Miki Tebeka, 2010. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this 
;   distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

;; test namespace for clojure.contrib.digest


(ns clojure.contrib.test-digest
  (:use [clojure.contrib.digest] :reload-all)
  (:use [clojure.string :only (lower-case)])
  (:use [clojure.test]))

(deftest md5-test
  (is (= (digest "md5" "foo") "acbd18db4cc2f85cedef654fccc4a4d8")))

(deftest sha-256-test
  (is (= (sha-256 "foo") 
         "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae")))

(deftest algorithms-test
  (let [names (algorithms)]
    (is not (empty? names))
    (is (names "SHA-1"))))

(deftest utils-test
  (for [name (algorithms)]
    (dorun (is (ns-resolve *ns* (symbol (lower-case name)))))))
