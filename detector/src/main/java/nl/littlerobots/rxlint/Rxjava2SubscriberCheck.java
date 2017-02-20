package nl.littlerobots.rxlint;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;

public class Rxjava2SubscriberCheck implements SubscribeDetector.SubscriberCheck {
    @Override
    public boolean isMissingOnError(PsiMethod method) {
        PsiClass clz = method.getContainingClass();
        String type = clz.getQualifiedName();
        if ("io.reactivex.Observable".equals(type) ||
                "io.reactivex.Flowable".equals(type) ||
                "io.reactivex.Completable".equals(type) ||
                "io.reactivex.Single".equals(type) ||
                "io.reactivex.Maybe".equals(type)) {
            return method.getReturnType() != PsiType.VOID && method.getParameterList().getParametersCount() == 1;
        }
        return false;
    }
}
