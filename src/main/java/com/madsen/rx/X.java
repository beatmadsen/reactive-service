package com.madsen.rx;

/**
 * Created by erikmadsen2 on 14/03/2016.
 */
public class X {

    public interface Step<T> {

        T buildStep(T visitor);
    }

    public interface AbstractFactory<S, T> {

        S createFrom(Iterable<Step<T>> steps);
    }
}
