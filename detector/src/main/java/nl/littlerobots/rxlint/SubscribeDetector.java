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

import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.Collections;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.MethodInvocation;

public class SubscribeDetector extends Detector implements Detector.JavaScanner {

    static final Issue ISSUE = Issue
            .create("RxSubscribeOnError", "Subscriber is missing onError handling",
                    "Every Observable stream can report errors that should be handled using onError. Not implementing onError will throw an OnErrorNotImplementedException at runtime which can be hard to debug when the error is thrown on a Scheduler that is not the invoking thread.",
                    Category.CORRECTNESS, 8, Severity.ERROR,
                    new Implementation(SubscribeDetector.class, Scope.JAVA_FILE_SCOPE));


    @Override
    public List<String> getApplicableMethodNames() {
        return Collections.singletonList("subscribe");
    }

    @Override
    public void visitMethod(JavaContext context, AstVisitor visitor, MethodInvocation node) {
        super.visitMethod(context, visitor, node);
        Object resolvedNode = context.resolve(node);
        if (!(resolvedNode instanceof JavaParser.ResolvedMethod)) {
            return;
        }
        JavaParser.ResolvedMethod resolvedMethod = (JavaParser.ResolvedMethod) resolvedNode;
        if (isRxSubscribeableClass(resolvedMethod.getContainingClass()) &&
                resolvedMethod.getArgumentCount() == 1 &&
                resolvedMethod.getArgumentType(0).getSignature().startsWith("rx.functions.Action1")) {
            context.report(ISSUE, node, context.getLocation(node), "Subscriber is missing onError");
        }
    }

    private boolean isRxSubscribeableClass(JavaParser.ResolvedClass clz) {
        return clz.isSubclassOf("rx.Observable", false) || clz.isSubclassOf("rx.Single", false);
    }
}
