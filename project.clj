(defproject advent-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot advent-clj.core
  :target-path "target/%s"
  :plugins [[com.github.clj-kondo/lein-clj-kondo "0.2.1"]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.25.0"]]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}
  :test-selectors {:default :year-2023
                   :year-2023 :year-2023
                   :year-2022 :year-2022})
