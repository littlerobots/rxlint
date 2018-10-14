package nl.littlerobots.testproject;


import com.uber.autodispose.AutoDispose;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class AutoDisposeOnErrorOperator {
    public void autoDisposeOnerrorHandling() {
        Completable.fromAction(() -> {

        }).onErrorComplete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe();

        Observable.just("test").onErrorReturnItem("ha ha").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
        Flowable.just("test").onErrorReturnItem("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
        Maybe.just("test").onErrorComplete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
        Maybe.just("test").onErrorReturnItem("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
    }
}
