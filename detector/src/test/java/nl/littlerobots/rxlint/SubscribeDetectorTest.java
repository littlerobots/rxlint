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

    public void testSubscribeCheck() throws Exception {
        String result = lintProject(TestFiles.copy("rxjava-1.1.2.jar", "libs/rxjava.jar", this),
                TestFiles.copy("rxjava-2.0.5.jar", "libs/rxjava2.jar", this),
                TestFiles.copy("reactive-streams-1.0.0.final.jar", "libs/reactive-streams-1.0.0.final.jar", this),
                TestFiles.copy("testjavalib.jar", "libs/testjavalib.jar", this),
                TestFiles.java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import java.util.concurrent.Callable;\n" +
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
                        "import nl.littlerobots.test.MySubscriber;\n" +
                        "import rx.Completable;\n" +
                        "import rx.Observable;\n" +
                        "import rx.Observer;\n" +
                        "import rx.Single;\n" +
                        "import rx.Subscriber;\n" +
                        "import rx.Subscription;\n" +
                        "import rx.functions.Action0;\n" +
                        "import rx.functions.Action1;\n" +
                        "\n" +
                        "public class SubscriberTest {\n" +
                        "    public void subscribeWithError() {\n" +
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
                        "    public void subscribeWithoutError() {\n" +
                        "        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {\n" +
                        "            @Override\n" +
                        "            public void call(String s) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void subscribeWithSubscriber() {\n" +
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
                        "    public void subscribeWithObserver() {\n" +
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
                        "        }).subscribe(new Action1<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void call(Throwable error) {\n" +
                        "\n" +
                        "            }\n" +
                        "        }, new Action0() {\n" +
                        "            @Override\n" +
                        "            public void call() {\n" +
                        "\n" +
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
                        "    // Rx2 types\n" +
                        "\n" +
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
                        "            @Override public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override public void onSuccess(Integer integer) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2WithMaybeObserver() {\n" +
                        "        io.reactivex.Maybe.just(1).subscribe(new io.reactivex.MaybeObserver<Integer>() {\n" +
                        "            @Override public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override public void onSuccess(Integer integer) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2WithCompletableObserver() {\n" +
                        "        io.reactivex.Completable.complete().subscribe(new io.reactivex.CompletableObserver() {\n" +
                        "            @Override public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override public void onComplete() {\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override public void onError(Throwable e) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    public void rx2WithSingleBiConsumer() {\n" +
                        "        io.reactivex.Single.just(1).subscribe(new BiConsumer<Integer, Throwable>() {\n" +
                        "            @Override public void accept(Integer integer, Throwable throwable) throws Exception {\n" +
                        "\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "}\n"));

        assertEquals("src/nl/littlerobots/testproject/SubscriberTest.java:40: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {\n" +
                "                         ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTest.java:114: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Subscription s = Single.<String>just(null).subscribe(new Action1<String>() {\n" +
                "                         ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTest.java:146: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.fromCallable(new Callable<Object>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTest.java:219: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Observable.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTest.java:261: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Flowable.just(\"Test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTest.java:298: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Completable.complete().subscribe(new Action() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTest.java:334: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Single.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/nl/littlerobots/testproject/SubscriberTest.java:376: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Maybe.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "8 errors, 0 warnings\n", result);
    }

    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
