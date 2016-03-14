package com.madsen.rx.second.repository;

import com.madsen.rx.Repository;
import com.madsen.rx.second.domain.Second;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by erikmadsen on 13/03/2016.
 */
public interface SecondRepository extends Repository<Second> {

    // TODO: will the repository have access ot the ID service,
    // or will id be injected as parameter to add method?

    Collection<Second> all();

    Optional<Second> findBy(long id);
}
