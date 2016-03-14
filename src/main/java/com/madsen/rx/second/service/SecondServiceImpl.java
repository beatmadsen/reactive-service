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

        return repository.all();
    }


    @Override
    public Optional<Second> read(final long id) {

        return repository.find(id);
    }


    @Override
    public <S> S create(final Second value, final OutcomeHandler<S> outcomeHandler) {

        final Second updated = value.copyWithId(idService.nextAvailableId());

        final boolean success = repository.addIfAbsent(updated);
        if (!success) {
            /*
             We do not follow normal failure procedure here because
             this scenario should not occur when we have acquired a new id.
             */
            throw new RuntimeException("An unexpected ID collision occurred");
        }
        return outcomeHandler.onSuccess();
    }


    @Override
    public <S> S update(final Second value, final OutcomeHandler<S> outcomeHandler) {

        final boolean success = repository.updateIfPresent(value);
        if (success){
            return outcomeHandler.onSuccess();
        }
        return outcomeHandler.onAbsentValue("Did not find value to update in store");
    }


    @Override
    public <S> S delete(final long id, final OutcomeHandler<S> outcomeHandler) {

        final boolean success = repository.removeIfPresent(id);
        if (success){
            return outcomeHandler.onSuccess();
        }
        return outcomeHandler.onAbsentValue("Nothing to delete!");
    }
}
