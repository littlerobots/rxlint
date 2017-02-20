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
        String result = lintProject("DanglingSubscriptionTest.java.txt=>src/test/nl/littlerobots/testproject/DanglingSubscriptionTest.java",
                "rxjava-1.1.2.jar=>libs/rxjava.jar",
                "rxjava-2.0.5.jar=>libs/rxjava2.jar",
                "reactive-streams-1.0.0.final.jar=>libs/reactive-streams-1.0.0.final.jar");

        assertEquals("src/test/nl/littlerobots/testproject/DanglingSubscriptionTest.java:32: Error: No reference to the subscription is kept [RxLeakedSubscription]\n" +
                "        Observable.just(\"hi\").subscribe(new Subscriber<String>() {\n" +
                "        ^\n" +
                "src/test/nl/littlerobots/testproject/DanglingSubscriptionTest.java:132: Error: No reference to the subscription is kept [RxLeakedSubscription]\n" +
                "        Observable.just(\"test\").subscribe(new rx.Observer<String>() {\n" +
                "        ^\n" +
                "src/test/nl/littlerobots/testproject/DanglingSubscriptionTest.java:151: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "        io.reactivex.Observable.just(\"Test\").subscribe();\n" +
                "        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/test/nl/littlerobots/testproject/DanglingSubscriptionTest.java:159: Error: No reference to the disposable is kept [RxLeakedSubscription]\n" +
                "        io.reactivex.Observable.just(\"Test\").subscribeWith(new DisposableObserver<String>() {\n" +
                "        ^\n" +
                "4 errors, 0 warnings\n", result);
    }

    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
