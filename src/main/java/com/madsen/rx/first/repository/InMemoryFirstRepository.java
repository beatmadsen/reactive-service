package com.madsen.rx.first.repository;

import com.madsen.rx.first.domain.First;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */

@Component
public class InMemoryFirstRepository implements FirstRepository {

    private final Map<Integer, First> map = new ConcurrentHashMap<>();


    @Override
    public Collection<First> all() {
        return map.values();
    }


    @Override
    public Collection<First> findBy(final String name) {
        return all().stream().filter(first -> first.isMatch(name)).collect(Collectors.toSet());
    }


    @Override
    public void update(final First value) {
        final int index = value.index();
        map.put(index, value);
    }


    @Override
    public void add(final First value) {
        map.putIfAbsent(value.index(), value);
    }


    @Override
    public void remove(final First value) {
        map.remove(value.index());
    }


    @Override
    public Collection<First> query(final Predicate<First> predicate) {
        return all().stream().filter(predicate).collect(Collectors.toSet());
    }
}
