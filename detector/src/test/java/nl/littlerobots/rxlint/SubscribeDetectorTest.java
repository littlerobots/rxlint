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
        String result = lintProject("SubscriberTest.java.txt=>src/test/nl/littlerobots/testproject/SubscriberTest.java",
                "rxjava-1.1.2.jar=>libs/rxjava.jar",
                "testjavalib.jar=>libs/testlib.jar",
                "rxjava-2.0.5.jar=>libs/rxjava2.jar",
                "reactive-streams-1.0.0.final.jar=>libs/reactive-streams-1.0.0.final.jar");
        assertEquals("src/test/nl/littlerobots/testproject/SubscriberTest.java:56: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {\n" +
                "                         ^\n" +
                "src/test/nl/littlerobots/testproject/SubscriberTest.java:130: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Subscription s = Single.<String>just(null).subscribe(new Action1<String>() {\n" +
                "                         ^\n" +
                "src/test/nl/littlerobots/testproject/SubscriberTest.java:162: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Completable.fromCallable(new Callable<Object>() {\n" +
                "        ^\n" +
                "src/test/nl/littlerobots/testproject/SubscriberTest.java:235: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Observable.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/test/nl/littlerobots/testproject/SubscriberTest.java:277: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Flowable.just(\"Test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/test/nl/littlerobots/testproject/SubscriberTest.java:314: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Completable.complete().subscribe(new Action() {\n" +
                "        ^\n" +
                "src/test/nl/littlerobots/testproject/SubscriberTest.java:350: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Single.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "src/test/nl/littlerobots/testproject/SubscriberTest.java:392: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        io.reactivex.Maybe.just(\"test\").subscribe(new Consumer<String>() {\n" +
                "        ^\n" +
                "8 errors, 0 warnings\n", result);
    }

    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
