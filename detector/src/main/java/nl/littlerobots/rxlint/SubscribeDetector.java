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

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.util.TypeConversionUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UExpression;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static nl.littlerobots.rxlint.ObservableTypesUtil.isErrorSuppressingOperator;

public class SubscribeDetector extends Detector implements Detector.UastScanner {

    static final Issue ISSUE = Issue
            .create("RxSubscribeOnError", "Subscriber is missing onError handling",
                    "Every Observable stream can report errors that should be handled using onError. Not implementing onError will throw an exception at runtime which can be hard to debug when the error is thrown on a Scheduler that is not the invoking thread.",
                    Category.CORRECTNESS, 8, Severity.ERROR,
                    new Implementation(SubscribeDetector.class, Scope.JAVA_FILE_SCOPE));
    private static final SubscriberCheck[] CHECKS = new SubscriberCheck[]{new RxJavaSubscriberCheck(), new RxJava2And3SubscriberCheck()};

    private static void report(JavaContext context, UElement node) {
        context.report(ISSUE, node, context.getLocation(node), "Subscriber is missing onError");
    }

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("subscribe", "subscribeBy", "blockingSubscribeBy");
    }

    @Override
    public void visitMethod(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod method) {
        if ("subscribeBy".equals(method.getName()) || "blockingSubscribeBy".equals(method.getName())) {
            handleSubscribeBy(context, node, method);
        } else {
            for (SubscriberCheck check : CHECKS) {
                if (check.isMissingOnError(node, method)) {
                    report(context, node);
                    return;
                }
            }
        }
    }

    private void handleSubscribeBy(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod method) {
        boolean inRx2 = context.getEvaluator().isMemberInClass(method, "io.reactivex.rxkotlin.SubscribersKt");
        boolean inRx3 = context.getEvaluator().isMemberInClass(method, "io.reactivex.rxjava3.kotlin.SubscribersKt");
        if (inRx2 || inRx3) {
            String erasedType = TypeConversionUtil.erasure(node.getReceiverType()).getCanonicalText();
            if (isErrorSuppressingOperator(node.getReceiver(), erasedType)) {
                return;
            }
            PsiMethod resolvedMethod = node.resolve();
            if (resolvedMethod != null) {
                Map<UExpression, PsiParameter> mapping = context.getEvaluator().computeArgumentMapping(node, resolvedMethod);
                for (PsiParameter parameter : mapping.values()) {
                    if ("onError".equals(parameter.getName())) {
                        // onError has been supplied
                        return;
                    }
                }
                report(context, node);
            }
        }
    }

    interface SubscriberCheck {
        boolean isMissingOnError(UCallExpression node, PsiMethod method);
    }
}
