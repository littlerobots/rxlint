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

import com.android.tools.lint.checks.infrastructure.*;
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

    private TestFile rxKotlinSubscribeByStub = new TestFile.KotlinTestFile().to("io/reactivex/rxkotlin/subscribers.kt").within("src").withSource("package io.reactivex.rxkotlin\n" +
            "\n" +
            "import io.reactivex.Observable\n" +
            "import io.reactivex.disposables.Disposable\n" +
            "import io.reactivex.disposables.Disposables\n" +
            "\n" +
            "fun <T : Any> Observable<T>.subscribeBy(\n" +
            "    onError: (Throwable) -> Unit = {},\n" +
            "    onComplete: () -> Unit = {},\n" +
            "    onNext: (T) -> Unit = {}\n" +
            "): Disposable = Disposables.disposed()");

    private TestFile rx3KotlinSubscribeByStub = new TestFile.KotlinTestFile().to("io/reactivex/rxjava3/kotlin/subscribers.kt").within("src").withSource("package io.reactivex.rxjava3.kotlin\n" +
            "\n" +
            "import io.reactivex.rxjava3.core.Observable\n" +
            "import io.reactivex.rxjava3.disposables.Disposable\n" +
            "import io.reactivex.rxjava3.disposables.Disposables\n" +
            "\n" +
            "fun <T : Any> Observable<T>.subscribeBy(\n" +
            "    onError: (Throwable) -> Unit = {},\n" +
            "    onComplete: () -> Unit = {},\n" +
            "    onNext: (T) -> Unit = {}\n" +
            "): Disposable = Disposables.disposed()");

    private TestFile rxKotlinBlockingSubscribeByStub = new TestFile.KotlinTestFile().to("io/reactivex/rxkotlin/subscribers.kt").within("src").withSource("package io.reactivex.rxkotlin\n" +
            "\n" +
            "import io.reactivex.Observable\n" +
            "import io.reactivex.disposables.Disposable\n" +
            "import io.reactivex.disposables.Disposables\n" +
            "\n" +
            "fun <T : Any> Observable<T>.blockingSubscribeBy(\n" +
            "    onError: (Throwable) -> Unit = {},\n" +
            "    onComplete: () -> Unit = {},\n" +
            "    onNext: (T) -> Unit = {}\n" +
            "): Disposable = Disposables.disposed()");

    private TestFile rx3KotlinBlockingSubscribeByStub = new TestFile.KotlinTestFile().to("io/reactivex/rxjava3/kotlin/subscribers.kt").within("src").withSource("package io.reactivex.rxjava3.kotlin\n" +
            "\n" +
            "import io.reactivex.rxjava3.core.Observable\n" +
            "import io.reactivex.rxjava3.disposables.Disposable\n" +
            "import io.reactivex.rxjava3.disposables.Disposables\n" +
            "\n" +
            "fun <T : Any> Observable<T>.blockingSubscribeBy(\n" +
            "    onError: (Throwable) -> Unit = {},\n" +
            "    onComplete: () -> Unit = {},\n" +
            "    onNext: (T) -> Unit = {}\n" +
            "): Disposable = Disposables.disposed()");

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
        String result = lintProject(TestFiles.copy("rxjava-2.2.2.jar", "libs/rxjava2.jar", this),
                TestFiles.copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar", this),
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

    public void testSubscribeCheckRx3() throws Exception {
        String result = lintProject(TestFiles.copy("rxjava-3.0.9.jar", "libs/rxjava3.jar", this),
                TestFiles.copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar", this),
                TestFiles.copy("testjavalib.jar", "libs/testjavalib.jar", this),
                TestFiles.java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import io.reactivex.rxjava3.disposables.Disposable;\n" +
                        "import io.reactivex.rxjava3.functions.Action;\n" +
                        "import io.reactivex.rxjava3.functions.BiConsumer;\n" +
                        "import io.reactivex.rxjava3.functions.Consumer;\n" +
                        "import io.reactivex.rxjava3.observers.DisposableCompletableObserver;\n" +
                        "import io.reactivex.rxjava3.observers.DisposableMaybeObserver;\n" +
                        "import io.reactivex.rxjava3.observers.DisposableObserver;\n" +
                        "import io.reactivex.rxjava3.observers.DisposableSingleObserver;\n" +
                        "import io.reactivex.rxjava3.subscribers.DisposableSubscriber;\n" +
                        "\n" +
                        "public class SubscriberTestRx3 {\n" +
                        "    public void rx3ObservableWithSubscriber() {\n" +
                        "        io.reactivex.rxjava3.core.Observable.just(\"test\").subscribeWith(new DisposableObserver<String>() {\n" +
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
                        "    public void rx3ObservableWithOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Observable.just(\"test\").subscribe(new Consumer<String>() {\n" +
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
                        "    public void rx3ObservableWithoutOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Observable.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3ObservableNoArguments() {\n" +
                        "        io.reactivex.rxjava3.core.Observable.just(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void r3FlowableWithSubscriber() {\n" +
                        "        io.reactivex.rxjava3.core.Flowable.just(\"test\").subscribeWith(new DisposableSubscriber<String>() {\n" +
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
                        "    public void rx3FlowableWithOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Flowable.just(\"test\").subscribe(new Consumer<String>() {\n" +
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
                        "    public void rx3FlowableWithoutOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Flowable.just(\"Test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3FlowableNoArguments() {\n" +
                        "        io.reactivex.rxjava3.core.Flowable.just(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3CompletableWithSubscriber() {\n" +
                        "        io.reactivex.rxjava3.core.Completable.complete().subscribeWith(new DisposableCompletableObserver() {\n" +
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
                        "    public void rx3CompletableWithOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Completable.complete().subscribe(new Action() {\n" +
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
                        "    public void rx3CompletableWithoutOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Completable.complete().subscribe(new Action() {\n" +
                        "            @Override\n" +
                        "            public void run() throws Exception {\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3CompletableNoArguments() {\n" +
                        "        io.reactivex.rxjava3.core.Completable.complete().subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    public void rx3SingleWithSubscriber() {\n" +
                        "        io.reactivex.rxjava3.core.Single.just(\"test\").subscribeWith(new DisposableSingleObserver<String>() {\n" +
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
                        "    public void rx3SingleWithOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Single.just(\"test\").subscribe(new Consumer<String>() {\n" +
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
                        "    public void rx3SingleWithoutOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Single.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3SingleNoArguments() {\n" +
                        "        io.reactivex.rxjava3.core.Single.just(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3MaybeWithSubscriber() {\n" +
                        "        io.reactivex.rxjava3.core.Maybe.just(\"test\").subscribeWith(new DisposableMaybeObserver<String>() {\n" +
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
                        "    public void rx3MaybeWithOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Maybe.just(\"test\").subscribe(new Consumer<String>() {\n" +
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
                        "    public void rx3MaybeWithoutOnError() {\n" +
                        "        io.reactivex.rxjava3.core.Maybe.just(\"test\").subscribe(new Consumer<String>() {\n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3MaybeNoArguments() {\n" +
                        "        io.reactivex.rxjava3.core.Maybe.just(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    public void rx3WithObserver() {\n" +
                        "        io.reactivex.rxjava3.core.Observable.just(\"test\").subscribe(new io.reactivex.Observer<String>() {\n" +
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
                        "    public void rx3WithSingleObserver() {\n" +
                        "        io.reactivex.rxjava3.core.Single.just(1).subscribe(new io.reactivex.SingleObserver<Integer>() {\n" +
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
                        "    public void rx3WithMaybeObserver() {\n" +
                        "        io.reactivex.rxjava3.core.Maybe.just(1).subscribe(new io.reactivex.MaybeObserver<Integer>() {\n" +
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
                        "    public void rx3WithCompletableObserver() {\n" +
                        "        io.reactivex.rxjava3.core.Completable.complete().subscribe(new io.reactivex.CompletableObserver() {\n" +
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
                        "    public void rx3WithSingleBiConsumer() {\n" +
                        "        io.reactivex.rxjava3.core.Single.just(1).subscribe(new BiConsumer<Integer, Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Integer integer, Throwable throwable) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "}\n"));

        assertEquals("src/nl/littlerobots/testproject/SubscriberTestRx3.java:48: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Observable.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:57: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Observable.just(\"test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:94: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Flowable.just(\"Test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:103: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Flowable.just(\"test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:135: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Completable.complete().subscribe(new Action() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:143: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Completable.complete().subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:177: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Single.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:186: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Single.just(\"test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:223: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Maybe.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTestRx3.java:232: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.rxjava3.core.Maybe.just(\"test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "10 errors, 0 warnings\n", result);
    }

    public void testOnErrorComplete() throws Exception {
        String result = lintProject(TestFiles.copy("rxjava-2.2.2.jar", "libs/rxjava2.jar", this),
                TestFiles.copy("rxjava-3.0.9.jar", "libs/rxjava3.jar", this),
                TestFiles.copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar", this),
                TestFiles.copy("testjavalib.jar", "libs/testjavalib.jar", this),
                TestFiles.java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import io.reactivex.rxjava3.core.Completable;\n" +
                        "import io.reactivex.rxjava3.core.Flowable;\n" +
                        "import io.reactivex.rxjava3.core.Maybe;\n" +
                        "import io.reactivex.rxjava3.core.Observable;\n" +
                        "\n" +
                        "public class ErrorHandlingOperatorsTest {\n" +
                        "\n" +
                        "    public void surpressesErrorsRx2() {\n" +
                        "        io.reactivex.Completable.fromAction(() -> {\n" +
                        "\n" +
                        "        }).onErrorComplete().subscribe();\n" +
                        "\n" +
                        "        io.reactivex.Observable.just(\"test\").onErrorReturnItem(\"ha ha\").subscribe();\n" +
                        "        io.reactivex.Flowable.just(\"test\").onErrorReturnItem(\"test\").subscribe();\n" +
                        "        io.reactivex.Maybe.just(\"test\").onErrorComplete().subscribe();\n" +
                        "        io.reactivex.Maybe.just(\"test\").onErrorReturnItem(\"test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void surpressesErrorsRx3() {\n" +
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
                        "    public void doesNotSupressErrorsRx2() {\n" +
                        "        io.reactivex.Completable.fromAction(() -> {\n" +
                        "\n" +
                        "        }).onErrorComplete().cache().subscribe();\n" +
                        "\n" +
                        "        io.reactivex.Completable.fromAction(() -> {\n" +
                        "\n" +
                        "        }).onErrorComplete(t -> false).subscribe();\n" +
                        "\n" +
                        "\n" +
                        "        io.reactivex.Observable.just(\"test\").onErrorReturnItem(\"ha ha\").map(v -> v).subscribe();\n" +
                        "        io.reactivex.Flowable.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                        "        io.reactivex.Maybe.just(\"test\").onErrorComplete(t -> false).subscribe();\n" +
                        "        io.reactivex.Maybe.just(\"test\").onErrorComplete().map(v -> v).subscribe();\n" +
                        "        io.reactivex.Maybe.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void doesNotSupressErrorsRx3() {\n" +
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
        assertEquals("src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:33: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Completable.fromAction(() -> {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:37: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Completable.fromAction(() -> {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:42: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Observable.just(\"test\").onErrorReturnItem(\"ha ha\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:43: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Flowable.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:44: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Maybe.just(\"test\").onErrorComplete(t -> false).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:45: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Maybe.just(\"test\").onErrorComplete().map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:46: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Maybe.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:50: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.fromAction(() -> {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:54: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.fromAction(() -> {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:59: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Observable.just(\"test\").onErrorReturnItem(\"ha ha\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:60: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Flowable.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:61: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Maybe.just(\"test\").onErrorComplete(t -> false).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:62: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Maybe.just(\"test\").onErrorComplete().map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/ErrorHandlingOperatorsTest.java:63: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Maybe.just(\"test\").onErrorReturnItem(\"test\").map(v -> v).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "14 errors, 0 warnings\n", result);
    }

    public void testOnErrorHandlingOperatorsAutoDispose() {
        lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                copy("autodispose-1.0.0.jar", "libs/autodispose.jar"),
                java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "\n" +
                        "import com.uber.autodispose.AutoDispose;\n" +
                        "\n" +
                        "import io.reactivex.Completable;\n" +
                        "import io.reactivex.Flowable;\n" +
                        "import io.reactivex.Maybe;\n" +
                        "import io.reactivex.Observable;\n" +
                        "\n" +
                        "public class AutoDisposeOnErrorOperator {\n" +
                        "    public void autoDisposeOnerrorHandling() {\n" +
                        "        Completable.fromAction(() -> {\n" +
                        "\n" +
                        "        }).onErrorComplete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "\n" +
                        "        Observable.just(\"test\").onErrorReturnItem(\"ha ha\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "        Flowable.just(\"test\").onErrorReturnItem(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "        Maybe.just(\"test\").onErrorComplete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "        Maybe.just(\"test\").onErrorReturnItem(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "    }\n" +
                        "}\n")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(false).run().expect("No warnings.");
    }

    public void testErrorHandlingOperatorKotlinRx2() {
        lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                copy("autodispose-1.0.0.jar", "libs/autodispose.jar"),
                kotlin("package nl.littlerobots.test\n" +
                        "\n" +
                        "import io.reactivex.Observable\n" +
                        "\n" +
                        "class Demo {\n" +
                        "    fun getItemFromNetwork(): Observable<String> = Observable.just(\"test\")\n" +
                        "\n" +
                        "    fun handlingErrors() {\n" +
                        "        val notHandlingErrors = getItemFromNetwork().subscribe()\n" +
                        "        val hasDefaultErrorHandling = getItemFromNetwork().onErrorReturnItem(\"test\").subscribe()\n" +
                        "    }\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(false).run().expect("src/nl/littlerobots/test/Demo.kt:9: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        val notHandlingErrors = getItemFromNetwork().subscribe()\n" +
                "                                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "1 errors, 0 warnings");
    }

    public void testErrorHandlingOperatorKotlinRx3() {
        lint().files(
                copy("rxjava-3.0.9.jar", "libs/rxjava3.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                copy("autodispose-1.0.0.jar", "libs/autodispose.jar"),
                kotlin("package nl.littlerobots.test\n" +
                        "\n" +
                        "import io.reactivex.rxjava3.core.Observable\n" +
                        "\n" +
                        "class Demo {\n" +
                        "    fun getItemFromNetwork(): Observable<String> = Observable.just(\"test\")\n" +
                        "\n" +
                        "    fun handlingErrors() {\n" +
                        "        val notHandlingErrors = getItemFromNetwork().subscribe()\n" +
                        "        val hasDefaultErrorHandling = getItemFromNetwork().onErrorReturnItem(\"test\").subscribe()\n" +
                        "    }\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(false).run().expect("src/nl/littlerobots/test/Demo.kt:9: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        val notHandlingErrors = getItemFromNetwork().subscribe()\n" +
                "                                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "1 errors, 0 warnings");
    }

    public void testAutodispose() throws Exception {
        lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                copy("autodispose-1.0.0.jar", "libs/autodispose.jar"),
                java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import com.uber.autodispose.AutoDispose;\n" +
                        "\n" +
                        "import org.reactivestreams.Subscriber;\n" +
                        "import org.reactivestreams.Subscription;\n" +
                        "\n" +
                        "import io.reactivex.Completable;\n" +
                        "import io.reactivex.CompletableObserver;\n" +
                        "import io.reactivex.Flowable;\n" +
                        "import io.reactivex.Maybe;\n" +
                        "import io.reactivex.MaybeObserver;\n" +
                        "import io.reactivex.Observable;\n" +
                        "import io.reactivex.Observer;\n" +
                        "import io.reactivex.Single;\n" +
                        "import io.reactivex.SingleObserver;\n" +
                        "import io.reactivex.disposables.Disposable;\n" +
                        "\n" +
                        "public class AutodisposeTest {\n" +
                        "\n" +
                        "    public void errorHandlerMissing() {\n" +
                        "        Observable.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "        Flowable.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "        Maybe.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "        Single.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "        Completable.complete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void withErrorHandling() {\n" +
                        "        Observable.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(s -> {\n" +
                        "\n" +
                        "        }, t -> {\n" +
                        "\n" +
                        "        });\n" +
                        "\n" +
                        "        Observable.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new Observer<String>() {\n" +
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
                        "\n" +
                        "        Flowable.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(r -> {}, t -> {});\n" +
                        "        Flowable.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new Subscriber<String>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Subscription s) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
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
                        "\n" +
                        "        Maybe.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(r -> {}, v -> {});\n" +
                        "        Maybe.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new MaybeObserver<String>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
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
                        "        Single.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(r -> {}, t -> {});\n" +
                        "        Single.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new SingleObserver<String>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
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
                        "\n" +
                        "        Completable.complete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe(() -> {\n" +
                        "\n" +
                        "        }, t -> {});\n" +
                        "\n" +
                        "        Completable.complete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new CompletableObserver() {\n" +
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
                        "        }); \n" +
                        "    }\n" +
                        "}\n")
        ).issues(SubscribeDetector.ISSUE).testModes(TestMode.PARTIAL).allowCompilationErrors(true).run().expect("src/nl/littlerobots/testproject/AutodisposeTest.java:22: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Observable.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/AutodisposeTest.java:23: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Flowable.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/AutodisposeTest.java:24: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Maybe.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/AutodisposeTest.java:25: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Single.just(\"test\").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/AutodisposeTest.java:26: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.complete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "5 errors, 0 warnings");
    }

    public void testRxKotlinSubscribeBy() {
        TestLintTask.lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                rxKotlinSubscribeByStub,
                kotlin("package nl.littlerobots.test\n" +
                        "\n" +
                        "import io.reactivex.Observable\n" +
                        "import io.reactivex.rxkotlin.subscribeBy\n" +
                        "\n" +
                        "\n" +
                        "fun test() {\n" +
                        "    Observable.just(\"test\").subscribeBy { }\n" +
                        "    Observable.just(\"test\").onErrorReturnItem(\"test\").subscribeBy { }\n" +
                        "    Observable.just(\"test\").subscribeBy(onError = {})\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(false).run().expect("src/nl/littlerobots/test/test.kt:8: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "    Observable.just(\"test\").subscribeBy { }\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "1 errors, 0 warnings");
    }

    public void testRx3KotlinSubscribeBy() {
        TestLintTask.lint().files(
                copy("rxjava-3.0.9.jar", "libs/rxjava3.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                rx3KotlinSubscribeByStub,
                kotlin("package nl.littlerobots.test\n" +
                        "\n" +
                        "import io.reactivex.rxjava3.core.Observable\n" +
                        "import io.reactivex.rxjava3.kotlin.subscribeBy\n" +
                        "\n" +
                        "\n" +
                        "fun test() {\n" +
                        "    Observable.just(\"test\").subscribeBy { }\n" +
                        "    Observable.just(\"test\").onErrorReturnItem(\"test\").subscribeBy { }\n" +
                        "    Observable.just(\"test\").subscribeBy(onError = {})\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(true).run().expect("src/nl/littlerobots/test/test.kt:8: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "    Observable.just(\"test\").subscribeBy { }\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "1 errors, 0 warnings");
    }

    public void testRxKotlinBlockingSubscribeBy() {
        TestLintTask.lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                rxKotlinBlockingSubscribeByStub,
                kotlin("package nl.littlerobots.test\n" +
                        "\n" +
                        "import io.reactivex.Observable\n" +
                        "import io.reactivex.rxkotlin.blockingSubscribeBy\n" +
                        "\n" +
                        "\n" +
                        "fun test() {\n" +
                        "    Observable.just(\"test\").blockingSubscribeBy { }\n" +
                        "    Observable.just(\"test\").onErrorReturnItem(\"test\").blockingSubscribeBy { }\n" +
                        "    Observable.just(\"test\").blockingSubscribeBy(onError = {})\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(true).run().expect("src/nl/littlerobots/test/test.kt:8: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "    Observable.just(\"test\").blockingSubscribeBy { }\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "1 errors, 0 warnings");
    }

    public void testRx3KotlinBlockingSubscribeBy() {
        TestLintTask.lint().files(
                copy("rxjava-3.0.9.jar", "libs/rxjava3.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                rx3KotlinBlockingSubscribeByStub,
                kotlin("package nl.littlerobots.test\n" +
                        "\n" +
                        "import io.reactivex.rxjava3.core.Observable\n" +
                        "import io.reactivex.rxjava3.kotlin.blockingSubscribeBy\n" +
                        "\n" +
                        "\n" +
                        "fun test() {\n" +
                        "    Observable.just(\"test\").blockingSubscribeBy { }\n" +
                        "    Observable.just(\"test\").onErrorReturnItem(\"test\").blockingSubscribeBy { }\n" +
                        "    Observable.just(\"test\").blockingSubscribeBy(onError = {})\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(true).run().expect("src/nl/littlerobots/test/test.kt:8: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "    Observable.just(\"test\").blockingSubscribeBy { }\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "1 errors, 0 warnings");
    }

    public void testRxKotlinSubscribeByWithMethodReference() {
        TestLintTask.lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                rxKotlinSubscribeByStub,
                kotlin("package nl.littlerobots.rxlinttest\n" +
                        "\n" +
                        "import io.reactivex.Observable\n" +
                        "import io.reactivex.rxkotlin.subscribeBy\n" +
                        "\n" +
                        "fun test() {\n" +
                        "    val d2 = Observable.just(\"test\").subscribeBy(onError = ::handleError)\n" +
                        "}\n" +
                        "\n" +
                        "private fun handleError(throwable: Throwable) {\n" +
                        "\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(false).run().expectClean();
    }

    public void testRx3KotlinSubscribeByWithMethodReference() {
        TestLintTask.lint().files(
                copy("rxjava-3.0.9.jar", "libs/rxjava3.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                rxKotlinSubscribeByStub,
                kotlin("package nl.littlerobots.rxlinttest\n" +
                        "\n" +
                        "import io.reactivex.rxjava3.core.Observable\n" +
                        "import io.reactivex.rxjava3.kotlin.subscribeBy\n" +
                        "\n" +
                        "fun test() {\n" +
                        "    val d2 = Observable.just(\"test\").subscribeBy(onError = ::handleError)\n" +
                        "}\n" +
                        "\n" +
                        "private fun handleError(throwable: Throwable) {\n" +
                        "\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(true).run().expectClean();
    }

    public void testRxKotlinSubscribeByWithInvokingReferenceExpression() {
        TestLintTask.lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                rxKotlinSubscribeByStub,
                kotlin("package nl.littlerobots.rxlinttest\n" +
                        "\n" +
                        "import io.reactivex.Observable\n" +
                        "import io.reactivex.rxkotlin.subscribeBy\n" +
                        "\n" +
                        "\n" +
                        "fun test() {\n" +
                        "    val onError: (Throwable) -> Unit = {}\n" +
                        "    val d2 = Observable.just(\"test\").subscribeBy(onError = onError::invoke)\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(false).run().expectClean();
    }

    public void testRxKotlinSubscribeByWithInvokingObjectReference() {
        TestLintTask.lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                rxKotlinSubscribeByStub,
                kotlin("package nl.littlerobots.rxlinttest\n" +
                        "\n" +
                        "import io.reactivex.Observable\n" +
                        "import io.reactivex.rxkotlin.subscribeBy\n" +
                        "\n" +
                        "class HandlerThing : Action1<Throwable> {\n" +
                        "    override fun call(arg: Throwable) {\n" +
                        "        \n" +
                        "    } \n" +
                        "}\n" +
                        "\n" +
                        "interface Action1<T> {\n" +
                        "    fun call(arg: T)\n" +
                        "}\n" +
                        "\n" +
                        "class Test {\n" +
                        "    val onError: Action1<Throwable> = nl.littlerobots.rxlinttest.HandlerThing()\n" +
                        "\n" +
                        "    fun test() {\n" +
                        "        val d2 = Observable.just(\"test\").subscribeBy(onError = onError::call)\n" +
                        "    }\n" +
                        "}")
        ).issues(SubscribeDetector.ISSUE).allowCompilationErrors(false).run().expectClean();
    }
    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
