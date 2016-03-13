package com.madsen.rx.second.service;

import com.madsen.rx.second.domain.Second;
import com.madsen.rx.second.repository.SecondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by erikmadsen on 13/03/2016.
 */
@Service
public class SecondServiceImpl implements SecondService {

    private final IdService idService;
    private final SecondRepository repository;

    @Autowired
    public SecondServiceImpl(final IdService idService, final SecondRepository repository) {

        this.idService = idService;
        this.repository = repository;
    }


    @Override
    public Collection<Second> readAll() {

        return null;
    }


    @Override
    public Optional<Second> read(final long id) {

        return null;
    }


    @Override
    public <S> S create(final Second value, final OutcomeHandler<S> outcomeHandler) {

        return null;
    }


    @Override
    public <S> S update(final Second value, final OutcomeHandler<S> outcomeHandler) {

        return null;
    }


    @Override
    public <S> S delete(final long id, final OutcomeHandler<S> outcomeHandler) {

        return null;
    }
}
