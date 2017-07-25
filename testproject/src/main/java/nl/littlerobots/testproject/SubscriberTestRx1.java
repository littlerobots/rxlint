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

package nl.littlerobots.testproject;

import java.util.concurrent.Callable;

import nl.littlerobots.test.MySubscriber;
import rx.Completable;
import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class SubscriberTestRx1 {
    public void subscribeObservableWithError() {
        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    public void subscribeObservableNoArguments() {
        Observable.just("Test").subscribe();
    }

    public void subscribeObservableWithoutError() {
        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    public void subscribeObservableWithSubscriber() {
        Subscription s = Observable.<String>just(null).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    public void subscribeObservableWithObserver() {
        Subscription s = Observable.<String>just(null).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    public void testCallingRandomSubcribe() {
        subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    private void subscribe(Action1<String> test) {

    }

    public void singleWithError() {
        Subscription s = Single.<String>just(null).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    public void singleWithoutError() {
        Subscription s = Single.<String>just(null).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    public void singleNoArguments() {
        Single.just(null).subscribe();
    }

    public void singleWithSingleSubscriber() {
        Single.just("test").subscribe(new SingleSubscriber<String>() {
            @Override
            public void onSuccess(String value) {

            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void singleWithSubcriber() {
        Single.just("test").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    public void subscribeWithJavaLibSubscriber() {
        Subscription s = Observable.just(null).subscribe(new MySubscriber<>());
    }

    public void completableWithError() {
        Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        }).subscribe(new Action0() {
            @Override
            public void call() {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    public void completableWithoutError() {
        Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        }).subscribe(new Action0() {
            @Override
            public void call() {

            }
        });
    }

    public void completableWithSubscriber() {
        Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    public void completableWithOutSubscriber() {
        Completable.fromAction(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe();
    }
}
