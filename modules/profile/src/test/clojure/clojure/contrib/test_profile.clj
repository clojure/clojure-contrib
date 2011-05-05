(ns clojure.contrib.test-profile
  (:use clojure.test
	clojure.contrib.profile))

(deftest test-print-summary
  (testing "doesn't blow up with no data (assembla #31)"
    (is (= "Name      mean       min       max     count       sum\n"
           (with-out-str (print-summary {}))))))

(deftest can-summarize-profile-data
  (testing "summarizing a quick method"
    (is (= {:really-quick-operation {:mean 6, :min 0, :max 40, :count 7, :sum 46}}
           (summarize {:really-quick-operation (list 0 0 1 1 2 2 40)}))))

  (testing "summarizing a slow method"
    (let [long-time (+ Integer/MAX_VALUE 1)]
     (is (= {:really-quick-operation {:mean long-time, :min long-time, :max long-time, :count 1, :sum long-time}}
            (summarize {:really-slow-operation (list  long-time)}))))))

