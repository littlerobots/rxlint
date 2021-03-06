package nl.littlerobots.rxlint;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

import org.jetbrains.uast.UExpression;
import org.jetbrains.uast.UastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ObservableTypesUtil {
    private static final String RX2_OBSERVABLE_TYPE = "io.reactivex.Observable";
    private static final String RX2_FLOWABLE_TYPE = "io.reactivex.Flowable";
    private static final String RX2_COMPLETABLE_TYPE = "io.reactivex.Completable";
    private static final String RX2_SINGLE_TYPE = "io.reactivex.Single";
    private static final String RX2_MAYBE_TYPE = "io.reactivex.Maybe";
    private static final String RX3_OBSERVABLE_TYPE = "io.reactivex.rxjava3.core.Observable";
    private static final String RX3_FLOWABLE_TYPE = "io.reactivex.rxjava3.core.Flowable";
    private static final String RX3_COMPLETABLE_TYPE = "io.reactivex.rxjava3.core.Completable";
    private static final String RX3_SINGLE_TYPE = "io.reactivex.rxjava3.core.Single";
    private static final String RX3_MAYBE_TYPE = "io.reactivex.rxjava3.core.Maybe";
    static final List<String> RXJAVA_TYPES = Arrays.asList(
            RX2_OBSERVABLE_TYPE, RX3_OBSERVABLE_TYPE,
            RX2_FLOWABLE_TYPE, RX3_FLOWABLE_TYPE,
            RX2_COMPLETABLE_TYPE, RX3_COMPLETABLE_TYPE,
            RX2_SINGLE_TYPE, RX3_SINGLE_TYPE,
            RX2_MAYBE_TYPE, RX3_MAYBE_TYPE);
    private static final String AUTODISPOSE_OBSERVABLE_PROXY_TYPE = "com.uber.autodispose.ObservableSubscribeProxy";
    private static final String AUTODISPOSE_FLOWABLE_PROXY_TYPE = "com.uber.autodispose.FlowableSubscribeProxy";
    private static final String AUTODISPOSE_COMPLETABLE_PROXY_TYPE = "com.uber.autodispose.CompletableSubscribeProxy";
    private static final String AUTODISPOSE_SINGLE_PROXY_TYPE = "com.uber.autodispose.SingleSubscribeProxy";
    private static final String AUTODISPOSE_MAYBE_PROXY_TYPE = "com.uber.autodispose.MaybeSubscribeProxy";
    static final List<String> AUTODISPOSE_TYPES = Arrays.asList(
            AUTODISPOSE_OBSERVABLE_PROXY_TYPE,
            AUTODISPOSE_FLOWABLE_PROXY_TYPE,
            AUTODISPOSE_COMPLETABLE_PROXY_TYPE,
            AUTODISPOSE_SINGLE_PROXY_TYPE,
            AUTODISPOSE_MAYBE_PROXY_TYPE);
    private static final Map<String, List<ErrorHandlingOperator>> ERROR_HANDLING_OPERATORS = new HashMap<String, List<ErrorHandlingOperator>>(10);

    static final List<String> ALL_TYPES = new ArrayList<String>(RXJAVA_TYPES.size() + AUTODISPOSE_TYPES.size());

    static {
        ALL_TYPES.addAll(RXJAVA_TYPES);
        ALL_TYPES.addAll(AUTODISPOSE_TYPES);

        ERROR_HANDLING_OPERATORS.put(RX2_OBSERVABLE_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorReturnItem", true)));
        ERROR_HANDLING_OPERATORS.put(RX3_OBSERVABLE_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorReturnItem", true)));
        ERROR_HANDLING_OPERATORS.put(RX2_FLOWABLE_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorReturnItem", true)));
        ERROR_HANDLING_OPERATORS.put(RX3_FLOWABLE_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorReturnItem", true)));
        ERROR_HANDLING_OPERATORS.put(RX2_COMPLETABLE_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorComplete")));
        ERROR_HANDLING_OPERATORS.put(RX3_COMPLETABLE_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorComplete")));
        ERROR_HANDLING_OPERATORS.put(RX2_MAYBE_TYPE, Arrays.asList(new ErrorHandlingOperator("onErrorComplete"), new ErrorHandlingOperator("onErrorReturnItem", true)));
        ERROR_HANDLING_OPERATORS.put(RX3_MAYBE_TYPE, Arrays.asList(new ErrorHandlingOperator("onErrorComplete"), new ErrorHandlingOperator("onErrorReturnItem", true)));

        ERROR_HANDLING_OPERATORS.put(AUTODISPOSE_OBSERVABLE_PROXY_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorReturnItem", true)));
        ERROR_HANDLING_OPERATORS.put(AUTODISPOSE_FLOWABLE_PROXY_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorReturnItem", true)));
        ERROR_HANDLING_OPERATORS.put(AUTODISPOSE_COMPLETABLE_PROXY_TYPE, Collections.singletonList(new ErrorHandlingOperator("onErrorComplete")));
        ERROR_HANDLING_OPERATORS.put(AUTODISPOSE_MAYBE_PROXY_TYPE, Arrays.asList(new ErrorHandlingOperator("onErrorComplete"), new ErrorHandlingOperator("onErrorReturnItem", true)));
    }

    static boolean isErrorSuppressingOperator(UExpression receiver, String type) {
        List<ErrorHandlingOperator> methods = ERROR_HANDLING_OPERATORS.get(type);
        if (methods == null || methods.isEmpty()) {
            return false;
        }
        if (receiver == null) {
            return false;
        }
        PsiElement element = UastUtils.tryResolve(receiver);
        if (element instanceof PsiMethod) {
            for (ErrorHandlingOperator method : methods) {
                if (method.matches((PsiMethod) element)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class ErrorHandlingOperator {
        private final String name;
        private final boolean hasParameter;

        ErrorHandlingOperator(String name, boolean hasParameter) {
            this.name = name;
            this.hasParameter = hasParameter;
        }

        ErrorHandlingOperator(String name) {
            this(name, false);
        }

        boolean matches(PsiMethod method) {
            return name.equals(method.getName()) && ((hasParameter && method.getParameterList().getParametersCount() == 1) || (!hasParameter && method.getParameterList().isEmpty()));
        }
    }
}
