package com.madsen.rx;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by erikmadsen on 14/03/2016.
 */
public interface RestRepository<T> {

    boolean addIfAbsent(T value);

    boolean updateIfPresent(T value);

    boolean removeIfPresent(T value);

    Optional<T> find(long id);

    Collection<T> all();
}
