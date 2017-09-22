package nl.littlerobots.testproject;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;

public class DefaultSchedulerTest {
    public void testDefaultScheduler() {
        io.reactivex.Observable<String> observable = io.reactivex.Observable.just("test").delay(1, TimeUnit.SECONDS).timeout(1, TimeUnit.SECONDS);
    }

    public void testNonDefaultScheduler() {
        io.reactivex.Observable<String> observable = io.reactivex.Observable.just("test").delay(1, TimeUnit.SECONDS, Schedulers.computation());
    }
}
