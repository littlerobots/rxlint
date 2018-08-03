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

package nl.littlerobots.rxlint;

import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.checks.infrastructure.TestFiles;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;

import java.util.Collections;
import java.util.List;

public class SubscribeDetectorTest extends LintDetectorTest {

    @Override
    protected Detector getDetector() {
        return new SubscribeDetector();
    }

    @Override
    protected List<Issue> getIssues() {
        return Collections.singletonList(SubscribeDetector.ISSUE);
    }

    public void testSubscribeCheckRx1() throws Exception {
        String result = lintProject(TestFiles.copy("rxjava-1.3.0.jar", "libs/rxjava.jar", this),
                TestFiles.copy("testjavalib.jar", "libs/testjavalib.jar", this),
                TestFiles.java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import java.util.concurrent.Callable;\n" +
                        "\n" +
                        "import nl.littlerobots.test.MySubscriber;\n" +
                        "import rx.Completable;\n" +
                        "import rx.Observable;\n" +
                        "import rx.Observer;\n" +
                        "import rx.Single;\n" +
                        "import rx.SingleSubscriber;\n" +
                        "import rx.Subscriber;\n" +
                        "import rx.Subscription;\n" +
                        "import rx.functions.Action0;\n" +
                        "import rx.functions.Action1;\n" +
                        "\n" +
                        "public class SubscriberTestRx1 {\n" +
                        "    public void subscribeObservableWithError() {\n" +
                        "        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {\n" +
                        "            @Override\n" +
                        "            public void call(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Action1<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void call(Throwable throwable) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscribeObservableNoArguments() {\n" +
                        "        Observable.just(\"Test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscribeObservableWithoutError() {\n" +
                        "        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {\n" +
                        "            @Override\n" +
                        "            public void call(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscribeObservableWithSubscriber() {\n" +
                        "        Subscription s = Observable.<String>just(null).subscribe(new Subscriber<String>() {\n" +
                        "            @Override\n" +
                        "            public void onCompleted() {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscribeObservableWithObserver() {\n" +
                        "        Subscription s = Observable.<String>just(null).subscribe(new Observer<String>() {\n" +
                        "            @Override\n" +
                        "            public void onCompleted() {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void testCallingRandomSubcribe() {\n" +
                        "        subscribe(new Action1<String>() {\n" +
                        "            @Override\n" +
                        "            public void call(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    private void subscribe(Action1<String> test) {\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "    public void singleWithError() {\n" +
                        "        Subscription s = Single.<String>just(null).subscribe(new Action1<String>() {\n" +
                        "            @Override\n" +
                        "            public void call(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Action1<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void call(Throwable throwable) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void singleWithoutError() {\n" +
                        "        Subscription s = Single.<String>just(null).subscribe(new Action1<String>() {\n" +
                        "            @Override\n" +
                        "            public void call(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void singleNoArguments() {\n" +
                        "        Single.just(null).subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void singleWithSingleSubscriber() {\n" +
                        "        Single.just(\"test\").subscribe(new SingleSubscriber<String>() {\n" +
                        "            @Override\n" +
                        "            public void onSuccess(String value) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable error) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void singleWithSubcriber() {\n" +
                        "        Single.just(\"test\").subscribe(new Subscriber<String>() {\n" +
                        "            @Override\n" +
                        "            public void onCompleted() {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscribeWithJavaLibSubscriber() {\n" +
                        "        Subscription s = Observable.just(null).subscribe(new MySubscriber<>());\n" +
                        "    }\n" +
                        "\n" +
                        "    public void completableWithError() {\n" +
                        "        Completable.fromCallable(new Callable<Object>() {\n" +
                        "            @Override\n" +
                        "            public Object call() throws Exception {\n" +
                        "                return null;\n" +
                        "            }\n" +
                        "        }).subscribe(new Action0() {\n" +
                        "            @Override\n" +
                        "            public void call() {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Action1<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void call(Throwable throwable) {\n" +
                        "                \n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void completableWithoutError() {\n" +
                        "        Completable.fromCallable(new Callable<Object>() {\n" +
                        "            @Override\n" +
                        "            public Object call() throws Exception {\n" +
                        "                return null;\n" +
                        "            }\n" +
                        "        }).subscribe(new Action0() {\n" +
                        "            @Override\n" +
                        "            public void call() {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void completableWithSubscriber() {\n" +
                        "        Completable.fromCallable(new Callable<Object>() {\n" +
                        "            @Override\n" +
                        "            public Object call() throws Exception {\n" +
                        "                return null;\n" +
                        "            }\n" +
                        "        }).subscribe(new Subscriber<Object>() {\n" +
                        "            @Override\n" +
                        "            public void onCompleted() {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Object o) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void completableWithOutSubscriber() {\n" +
                        "        Completable.fromAction(new Action0() {\n" +
                        "            @Override\n" +
                        "            public void call() {\n" +
                        "\n" +
                        "            }\n" +
                        "        }).subscribe();\n" +
                        "    }\n" +
                        "}\n"));

        assertEquals("src/nl/littlerobots/testproject/SubscriberTestRx1.java:32: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Observable.just(\"Test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx1.java:36: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {\n" +
                "                         ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx1.java:110: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Subscription s = Single.<String>just(null).subscribe(new Action1<String>() {\n" +
                "                         ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx1.java:119: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Single.just(null).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx1.java:179: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.fromCallable(new Callable<Object>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx1.java:217: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.fromAction(new Action0() {\n" +
                "        ^\n" +
                "6 errors, 0 warnings\n", result);
    }


