package nl.littlerobots.testproject;

import com.uber.autodispose.AutoDispose;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class AutodisposeTest {

    public void errorHandlerMissing() {
        Observable.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
        Flowable.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
        Maybe.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
        Single.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
        Completable.complete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe();
    }

    public void withErrorHandling() {
        Observable.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(s -> {

        }, t -> {

        });

        Observable.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Flowable.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(r -> {
        }, t -> {
        });
        Flowable.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        Maybe.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(r -> {
        }, v -> {
        });
        Maybe.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        Single.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(r -> {
        }, t -> {
        });
        Single.just("test").as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

        Completable.complete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe(() -> {

        }, t -> {
        });

        Completable.complete().as(AutoDispose.autoDisposable(Completable.complete())).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
