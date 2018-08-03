package nl.littlerobots.testproject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class ErrorHandlingOperatorsTest {

    public void surpressesErrors() {
        Completable.fromAction(() -> {

        }).onErrorComplete().subscribe();

        Observable.just("test").onErrorReturnItem("ha ha").subscribe();
        Flowable.just("test").onErrorReturnItem("test").subscribe();
        Maybe.just("test").onErrorComplete().subscribe();
        Maybe.just("test").onErrorReturnItem("test").subscribe();
    }

    public void doesNotSupressErrors() {
        Completable.fromAction(() -> {

        }).onErrorComplete().cache().subscribe();

        Completable.fromAction(() -> {

        }).onErrorComplete(t -> false).subscribe();


        Observable.just("test").onErrorReturnItem("ha ha").map(v -> v).subscribe();
        Flowable.just("test").onErrorReturnItem("test").map(v -> v).subscribe();
        Maybe.just("test").onErrorComplete(t -> false).subscribe();
        Maybe.just("test").onErrorComplete().map(v -> v).subscribe();
        Maybe.just("test").onErrorReturnItem("test").map(v -> v).subscribe();
    }
}
