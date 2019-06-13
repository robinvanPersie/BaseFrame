package com.antimage.basemodule.event;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by xuyuming on 2018/10/25.
 */

public class RxBus {

    private static volatile RxBus instance;
    private Subject bus;

    private RxBus() {
        bus = PublishSubject.create();
    }

    private static RxBus getDefault() {
        RxBus bus = instance;
        if (bus == null) {
            synchronized (RxBus.class) {
                if (bus == null) {
                    bus = new RxBus();
                    instance = bus;
                }
            }
        }
        return bus;
    }

    public static <T> Observable<T> register(Class<T> eventType) {
        return getDefault().toObservable(eventType);
    }

    private <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public static void post(Object o) {
        getDefault().bus.onNext(o);
    }
}
