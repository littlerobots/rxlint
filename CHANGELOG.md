Change Log
==========

Version 1.7.7 (next version)
-------------
* nothing new yet!

Version 1.7.6
-------------
* Bump lint dependencies to AGP 4.0, removing the obsolete lint warning.

Version 1.7.5
-------------
* Fix packaging of the lint rules
* Don't generate build config

Version 1.7.4
--------------
* Updated lint dependencies to the latest 3.5 beta, which fixes a regression when linting RxKotlin code (#38)

Version 1.7.3
----------------------
* Updated lint dependencies to prevent the "obsolete lint check" error on newer AGP versions.

Version 1.7.2
----------------------
* Rewrote the `subscribeBy` support and added `blockingSubscribeBy` support for consistency

Version 1.7.1
----------------------
* Fixed an issue in `subscribeBy` detection that caused a false positive when using a function reference (#30)

Version 1.7.0
----------------------
* When the `onErrorComplete()` or `onErrorReturnItem()` operator is used as a last operator before `subscribe()` no missing error handler will be flagged.
This only works for RxJava 2.x (see below)
* In this version support for RxJava version 1.x is deprecated. Existing checks will keep working until RxLint 2.0, but no new checks will be added.
* Support missing onError check when using `AutoDispose`
* Support for detecting subscription leaks and missing onError when using `RxKotlin` `subscribeBy` (in Kotlin code only) 

Version 1.6.1
-----------
* Updated 3.1 beta lint api

Version 1.6
-----------
* New check to flag operators using their default Scheduler, such as delay()
* RxLint now requires Android Gradle Plugin (AGP) 3.0 or up

Version 1.5
-----------
* Plain `subscribe()` without arguments is now also considered an error
* UAST support which means the check also works for Kotlin code (AS 3.0 and up)

Version 1.4
-----------
* Fix for false positive when using `BiConsumer` as a subscriber
* Fixed internal usage of PSI classes

Version 1.3
-----------
* Bug fixes

Version 1.2
-----------
* Add check for leaking subscriptions

Version 1.1
-------------
* Support for all Observable types: Observable, Single and Completable (rxjava 1.x)
* Support for all rxjava 2.x Observable types: Observable, Single, Completable, Maybe and Flowable
* (internal) The lint check is now converted to updated lint API using Psi in stead of Lombok

Version 1.0
-----------
* Oh hey, it's the first version!