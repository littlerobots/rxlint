package nl.littlerobots.rxlint;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.TypeConversionUtil;

import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UExpression;
import org.jetbrains.uast.UQualifiedReferenceExpression;
import org.jetbrains.uast.USimpleNameReferenceExpression;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RxJava2SubscriberCheck implements SubscribeDetector.SubscriberCheck {

    private static final String OBSERVABLE_TYPE = "io.reactivex.Observable";
    private static final String FLOWABLE_TYPE = "io.reactivex.Flowable";
    private static final String COMPLETABLE_TYPE = "io.reactivex.Completable";
    private static final String SINGLE_TYPE = "io.reactivex.Single";
    private static final String MAYBE_TYPE = "io.reactivex.Maybe";

    private static Map<String, List<String>> ERROR_HANDLING_OPERATORS = new HashMap<String, List<String>>(5);

    static {
        ERROR_HANDLING_OPERATORS.put(OBSERVABLE_TYPE, Collections.singletonList("onErrorReturnItem("));
        ERROR_HANDLING_OPERATORS.put(FLOWABLE_TYPE, Collections.singletonList("onErrorReturnItem("));
        ERROR_HANDLING_OPERATORS.put(COMPLETABLE_TYPE, Collections.singletonList("onErrorComplete()"));
        ERROR_HANDLING_OPERATORS.put(MAYBE_TYPE, Arrays.asList("onErrorComplete()", "onErrorReturnItem("));
    }

    @Override
    public boolean isMissingOnError(UCallExpression node, PsiMethod method) {
        PsiClass clz = method.getContainingClass();
        String type = clz.getQualifiedName();
        if (OBSERVABLE_TYPE.equals(type) ||
                FLOWABLE_TYPE.equals(type) ||
                COMPLETABLE_TYPE.equals(type) ||
                SINGLE_TYPE.equals(type) ||
                MAYBE_TYPE.equals(type)) {
            return hasNoErrorHandling(method) && canProduceError(node, type);
        }
        return false;
    }

    private boolean canProduceError(UCallExpression node, String type) {
        return !isErrorSuppressingOperator(node.getReceiver(), type);
    }

    private boolean isErrorSuppressingOperator(UExpression receiver, String type) {
        List<String> methods = ERROR_HANDLING_OPERATORS.get(type);
        if (methods == null || methods.isEmpty()) {
            return false;
        }
        if (receiver == null) {
            return false;
        }
        if (receiver instanceof UQualifiedReferenceExpression && !(receiver instanceof USimpleNameReferenceExpression)) {
            //FIXME Change to a proper way to get the method name and params
            String selector = ((UQualifiedReferenceExpression) receiver).getSelector().toString();
            for (String method : methods) {
                if (selector.startsWith(method)) {
                    return true;
                }
            }
        }
        return false;
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
        return !"io.reactivex.functions.BiConsumer".equals(TypeConversionUtil.erasure(parameter.getType()).getCanonicalText());
    }
}
