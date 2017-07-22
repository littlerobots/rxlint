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

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import nl.littlerobots.test.MySubscriber;
import rx.Completable;
import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class SubscriberTest {
    public void subscribeWithError() {
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

    public void subscribeWithoutError() {
        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    public void subscribeWithSubscriber() {
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

    public void subscribeWithObserver() {
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

    public void subscribeWithJavaLibSubscriber() {
        Subscription s = Observable.just(null).subscribe(new MySubscriber<>());
    }

    public void completableWithError() {
        Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        }).subscribe(new Action1<Throwable>() {
            @Override
            public void call(Throwable error) {

            }
        }, new Action0() {
            @Override
            public void call() {

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

    // Rx2 types

    public void rx2ObservableWithSubscriber() {
        io.reactivex.Observable.just("test").subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void rx2ObservableWithOnError() {
        io.reactivex.Observable.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2ObservableWithoutOnError() {
        io.reactivex.Observable.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    public void rx2FlowableWithSubscriber() {
        io.reactivex.Flowable.just("test").subscribeWith(new DisposableSubscriber<String>() {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void rx2FlowableWithOnError() {
        io.reactivex.Flowable.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2FlowableWithoutOnError() {
        io.reactivex.Flowable.just("Test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    public void rx2CompletableWithSubscriber() {
        io.reactivex.Completable.complete().subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void rx2CompletableWithOnError() {
        io.reactivex.Completable.complete().subscribe(new Action() {
            @Override
            public void run() throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2CompletableWithoutOnError() {
        io.reactivex.Completable.complete().subscribe(new Action() {
            @Override
            public void run() throws Exception {
            }
        });
    }

    public void rx2SingleWithSubscriber() {
        io.reactivex.Single.just("test").subscribeWith(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void rx2SingleWithOnError() {
        io.reactivex.Single.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2SingleWithoutOnError() {
        io.reactivex.Single.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    public void rx2MaybeWithSubscriber() {
        io.reactivex.Maybe.just("test").subscribeWith(new DisposableMaybeObserver<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void rx2MaybeWithOnError() {
        io.reactivex.Maybe.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2MaybeWithoutOnError() {
        io.reactivex.Maybe.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    public void rx2WithObserver() {
        io.reactivex.Observable.just("test").subscribe(new io.reactivex.Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void rx2WithSingleObserver() {
        io.reactivex.Single.just(1).subscribe(new io.reactivex.SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void rx2WithMaybeObserver() {
        io.reactivex.Maybe.just(1).subscribe(new io.reactivex.MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void rx2WithCompletableObserver() {
        io.reactivex.Completable.complete().subscribe(new io.reactivex.CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void rx2WithSingleBiConsumer() {
        io.reactivex.Single.just(1).subscribe(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer integer, Throwable throwable) throws Exception {

            }
        });
    }
}
