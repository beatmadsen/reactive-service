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

    <S> S create(T value, OutcomeHandler<S> outcomeHandler);

    <S> S update(T value, OutcomeHandler<S> outcomeHandler);

    <S> S delete(long id, OutcomeHandler<S> outcomeHandler);

    interface OutcomeHandler<S> {
        S onAbsentValue(String messsage);

        S onPresentValue(String messsage);

        S onSuccess();
    }
}
