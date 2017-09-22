package nl.littlerobots.rxlint;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;

import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UIdentifier;
import org.jetbrains.uast.UMethod;
import org.jetbrains.uast.UastUtils;
import org.jetbrains.uast.visitor.AbstractUastVisitor;

import java.util.Collections;
import java.util.List;

public class DefaultSchedulerDetector extends Detector implements Detector.UastScanner {
    static final Issue ISSUE = Issue
            .create("RxDefaultScheduler", "Using a default scheduler",
                    "Some operators use their own scheduler which can lead to unexpected threading issues. Prefer to use an overload of the operator that takes a Scheduler as a parameter to prevent these issues.",
                    Category.CORRECTNESS, 8, Severity.WARNING,
                    new Implementation(DefaultSchedulerDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.<Class<? extends UElement>>singletonList(UMethod.class);
    }

    @Override
    public UElementHandler createUastHandler(final JavaContext context) {
        return new UElementHandler() {
            @Override
            public void visitMethod(UMethod uMethod) {
                uMethod.accept(new AbstractUastVisitor() {

                    @Override
                    public boolean visitCallExpression(UCallExpression node) {
                        UIdentifier identifier = node.getMethodIdentifier();

                        if (identifier != null && identifier.getUastParent() != null) {
                            PsiMethod method = (PsiMethod) UastUtils.tryResolve(identifier.getUastParent());
                            PsiAnnotation annotation = AnnotationUtil.findAnnotation(method, "io.reactivex.annotations.SchedulerSupport");
                            if (annotation != null) {
                                String value = AnnotationUtil.getStringAttributeValue(annotation, null);
                                if (!("none".equals(value) || "custom".equals(value))) {
                                    context.report(ISSUE, context.getLocation(node), String.format("%s() is using its default scheduler", identifier.getName()));
                                }
                            }
                        }
                        return false;
                    }
                });
            }
        };
    }
}
