package nl.littlerobots.rxlint;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.TypeConversionUtil;

import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UExpression;
import org.jetbrains.uast.UQualifiedReferenceExpression;

import static nl.littlerobots.rxlint.ObservableTypesUtil.ALL_TYPES;
import static nl.littlerobots.rxlint.ObservableTypesUtil.AUTODISPOSE_TYPES;
import static nl.littlerobots.rxlint.ObservableTypesUtil.isErrorSuppressingOperator;

public class RxJava2And3SubscriberCheck implements SubscribeDetector.SubscriberCheck {

    @Override
    public boolean isMissingOnError(UCallExpression node, PsiMethod method) {
        PsiClass clz = method.getContainingClass();
        String type = clz.getQualifiedName();
        if (ALL_TYPES.contains(type)) {
            return hasNoErrorHandling(method) && canProduceError(node, type);
        }
        return false;
    }

    private boolean canProduceError(UCallExpression node, String type) {
        UExpression receiver = node.getReceiver();
        if (AUTODISPOSE_TYPES.contains(type) && receiver instanceof UQualifiedReferenceExpression) {
            receiver = ((UQualifiedReferenceExpression) receiver).getReceiver();
        }
        return !isErrorSuppressingOperator(receiver, type);
    }

    private boolean hasNoErrorHandling(PsiMethod method) {
        // subscribe() calls that have no return type are custom Observers whose error handling we cannot check
        if (PsiType.VOID.equals(method.getReturnType())) {
            return false;
        }
        int argCount = method.getParameterList().getParametersCount();
        // when more than one argument is given, there is usually one error callback included
        if (argCount > 1) {
            return false;
        }
        // no arguments, no error handling
        if (argCount == 0) {
            return true;
        }
        // if we have only one argument, check that it's not a BiConsumer
        PsiParameter parameter = method.getParameterList().getParameters()[0];
        String canonicalClassName = TypeConversionUtil.erasure(parameter.getType()).getCanonicalText();
        return !"io.reactivex.functions.BiConsumer".equals(canonicalClassName) &&
                !"io.reactivex.rxjava3.functions.BiConsumer".equals(canonicalClassName);
    }
}
