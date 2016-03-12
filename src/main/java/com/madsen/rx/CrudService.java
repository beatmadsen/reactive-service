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

    void create(T value, ErrorHandler errorHandler);

    void update(T value, ErrorHandler errorHandler);

    void delete(T value, ErrorHandler errorHandler);

    interface ErrorHandler {
        void onAbsentValue(String messsage);

        void onPresentValue(String messsage);
    }
}
