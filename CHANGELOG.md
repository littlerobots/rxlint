Change Log
==========

Version 1.6.1
-----------
* Updated 3.1 beta lint api

Version 1.6
-----------
* New check to flag operators using their default Scheduler, such as delay()

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