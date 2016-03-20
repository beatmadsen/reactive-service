package com.madsen.keep;

/**
 * Created by erikmadsen on 20/03/2016.
 */
public interface OutcomeHandler<S, T> {

    T onSuccess(String message);

    T onSuccess(S value, String message);

    T onFailure(String message);

    T onFailure(S value, String message);
}
