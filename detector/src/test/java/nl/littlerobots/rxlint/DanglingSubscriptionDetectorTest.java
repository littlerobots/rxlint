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
        String result = lintProject(TestFiles.copy("rxjava-1.3.0.jar", "libs/rxjava.jar", this),
                TestFiles.copy("rxjava-2.0.5.jar", "libs/rxjava2.jar", this),
                TestFiles.copy("reactive-streams-1.0.0.final.jar", "libs/reactive-streams-1.0.0.final.jar", this),
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
                "4 errors, 0 warnings\n", result);
    }

    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
