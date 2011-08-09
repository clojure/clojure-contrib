## Clojure-contrib has moved!

To take advantage of the modularity allowed by git, and by tools such
as maven and leiningen, Clojure-contrib has been factored into separate git
projects and maven artifacts.

This repository is kept online for historical reasons. For new
development on Clojure-contrib, you should check out the following resources:

* [List of projects](http://dev.clojure.org/display/design/Contrib+Projects)
* [Clojure downloads](http://clojure.org/downloads)
* [User mailing list](http://groups.google.com/group/clojure)
* [Dev mailing list](http://groups.google.com/group/clojure-dev)
* [Maven configuration](http://dev.clojure.org/display/doc/Maven+Settings+and+Repositories)

If your favorite contrib isn't in a module yet, you [can help](http://dev.clojure.org/display/design/How+to+Create+New+Contrib+Projects).

## Clojure-contrib

The user contributions library, clojure-contrib, is a collection of namespaces implementing features that may be useful to a large part of the Clojure community.

Clojure-contrib is open source under the Eclipse Public License and is copyrighted by Rich Hickey and the various contributors.

## Clojure-contrib Versions

Versions of clojure-contrib are matched to versions of Clojure.

* If you are using Clojure 1.0, use clojure-contrib 1.0.
* If you are using Clojure 1.1, use clojure-contrib 1.1.
* If you are using Clojure 1.2, use clojure-contrib 1.2, or the new [modular Contrib](http://dev.clojure.org/display/doc/Clojure+Contrib) libraries.
*  If you are using Clojure 1.3, use the new [modular Contrib](http://dev.clojure.org/display/doc/Clojure+Contrib) libraries.

## Building "Old" Clojure-contrib

If you downloaded a release distribution or pre-compiled JAR, you do NOT need to build anything.

If you downloaded the sources from Github, you will need Apache Maven (2.0 or higher) to run the build.  See http://maven.apache.org/

AFTER version 1.2.0, clojure-contrib is divided into many small modules.

To build all the modules, run the following command in this directory:

    mvn install

This will compile and test all modules and store them in your local Maven repository cache (usually $HOME/.m2/repository).

There is also an "uberjar" containing all compiled modules at ./modules/complete/target/complete-$VERSION-bin.jar

Additional build commands are available:

    mvn clojure:repl
    To start a Clojure REPL (Read-Eval-Print Loop)

    mvn compile
    To compile sources without building a JAR

    mvn test
    To run unit tests

    mvn assembly:assembly
    To build ZIP/tar distributions containing source and JARs

To skip the testing phase when building, add "-Dmaven.test.skip=true"
to the mvn command line.

## Building Against Specific Released Clojure Versions

You can specify -Dclojure.version=VERSION on the command line to select a different Clojure version.

## Building Against a Custom Clojure JAR

To build against a customized Clojure JAR, you can specify
-Dclojure.jar=/absolute/path/to/clojure.jar on the command line.

## Clojure-contrib Committers

The following people are committers to the official clojure-contrib
repositiory:

Tom Faulhaber
Stephen Gilardi
Christophe Grand
Rich Hickey
Konrad Hinsen
Stuart Holloway
Chris Houser
David Miller
Stuart Sierra
Frantisek Sodomka
