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

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.client.api.Vendor;
import com.android.tools.lint.detector.api.ApiKt;
import com.android.tools.lint.detector.api.Issue;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class RxIssueRegistry extends IssueRegistry {

    @Nullable
    @Override
    public Vendor getVendor() {
        return new Vendor("Little Robots", "rxlint", "https://github.com/littlerobots/rxlint");
    }

    @Override
    public int getApi() {
        return ApiKt.CURRENT_API;
    }

    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(SubscribeDetector.ISSUE, DanglingSubscriptionDetector.ISSUE, DefaultSchedulerDetector.ISSUE);
    }
}
