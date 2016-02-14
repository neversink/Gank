package com.neversink.gank.util;

import rx.Observable;

/**
 * Created by never on 16/1/27.
 */
public class Stream<T> extends Observable<T>{

    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * <p>
     * <em>Note:</em> Use {@link #create(OnSubscribe)} to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * @param f {@link OnSubscribe} to be executed when {@link #subscribe(Subscriber)} is called
     */
    protected Stream(OnSubscribe<T> f) {
        super(f);
    }
}
