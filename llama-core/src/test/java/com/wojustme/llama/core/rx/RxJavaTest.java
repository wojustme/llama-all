package com.wojustme.llama.core.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

/**
 * @author xurenhe
 * @date 2017/12/22
 */
public class RxJavaTest {

    @Test
    public void test_Observer() throws Exception {
        Observable<String> hello = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("hello");
                observableEmitter.onComplete();
            }
        });

        hello.subscribe(new Observer<String>() {

            private Disposable disposable;
            @Override
            public void onSubscribe(Disposable disposable) {
                this.disposable = disposable;
                System.out.println("onsubscribe" + disposable.isDisposed());
            }

            @Override
            public void onNext(String s) {
                System.out.println("recv: " + s);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("ok1...");
            }
        });

        hello.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
//                disposable.dispose();
                System.out.println("onsubscribe" + disposable.isDisposed());
            }

            @Override
            public void onNext(String s) {
                System.out.println("recv2: " + s);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("ok2...");
            }
        });
    }
}
