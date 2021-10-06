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
import com.android.tools.lint.checks.infrastructure.TestLintTask;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;

import java.util.Collections;
import java.util.List;

public class DanglingSubscriptionDetectorTest extends LintDetectorTest {

    @Override
    protected Detector getDetector() {
        return new DanglingSubscriptionDetector();
    }

    @Override
    protected List<Issue> getIssues() {
        return Collections.singletonList(DanglingSubscriptionDetector.ISSUE);
    }

    public void testSubscribeCheck() throws Exception {
        String result = lintProject(
                TestFiles.copy("rxjava-1.3.0.jar", "libs/rxjava.jar", this),
                TestFiles.copy("rxjava-2.2.2.jar", "libs/rxjava2.jar", this),
                TestFiles.copy("rxjava-3.0.9.jar", "libs/rxjava3.jar", this),
                TestFiles.copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar", this),
                TestFiles.java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import io.reactivex.Observer;\n" +
                        "import io.reactivex.disposables.Disposable;\n" +
                        "import io.reactivex.observers.DisposableObserver;\n" +
                        "import rx.Observable;\n" +
                        "import rx.Subscriber;\n" +
                        "import rx.Subscription;\n" +
                        "import rx.subscriptions.CompositeSubscription;\n" +
                        "\n" +
                        "public class DanglingSubscriptionTest {\n" +
                        "    private CompositeSubscription mSubscriptions = new CompositeSubscription();\n" +
                        "    private Subscription mSubscription;\n" +
                        "\n" +
                        "    public void subscriptionWithoutSavingReturn() {\n" +
                        "        Observable.just(\"hi\").subscribe(new Subscriber<String>() {\n" +
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
                        "    public void subscriptionAddedToCompositeSubscription() {\n" +
                        "        mSubscriptions.add(Observable.just(\"hi\").subscribe(new Subscriber<String>() {\n" +
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
                        "        }));\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscriptionWithSavingReturnButNoUnsubscribe() {\n" +
                        "        Subscription subscription = Observable.just(\"hi\").subscribe(new Subscriber<String>() {\n" +
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
                        "    public void subscriptionWithSavingReturnWithUnsubscribe() {\n" +
                        "        Subscription subscription = Observable.just(\"hi\").subscribe(new Subscriber<String>() {\n" +
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
                        "        subscription.unsubscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscriptionWithSavingReturnInGlobalField() {\n" +
                        "        mSubscription = Observable.just(\"hi\").subscribe(new Subscriber<String>() {\n" +
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
                        "    public Subscription subscriptionFromReturn() {\n" +
                        "        return Observable.just(\"Test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscriptionWithObservable() {\n" +
                        "        Observable.just(\"test\").subscribe(new rx.Observer<String>() {\n" +
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
                        "    public void rx2subscriptionWithoutSavingReturn() {\n" +
                        "        io.reactivex.Observable.just(\"Test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2subscriptionKeepingReference() {\n" +
                        "        Disposable test = io.reactivex.Observable.just(\"Test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2subscriptionWithoutSavingReturnSubscribeWith() {\n" +
                        "        io.reactivex.Observable.just(\"Test\").subscribeWith(new DisposableObserver<String>() {\n" +
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
                        "    public void rx2subscriptionSavingReturnSubscribeWith() {\n" +
                        "        DisposableObserver<String> disposable = io.reactivex.Observable.just(\"Test\").subscribeWith(new DisposableObserver<String>() {\n" +
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
                        "    public void rx2WithObservable() {\n" +
                        "        io.reactivex.Observable.just(\"test\").subscribe(new Observer<String>() {\n" +
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
                        "    public void rx3ubscriptionWithoutSavingReturn() {\n" +
                        "        io.reactivex.rxjava3.core.Observable.just(\"Test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3subscriptionKeepingReference() {\n" +
                        "        io.reactivex.rxjava3.disposables.Disposable test = io.reactivex.rxjava3.core.Observable.just(\"Test\").subscribe();\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx3subscriptionWithoutSavingReturnSubscribeWith() {\n" +
                        "        io.reactivex.rxjava3.core.Observable.just(\"Test\").subscribeWith(new io.reactivex.rxjava3.observers.DisposableObserver<String>() {\n" +
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
                        "    public void rx3subscriptionSavingReturnSubscribeWith() {\n" +
                        "        io.reactivex.rxjava3.observers.DisposableObserver<String> disposable = io.reactivex.rxjava3.core.Observable.just(\"Test\").subscribeWith(new io.reactivex.rxjava3.observers.DisposableObserver<String>() {\n" +
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
                        "    public void rx3WithObservable() {\n" +
                        "        io.reactivex.rxjava3.core.Observable.just(\"test\").subscribe(new io.reactivex.rxjava3.observers.Observer<String>() {\n" +
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
                        "}\n"));

