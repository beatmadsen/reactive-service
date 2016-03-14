package com.madsen.rx.second.repository;

import com.madsen.rx.second.domain.Second;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

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


    private final Map<Long, Second> map = new ConcurrentHashMap<>();


    @Override
    public Collection<Second> all() {
        return map.values();
    }


    @Override
    public Optional<Second> findBy(final long id) {
        return Optional.ofNullable(map.get(id));
    }


    @Override
    public void update(final Second value) {
        insert(value);
    }


    private void insert(final Second value) {
        extractId(value).map(key -> map.put(key, value));
    }


    private Optional<Long> extractId(final Second value) {
        return value.extract(secondVo -> Optional.of(secondVo.id));
    }


    @Override
    public void add(final Second value) {
        insert(value);
    }


    @Override
    public void remove(final Second value) {
        extractId(value).map(map::remove);
    }


    @Override
    public Collection<Second> query(final Predicate<Second> predicate) {
        return null;
    }
}
