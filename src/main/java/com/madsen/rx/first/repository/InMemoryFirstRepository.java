package com.madsen.rx.first.repository;

import com.madsen.rx.first.domain.First;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */

@Component
public class InMemoryFirstRepository implements FirstRepository {

    private final Map<Long, First> map = new ConcurrentHashMap<>();


    @Override
    public Collection<First> all() {
        return map.values();
    }


    @Override
    public Collection<First> findBy(final String name) {
        return query(first -> first.isMatch(name));
    }


    @Override
    public Optional<First> findBy(final long id) {
        return Optional.ofNullable(map.get(id));
    }


    @Override
    public boolean contains(final First value) {
        final Optional<Long> maybeKey = index(value);

        return maybeKey.map(map::containsKey).orElse(false);
    }


    private static Optional<Long> index(final First value) {
        return value.extract(state -> Optional.of(state.id));
    }


    @Override
    public void update(final First value) {

        index(value).ifPresent(index -> map.put(index, value));
    }


    @Override
    public void add(final First value) {

        index(value).ifPresent(index -> map.putIfAbsent(index, value));
    }


    @Override
    public void remove(final First value) {
        index(value).ifPresent(map::remove);
    }


    @Override
    public Collection<First> query(final Predicate<First> predicate) {
        return all().stream().filter(predicate).collect(Collectors.toSet());
    }
}
