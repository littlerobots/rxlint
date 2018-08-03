package nl.littlerobots.rxlint;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.TypeConversionUtil;

import org.jetbrains.uast.UCallExpression;

public class RxJavaSubscriberCheck implements SubscribeDetector.SubscriberCheck {
    @Override
    public boolean isMissingOnError(UCallExpression node, PsiMethod method) {
        PsiClass clz = method.getContainingClass();
        if ("rx.Observable".equals(clz.getQualifiedName()) || "rx.Single".equals(clz.getQualifiedName())) {
            return method.getParameterList().getParametersCount() == 0 ||
                    (method.getParameterList().getParametersCount() == 1 &&
                            TypeConversionUtil.erasure(method.getParameterList().getParameters()[0].getType()).equalsToText("rx.functions.Action1"));
        } else if ("rx.Completable".equals(clz.getQualifiedName())) {
            // only a completion callback
            return method.getParameterList().getParametersCount() == 0 ||
                    (method.getParameterList().getParametersCount() == 1 && TypeConversionUtil.erasure(method.getParameterList().getParameters()[0].getType()).equalsToText("rx.functions.Action0"));
        }
        return false;
    }
}
