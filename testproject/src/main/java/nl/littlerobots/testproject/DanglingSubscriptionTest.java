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

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class DanglingSubscriptionTest {
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private Subscription mSubscription;

    public void subscriptionWithoutSavingReturn() {
        Observable.just("hi").subscribe(new Subscriber<String>() {
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

    public void subscriptionAddedToCompositeSubscription() {
        mSubscriptions.add(Observable.just("hi").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        }));
    }

    public void subscriptionWithSavingReturnButNoUnsubscribe() {
        Subscription subscription = Observable.just("hi").subscribe(new Subscriber<String>() {
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

    public void subscriptionWithSavingReturnWithUnsubscribe() {
        Subscription subscription = Observable.just("hi").subscribe(new Subscriber<String>() {
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
        subscription.unsubscribe();
    }

    public void subscriptionWithSavingReturnInGlobalField() {
        mSubscription = Observable.just("hi").subscribe(new Subscriber<String>() {
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

    public Subscription subscriptionFromReturn() {
        return Observable.just("Test").subscribe();
    }

    public void subscriptionWithObservable() {
        Observable.just("test").subscribe(new rx.Observer<String>() {
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

    public void rx2subscriptionWithoutSavingReturn() {
        io.reactivex.Observable.just("Test").subscribe();
    }

    public void rx2subscriptionKeepingReference() {
        Disposable test = io.reactivex.Observable.just("Test").subscribe();
    }

    public void rx2subscriptionWithoutSavingReturnSubscribeWith() {
        io.reactivex.Observable.just("Test").subscribeWith(new DisposableObserver<String>() {
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

    public void rx2subscriptionSavingReturnSubscribeWith() {
        DisposableObserver<String> disposable = io.reactivex.Observable.just("Test").subscribeWith(new DisposableObserver<String>() {
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

    public void rx2WithObservable() {
        io.reactivex.Observable.just("test").subscribe(new Observer<String>() {
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
}
