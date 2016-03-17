package com.madsen.rx;

import rx.Observable;

/**
 * Created by erikmadsen on 17/03/2016.
 */
public interface ReactiveCrudService<T> {

    /*
    The service is responsible for the application logic,
    the controller is responsible for adapting and routing the data.
     */

    Observable<T> readAll();

    Observable<T> read(long id);

    <S> Observable<S> create(T value, OutcomeHandler<S, T> outcomeHandler);

    <S> Observable<S> update(T value, OutcomeHandler<S, T> outcomeHandler);

    <S> Observable<S> delete(long id, OutcomeHandler<S, T> outcomeHandler);

    interface OutcomeHandler<S, T> {

        S onAbsentValue(T value, String messsage);

        S onPresentValue(T value, String messsage);

        S onSuccess(T value);
    }
}
