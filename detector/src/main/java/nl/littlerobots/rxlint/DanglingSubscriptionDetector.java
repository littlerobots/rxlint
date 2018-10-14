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

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UBlockExpression;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.android.tools.lint.detector.api.Lint.skipParentheses;

public class DanglingSubscriptionDetector extends Detector implements Detector.UastScanner {

    static final Issue ISSUE = Issue
            .create("RxLeakedSubscription", "Subscription is leaked",
                    "Generally you should keep a reference to a subscription so that you can unsubscribe or dispose at the appropriate time. Not unsubscribing from the Observable might result memory leaks or other bugs.",
                    Category.CORRECTNESS, 8, Severity.ERROR,
                    new Implementation(DanglingSubscriptionDetector.class, Scope.JAVA_FILE_SCOPE));
    private static final List<String> OBSERVABLE_TYPES = Arrays.asList("rx.Observable", "rx.Single", "rx.Completable",
            "io.reactivex.Observable", "io.reactivex.Flowable",
            "io.reactivex.Single", "io.reactivex.Maybe", "io.reactivex.Completable");

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("subscribe", "subscribeWith");
    }


    private static void reportIfNotHandled(JavaContext context, UElement node, String message) {
        UElement element = skipParentheses(node.getUastParent()).getUastParent();
        if (element instanceof UBlockExpression) {
            context.report(ISSUE, node, context.getLocation(node), message);
        }
    }

    static void reportIfNotHandled(JavaContext context, UElement node) {
        reportIfNotHandled(context, node, "No reference to the disposable is kept");
    }

    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        super.visitMethod(context, node, method);
        if (isRxSubscribeableClass(method.getContainingClass()) && !PsiType.VOID.equals(method.getReturnType())) {
            String message;
            if (isRx2(method.getContainingClass())) {
                message = "No reference to the disposable is kept";
            } else {
                message = "No reference to the subscription is kept";
            }
            reportIfNotHandled(context, node, message);
        }
    }

    private boolean isRx2(PsiClass clz) {
        return clz.getQualifiedName().startsWith("io.reactivex.");
    }

    private boolean isRxSubscribeableClass(PsiClass clz) {
        return OBSERVABLE_TYPES.contains(clz.getQualifiedName());
    }

    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        ArrayList<Class<? extends UElement>> result = new ArrayList<Class<? extends UElement>>();
        result.add(UCallExpression.class);
        return result;
    }

    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new RxKotlinDanglingSubscriptionCheck(context);
    }
}
