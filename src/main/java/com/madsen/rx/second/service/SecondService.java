package com.madsen.rx.second.service;

import com.madsen.rx.CrudService;
import com.madsen.rx.second.domain.Second;

/**
 * Created by erikmadsen on 13/03/2016.
 */
public interface SecondService extends CrudService<Second> {

    /**
     * Assign a new id and store domain object
     * @param value an instance of the domain object with a provisional ID
     * @param outcomeHandler strategy for handling possible outcomes
     * @param <S> type of outcome output
     * @return succesful or failed output from outcome handler
     */
    @Override
    <S> S create(Second value, OutcomeHandler<S> outcomeHandler);
}
