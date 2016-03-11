package com.madsen.rx;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface Repository<T> {

    void update(T value);

    void add(T value);

    void remove(T value);

    Collection<T> query(Predicate<T> predicate);
}
