package com.madsen.rx;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface CrudService<T> {

    Collection<T> findAll();

    Optional<T> find(long id);

    void save(T value);

    void delete(T value);
}
