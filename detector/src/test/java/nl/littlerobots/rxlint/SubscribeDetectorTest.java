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
                "testjavalib.jar=>libs/testlib.jar");
        assertEquals("src/test/nl/littlerobots/testproject/SubscriberTest.java:43: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Subscription s = Observable.<String>just(null).subscribe(new Action1<String>() {\n" +
                "                         ^\n" +
                "src/test/nl/littlerobots/testproject/SubscriberTest.java:117: Error: Subscriber is missing onError [RxSubscribeOnError]\n" +
                "        Subscription s = Single.<String>just(null).subscribe(new Action1<String>() {\n" +
                "                         ^\n" +
                "2 errors, 0 warnings\n", result);
    }

    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
