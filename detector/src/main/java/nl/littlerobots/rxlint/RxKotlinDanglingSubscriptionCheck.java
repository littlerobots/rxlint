package nl.littlerobots.rxlint;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.JavaContext;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.TypeConversionUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;

import static nl.littlerobots.rxlint.DanglingSubscriptionDetector.reportIfNotHandled;
import static nl.littlerobots.rxlint.ObservableTypesUtil.RXJAVA_TYPES;

public class RxKotlinDanglingSubscriptionCheck extends UElementHandler {
    private final JavaContext context;

    RxKotlinDanglingSubscriptionCheck(@NotNull JavaContext context) {
        this.context = context;
    }

    @Override
    public void visitCallExpression(@NotNull UCallExpression node) {
        if ("subscribeBy".equals(node.getMethodName())) {
            PsiType type = node.getReceiverType();
            String erasedType = TypeConversionUtil.erasure(type).getCanonicalText();
            if (RXJAVA_TYPES.contains(erasedType)) {
                reportIfNotHandled(context, node);
            }
        }
    }
}
