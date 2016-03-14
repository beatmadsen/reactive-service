package com.madsen.rx.second.repository;

import com.madsen.rx.second.domain.Second;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by erikmadsen2 on 14/03/2016.
 */
@Repository
public class InMemorySecondRepository implements SecondRepository {

    // TODO:
    /*
    It's nice with a decoupling between database and service
    but the atomicity and the error handling needs to be supported at this level

     */


    private final ConcurrentHashMap<Long, Second> map = new ConcurrentHashMap<>();


    @Override
    public boolean addIfAbsent(final Second value) {

        final Long key = extractId(value);
        return map.putIfAbsent(key, value) == null;
    }


    private Long extractId(final Second value) {

        return value.extract(secondVo -> Optional.of(secondVo.id))
                .orElseThrow(() -> new RuntimeException("Expected an id available"));
    }


    @Override
    public boolean updateIfPresent(final Second value) {

        final Long key = extractId(value);
        return map.replace(key, value) != null;
    }


    @Override
    public boolean removeIfPresent(final Second value) {

        final Long key = extractId(value);
        return map.remove(key) != null;
    }


    @Override
    public Optional<Second> find(final long id) {

        return Optional.ofNullable(map.get(id));
    }


    @Override
    public Collection<Second> all() {

        return map.values();
    }
}
