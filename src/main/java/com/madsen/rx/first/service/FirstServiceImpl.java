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
    public void create(final First value, final ErrorHandler errorHandler) {

        // TODO: Atomic
        if (repository.contains(value)) {
            errorHandler.onPresentValue("Value already exists");
        } else {
            repository.add(value);
        }
    }


    @Override
    public void update(final First value, final ErrorHandler errorHandler) {

        // TODO: Atomic
        if (!repository.contains(value)) {
            errorHandler.onAbsentValue("No existing value to update");
        } else {
            repository.update(value);
        }
    }


    @Override
    public void delete(final long id, final ErrorHandler errorHandler) {

        // TODO: Atomic
        repository.findBy(id).map(value -> {
            repository.remove(value);
            return null;
        }).orElseGet(() -> {
            errorHandler.onAbsentValue("No existing value to remove");
            return null;
        });
    }
}
