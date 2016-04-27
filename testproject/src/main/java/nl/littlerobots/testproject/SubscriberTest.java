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

import nl.littlerobots.test.MySubscriber;
import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.Subscriber;
import rx.Subscription;
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
}
