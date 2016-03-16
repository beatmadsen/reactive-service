package com.madsen.rx;

import rx.Observable;

/**
 * Created by erikmadsen on 16/03/2016.
 */
public interface ReactiveRestRepository<T> {

    Observable<Boolean> addIfAbsent(T value);

    Observable<Boolean> updateIfPresent(T value);

    Observable<Boolean> removeIfPresent(long id);

    Observable<T> find(long id);

    Observable<T> all();
}
