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

public class DefaultSchedulerDetectorTest extends LintDetectorTest {

    @Override
    protected Detector getDetector() {
        return new DefaultSchedulerDetector();
    }

    @Override
    protected List<Issue> getIssues() {
        return Collections.singletonList(DefaultSchedulerDetector.ISSUE);
    }

    public void testDefaultScheduler() throws Exception {
        String result = lintProject(TestFiles.copy("rxjava-2.0.5.jar", "libs/rxjava2.jar", this),
                TestFiles.copy("reactive-streams-1.0.0.final.jar", "libs/reactive-streams-1.0.0.final.jar", this),
                TestFiles.copy("testjavalib.jar", "libs/testjavalib.jar", this),
                TestFiles.java("package nl.littlerobots.testproject;\n" +
                        "\n" +
                        "import java.util.concurrent.TimeUnit;\n" +
                        "\n" +
                        "import io.reactivex.schedulers.Schedulers;\n" +
                        "\n" +
                        "public class DefaultSchedulerTest {\n" +
                        "    public void testDefaultScheduler() {\n" +
                        "        io.reactivex.Observable<String> observable = io.reactivex.Observable.just(\"test\").delay(1, TimeUnit.SECONDS).timeout(1, TimeUnit.SECONDS);\n" +
                        "    }\n" +
                        "    \n" +
                        "    public void testNonDefaultScheduler() {\n" +
                        "        io.reactivex.Observable<String> observable = io.reactivex.Observable.just(\"test\").delay(1, TimeUnit.SECONDS, Schedulers.computation());\n" +
                        "    }\n" +
                        "}\n"));

        assertEquals("src/nl/littlerobots/testproject/DefaultSchedulerTest.java:9: Warning: delay() is using its default scheduler [RxDefaultScheduler]\n" +
                "        io.reactivex.Observable<String> observable = io.reactivex.Observable.just(\"test\").delay(1, TimeUnit.SECONDS).timeout(1, TimeUnit.SECONDS);\n" +
                "                                                     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "src/nl/littlerobots/testproject/DefaultSchedulerTest.java:9: Warning: timeout() is using its default scheduler [RxDefaultScheduler]\n" +
                "        io.reactivex.Observable<String> observable = io.reactivex.Observable.just(\"test\").delay(1, TimeUnit.SECONDS).timeout(1, TimeUnit.SECONDS);\n" +
                "                                                     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "0 errors, 2 warnings\n", result);
    }

    @Override
    protected boolean allowCompilationErrors() {
        return false;
    }
}
