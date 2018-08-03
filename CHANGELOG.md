Change Log
==========

Version 1.7.0-SNAPSHOT
----------------------
* When the `onErrorComplete()` or `onErrorReturnItem()` operator is used as a last operator before `subscribe()` no missing error handler will be flagged.
This only works for RxJava 2.x (see below)
* In this version support voor RxJava version 1.x is depreciated. Existing checks will keep working, but no new checks will be added.

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