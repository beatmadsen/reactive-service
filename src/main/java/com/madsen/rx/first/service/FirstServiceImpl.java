package com.madsen.rx.first.service;

import com.madsen.rx.first.domain.First;
import com.madsen.rx.first.repository.FirstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */

@Service
public class FirstServiceImpl implements FirstService {

    private final FirstRepository repository;


    @Autowired
    public FirstServiceImpl(final FirstRepository repository) {
        this.repository = repository;
    }

    //
    //    @Override
    //    public Collection<First> findAll() {
    //        return repository.all();
    //    }
    //
    //
    //    @Override
    //    public Optional<First> find(final long id) {
    //        return repository.query(first -> first.isMatch(id)).stream().findFirst();
    //    }
    //
    //
    //    @Override
    //    public void save(final First value) {
    //
    //        // TODO: atomic
    //        if (repository.contains(value)) {
    //            repository.update(value);
    //        } else {
    //            repository.add(value);
    //        }
    //    }
    //
    //
    //    @Override
    //    public void delete(final First value) {
    //
    //        repository.remove(value);
    //    }


    @Override
    public Collection<First> readAll() {
        return repository.all();
    }


    @Override
    public Optional<First> read(final long id) {
        return repository.findBy(id);
    }


    @Override
    public <S> S create(final First value, final OutcomeHandler<S> outcomeHandler) {

        // TODO: Atomic
        if (repository.contains(value)) {
            return outcomeHandler.onPresentValue("Value already exists");
        }

        repository.add(value);
        return outcomeHandler.onSuccess();
    }


    @Override
    public <S> S update(final First value, final OutcomeHandler<S> outcomeHandler) {

        // TODO: Atomic
        if (!repository.contains(value)) {
            return outcomeHandler.onAbsentValue("No existing value to update");
        }

        repository.update(value);
        return outcomeHandler.onSuccess();
    }


    @Override
    public <S> S delete(final long id, final OutcomeHandler<S> outcomeHandler) {

        // TODO: Atomic
        return repository.findBy(id).map(value -> {
            repository.remove(value);
            return outcomeHandler.onSuccess();
        }).orElseGet(() -> outcomeHandler.onAbsentValue("No existing value to remove"));
    }
}
