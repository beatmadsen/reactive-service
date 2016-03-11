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


    @Override
    public Collection<First> findAll() {
        return repository.all();
    }


    @Override
    public Optional<First> find(final long id) {
        return repository.query(first -> first.isMatch(id)).stream().findFirst();
    }


    @Override
    public void save(final First value) {

        // TODO: atomic
        if (repository.contains(value)) {
            repository.update(value);
        } else {
            repository.add(value);
        }
    }


    @Override
    public void delete(final First value) {

        repository.remove(value);
    }
}