    public void testSubscribeCheckRx2() throws Exception {
        String result = lintProject(TestFiles.copy("rxjava-2.0.5.jar", "libs/rxjava2.jar", this),
                TestFiles.copy("reactive-streams-1.0.0.final.jar", "libs/reactive-streams-1.0.0.final.jar", this),
                TestFiles.copy("testjavalib.jar", "libs/testjavalib.jar", this),
                TestFiles.java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import io.reactivex.disposables.Disposable;\n" +
                        "import io.reactivex.functions.Action;\n" +
                        "import io.reactivex.functions.BiConsumer;\n" +
                        "import io.reactivex.functions.Consumer;\n" +
                        "import io.reactivex.observers.DisposableCompletableObserver;\n" +
                        "import io.reactivex.observers.DisposableMaybeObserver;\n" +
                        "import io.reactivex.observers.DisposableObserver;\n" +
                        "import io.reactivex.observers.DisposableSingleObserver;\n" +
                        "import io.reactivex.subscribers.DisposableSubscriber;\n" +
                        "\n" +
                        "public class SubscriberTestRx2 {\n" +
                        "    public void rx2ObservableWithSubscriber() {\n" +
                        "        io.reactivex.Observable.just(\"test\").subscribeWith(new DisposableObserver<String>() {\n" +
                        "            @Override\n" +
                        "            public void onNext(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2ObservableWithOnError() {\n" +
                        "        io.reactivex.Observable.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Consumer<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Throwable throwable) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2ObservableWithoutOnError() {\n" +
                        "        io.reactivex.Observable.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2ObservableNoArguments() {\n" +
                        "        io.reactivex.Observable.just(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2FlowableWithSubscriber() {\n" +
                        "        io.reactivex.Flowable.just(\"test\").subscribeWith(new DisposableSubscriber<String>() {\n" +
                        "            @Override\n" +
                        "            public void onNext(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable t) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2FlowableWithOnError() {\n" +
                        "        io.reactivex.Flowable.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Consumer<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Throwable throwable) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2FlowableWithoutOnError() {\n" +
                        "        io.reactivex.Flowable.just(\"Test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2FlowableNoArguments() {\n" +
                        "        io.reactivex.Flowable.just(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2CompletableWithSubscriber() {\n" +
                        "        io.reactivex.Completable.complete().subscribeWith(new DisposableCompletableObserver() {\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2CompletableWithOnError() {\n" +
                        "        io.reactivex.Completable.complete().subscribe(new Action() {\n" +
                        "            @Override\n" +
                        "            public void run() throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Consumer<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Throwable throwable) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2CompletableWithoutOnError() {\n" +
                        "        io.reactivex.Completable.complete().subscribe(new Action() {\n" +
                        "            @Override\n" +
                        "            public void run() throws Exception {\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2CompletableNoArguments() {\n" +
                        "        io.reactivex.Completable.complete().subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    public void rx2SingleWithSubscriber() {\n" +
                        "        io.reactivex.Single.just(\"test\").subscribeWith(new DisposableSingleObserver<String>() {\n" +
                        "            @Override\n" +
                        "            public void onSuccess(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    public void rx2SingleWithOnError() {\n" +
                        "        io.reactivex.Single.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Consumer<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Throwable throwable) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2SingleWithoutOnError() {\n" +
                        "        io.reactivex.Single.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2SingleNoArguments() {\n" +
                        "        io.reactivex.Single.just(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2MaybeWithSubscriber() {\n" +
                        "        io.reactivex.Maybe.just(\"test\").subscribeWith(new DisposableMaybeObserver<String>() {\n" +
                        "            @Override\n" +
                        "            public void onSuccess(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2MaybeWithOnError() {\n" +
                        "        io.reactivex.Maybe.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Consumer<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Throwable throwable) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2MaybeWithoutOnError() {\n" +
                        "        io.reactivex.Maybe.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2MaybeNoArguments() {\n" +
                        "        io.reactivex.Maybe.just(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    public void rx2WithObserver() {\n" +
                        "        io.reactivex.Observable.just(\"test\").subscribe(new io.reactivex.Observer<String>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2WithSingleObserver() {\n" +
                        "        io.reactivex.Single.just(1).subscribe(new io.reactivex.SingleObserver<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onSuccess(Integer integer) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2WithMaybeObserver() {\n" +
                        "        io.reactivex.Maybe.just(1).subscribe(new io.reactivex.MaybeObserver<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onSuccess(Integer integer) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2WithCompletableObserver() {\n" +
                        "        io.reactivex.Completable.complete().subscribe(new io.reactivex.CompletableObserver() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2WithSingleBiConsumer() {\n" +
                        "        io.reactivex.Single.just(1).subscribe(new BiConsumer<Integer, Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Integer integer, Throwable throwable) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "}\n"));

        assertEquals("src/nl/littlerobots/testproject/SubscriberTestRx2.java:48: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Observable.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:57: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Observable.just(\"test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:94: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Flowable.just(\"Test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:103: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Flowable.just(\"test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:135: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Completable.complete().subscribe(new Action() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:143: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Completable.complete().subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:177: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Single.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:186: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Single.just(\"test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:223: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Maybe.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx2.java:232: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Maybe.just(\"test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "10 errors, 0 warnings\n", result);
    }

    public void testOnErrorComplete() throws Exception {
        String result = lintProject(TestFiles.copy("rxjava-2.0.5.jar", "libs/rxjava2.jar", this),
                TestFiles.copy("reactive-streams-1.0.0.final.jar", "libs/reactive-streams-1.0.0.final.jar", this),
                TestFiles.copy("testjavalib.jar", "libs/testjavalib.jar", this),
                TestFiles.java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import io.reactivex.Completable;\n" +
                        "import io.reactivex.Flowable;\n" +
                        "import io.reactivex.Maybe;\n" +
                        "import io.reactivex.Observable;\n" +
                        "\n" +
                        "public class ErrorHandlingOperatorsTest {\n" +
                        "\n" +
                        "    public void surpressesErrors() {\n" +
                        "        Completable.fromAction(() -> {\n" +
                        "\n" +
                        "        }).onErrorComplete().subscribe();\n" +
                        "\n" +
                        "        Observable.just(\"test\").onErrorReturnItem(\"ha ha\").subscribe();\n" +
                        "        Flowable.just(\"test\").onErrorReturnItem(\"test\").subscribe();\n" +
                        "        Maybe.just(\"test\").onErrorComplete().subscribe();\n" +
                        "        Maybe.just(\"test\").onErrorReturnItem(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void doesNotSupressErrors() {\n" +
                        "        Completable.fromAction(() -> {\n" +
                        "\n" +
                        "        }).onErrorComplete().cache().subscribe();\n" +
                        "\n" +
                        "        Completable.fromAction(() -> {\n" +
                        "\n" +
                        "        }).onErrorComplete(t -> false).subscribe();\n" +
                        "\n" +
                        "\n" +
                        "        Observable.just(\"test\").onErrorReturnItem(\"ha ha\").map(v -> v).subscribe();\n" +
                        "        Flowable.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                        "        Maybe.just(\"test\").onErrorComplete(t -> false).subscribe();\n" +
                        "        Maybe.just(\"test\").onErrorComplete().map(v -> v).subscribe();\n" +
                        "        Maybe.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                        "    }\n" +
                        "}\n"));
        assertEquals("src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:22: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.fromAction(() -> {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:26: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.fromAction(() -> {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:31: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Observable.just(\"test\").onErrorReturnItem(\"ha ha\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:32: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Flowable.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:33: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Maybe.just(\"test\").onErrorComplete(t -> false).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:34: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Maybe.just(\"test\").onErrorComplete().map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:35: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Maybe.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "7 errors, 0 warnings\n", result);
    }

    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
