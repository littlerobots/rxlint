# README

`rxlint` is (currently) a single lint check that checks if a [rxjava][1] subscriber is handling the `onError()`
 callback. 

By default, [rxjava][1] will throw an `OnErrorNotImplemented` exception wrapped in an `IllegalStateException` for every error that is not handled.
When subscribing on a [Scheduler][2], like `Schedulers.io()` the error will be thrown on the scheduler thread and the stack trace will have no reference to the place where you subscribed.

TL;DR you should handle `onError`.

## Using

Adding `rxlint` to your project is easy, just add the following dependency to your `build.gradle`:

    ::groovy
    compile 'nl.littlerobots.rxlint:rxlint:1.0'

Once added to your project an error will be shown like this:

![Lint screenshot](lint.png)

### Ignoring errors

Use the `@SuppressLint("RxSubscribeOnError")` annotation, [lint.xml][3] or use `//noinspection AndroidLintCustomError`.
Refer to the [tools documentation on lint][3] for more info.

# License

```
/*
 *    Copyright 2016 Little Robots
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
```

[1]:https://github.com/ReactiveX/RxJava
[2]:http://reactivex.io/RxJava/javadoc/rx/schedulers/Schedulers.html
[3]:http://tools.android.com/tips/lint/suppressing-lint-warnings