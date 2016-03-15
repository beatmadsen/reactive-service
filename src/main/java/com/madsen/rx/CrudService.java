package com.madsen.rx;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface CrudService<T> {

    /*
    The service is responsible for the application logic,
    the controller is responsible for adapting and routing the data.
     */

    Collection<T> readAll();

    Optional<T> read(long id);

    <S> S create(T value, OutcomeHandler<S, T> outcomeHandler);

    <S> S update(T value, OutcomeHandler<S, T> outcomeHandler);

    <S> S delete(long id, OutcomeHandler<S, T> outcomeHandler);

    interface OutcomeHandler<S, T> {
        S onAbsentValue(T value, String messsage);

        S onPresentValue(T value, String messsage);

        S onSuccess(T value);
    }
}
