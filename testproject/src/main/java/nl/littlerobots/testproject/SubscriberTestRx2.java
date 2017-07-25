package nl.littlerobots.testproject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class SubscriberTestRx2 {
    public void rx2ObservableWithSubscriber() {
        io.reactivex.Observable.just("test").subscribeWith(new DisposableObserver<String>() {
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
    }

    public void rx2ObservableWithOnError() {
        io.reactivex.Observable.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2ObservableWithoutOnError() {
        io.reactivex.Observable.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    public void rx2ObservableNoArguments() {
        io.reactivex.Observable.just("test").subscribe();
    }

    public void rx2FlowableWithSubscriber() {
        io.reactivex.Flowable.just("test").subscribeWith(new DisposableSubscriber<String>() {
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
    }

    public void rx2FlowableWithOnError() {
        io.reactivex.Flowable.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2FlowableWithoutOnError() {
        io.reactivex.Flowable.just("Test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    public void rx2FlowableNoArguments() {
        io.reactivex.Flowable.just("test").subscribe();
    }

    public void rx2CompletableWithSubscriber() {
        io.reactivex.Completable.complete().subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void rx2CompletableWithOnError() {
        io.reactivex.Completable.complete().subscribe(new Action() {
            @Override
            public void run() throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2CompletableWithoutOnError() {
        io.reactivex.Completable.complete().subscribe(new Action() {
            @Override
            public void run() throws Exception {
            }
        });
    }

    public void rx2CompletableNoArguments() {
        io.reactivex.Completable.complete().subscribe();
    }


    public void rx2SingleWithSubscriber() {
        io.reactivex.Single.just("test").subscribeWith(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    public void rx2SingleWithOnError() {
        io.reactivex.Single.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2SingleWithoutOnError() {
        io.reactivex.Single.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    public void rx2SingleNoArguments() {
        io.reactivex.Single.just("test").subscribe();
    }

    public void rx2MaybeWithSubscriber() {
        io.reactivex.Maybe.just("test").subscribeWith(new DisposableMaybeObserver<String>() {
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
    }

    public void rx2MaybeWithOnError() {
        io.reactivex.Maybe.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void rx2MaybeWithoutOnError() {
        io.reactivex.Maybe.just("test").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    public void rx2MaybeNoArguments() {
        io.reactivex.Maybe.just("test").subscribe();
    }


    public void rx2WithObserver() {
        io.reactivex.Observable.just("test").subscribe(new io.reactivex.Observer<String>() {
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
    }

    public void rx2WithSingleObserver() {
        io.reactivex.Single.just(1).subscribe(new io.reactivex.SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void rx2WithMaybeObserver() {
        io.reactivex.Maybe.just(1).subscribe(new io.reactivex.MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void rx2WithCompletableObserver() {
        io.reactivex.Completable.complete().subscribe(new io.reactivex.CompletableObserver() {
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

    public void rx2WithSingleBiConsumer() {
        io.reactivex.Single.just(1).subscribe(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer integer, Throwable throwable) throws Exception {

            }
        });
    }

}
