package nl.littlerobots.rxlint;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.JavaContext;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.TypeConversionUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.kotlin.KotlinUFunctionCallExpression;

import static nl.littlerobots.rxlint.ObservableTypesUtil.RXJAVA_TYPES;
import static nl.littlerobots.rxlint.ObservableTypesUtil.isErrorSuppressingOperator;
import static nl.littlerobots.rxlint.SubscribeDetector.report;

public class RxKotlinSubscriberCheck extends UElementHandler {

    private final JavaContext context;

    RxKotlinSubscriberCheck(@NotNull JavaContext context) {
        this.context = context;
    }

    @Override
    public void visitCallExpression(@NotNull UCallExpression node) {
        if ("subscribeBy".equals(node.getMethodName()) && node instanceof KotlinUFunctionCallExpression) {
            PsiType type = node.getReceiverType();
            String erasedType = TypeConversionUtil.erasure(type).getCanonicalText();
            if (RXJAVA_TYPES.contains(erasedType)) {
                if (!isErrorSuppressingOperator(node.getReceiver(), erasedType) && !handlesError((KotlinUFunctionCallExpression) node)) {
                    report(context, node);
                }
            }
        }
    }

    private boolean handlesError(KotlinUFunctionCallExpression expression) {
        for (int i = 0; i < expression.getValueArgumentCount(); i++) {
            if ("kotlin.jvm.functions.Function1<? super java.lang.Throwable,? extends kotlin.Unit>".equals(expression.getValueArguments().get(i).getExpressionType().getCanonicalText())) {
                return true;
            }
        }
        return false;
    }
}