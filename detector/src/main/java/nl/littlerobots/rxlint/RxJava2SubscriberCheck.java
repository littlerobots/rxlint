package nl.littlerobots.rxlint;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.TypeConversionUtil;

public class RxJava2SubscriberCheck implements SubscribeDetector.SubscriberCheck {
    @Override
    public boolean isMissingOnError(PsiMethod method) {
        PsiClass clz = method.getContainingClass();
        String type = clz.getQualifiedName();
        if ("io.reactivex.Observable".equals(type) ||
                "io.reactivex.Flowable".equals(type) ||
                "io.reactivex.Completable".equals(type) ||
                "io.reactivex.Single".equals(type) ||
                "io.reactivex.Maybe".equals(type)) {
            return hasNoErrorHandling(method);
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
