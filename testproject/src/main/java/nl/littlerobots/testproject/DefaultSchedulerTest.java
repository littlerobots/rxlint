package nl.littlerobots.testproject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DefaultSchedulerTest {
    public void testDefaultScheduler() {
        io.reactivex.Observable<String> observable = io.reactivex.Observable.just("test").delay(1, TimeUnit.SECONDS).timeout(1, TimeUnit.SECONDS);
    }

    public void testDefaultWithObserveOn() {
        io.reactivex.Observable<String> observable = io.reactivex.Observable.just("test").delay(1, TimeUnit.SECONDS).observeOn(Schedulers.io());
        Observable<List<String>> observable2 = Observable.just("test").delay(1, TimeUnit.SECONDS).buffer(1).observeOn(Schedulers.io());
    }

    public void testNonDefaultScheduler() {
        io.reactivex.Observable<String> observable = io.reactivex.Observable.just("test").delay(1, TimeUnit.SECONDS, Schedulers.computation());
    }
}