        assertEquals("src/nl/littlerobots/testproject/DanglingSubscriptionTest.java:16: Error: No reference to the subscription is kept [RxLeakedSubscription]\n" +
                "        Observable.just(\"hi\").subscribe(new Subscriber<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/DanglingSubscriptionTest.java:116: Error: No reference to the subscription is kept [RxLeakedSubscription]\n" +
                "        Observable.just(\"test\").subscribe(new rx.Observer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/DanglingSubscriptionTest.java:135: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "        io.reactivex.Observable.just(\"Test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/DanglingSubscriptionTest.java:143: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "        io.reactivex.Observable.just(\"Test\").subscribeWith(new DisposableObserver<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/DanglingSubscriptionTest.java:205: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "        io.reactivex.rxjava3.core.Observable.just(\"Test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/DanglingSubscriptionTest.java:213: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "        io.reactivex.rxjava3.core.Observable.just(\"Test\").subscribeWith(new io.reactivex.rxjava3.observers.DisposableObserver<String>() {\n" +
                "        ^\n" +
                "6 errors, 0 warnings\n", result);
    }

    public void testRxKotlinSubscribeBy() {
        TestLintTask.lint().files(
                copy("rxjava-2.2.2.jar", "libs/rxjava2.jar"),
                copy("rxjava-3.0.9.jar", "libs/rxjava3.jar"),
                copy("reactive-streams-1.0.3.jar", "libs/reactive-streams.jar"),
                copy("rxkotlin-2.3.0.jar", "libs/rxkotlin.jar"),
                copy("rxkotlin-3.0.1.jar", "libs/rxkotlin-rxjava3.jar"),
                kotlin("package nl.littlerobots.test\n" +
                        "\n" +
                        "import io.reactivex.Observable\n" +
                        "import io.reactivex.rxjava3.core.Observable as ObservableRx3\n" +
                        "import io.reactivex.rxkotlin.subscribeBy\n" +
                        "import io.reactivex.rxjava3.kotlin.subscribeBy as subscribeByRx3\n" +
                        "\n" +
                        "\n" +
                        "fun test() {\n" +
                        "    Observable.just(\"test\").subscribeBy { }\n" +
                        "    ObservableRx3.just(\"test\").subscribeByRx3 { }\n" +
                        "    Observable.just(\"test\").subscribeBy(onError = {})\n" +
                        "    ObservableRx3.just(\"test\").subscribeByRx3(onError = {})\n" +
                        "}")
        ).issues(DanglingSubscriptionDetector.ISSUE).allowCompilationErrors(true).run().expect("src/nl/littlerobots/test/test.kt:10: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "    Observable.just(\"test\").subscribeBy { }\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/test/test.kt:11: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "    ObservableRx3.just(\"test\").subscribeByRx3 { }\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/test/test.kt:12: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "    Observable.just(\"test\").subscribeBy(onError = {})\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/test/test.kt:13: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "    ObservableRx3.just(\"test\").subscribeByRx3(onError = {})\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "4 errors, 0 warnings");
    }

    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
