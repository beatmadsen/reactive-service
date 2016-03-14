package com.madsen.rx.second.domain;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by erikmadsen on 13/03/2016.
 */
public class SecondImpl implements Second {

    private final SecondVo vo;


    public SecondImpl(final SecondVo vo) {
        this.vo = vo;
    }


    @Override
    public boolean idMatches(final long id) {
        return vo.id == id;
    }


    @Override
    public boolean nameMatches(final String name) {

        return vo.name.equals(name);
    }


    @Override
    public boolean addressMatches(final String address) {

        return vo.address.equals(address);
    }


    @Override
    public <T> Optional<T> extract(final Function<SecondVo, Optional<T>> strategy) {
        return strategy.apply(vo);
    }


    @Override
    public int hashCode() {

        return vo.hashCode();
    }


    @Override
    public boolean equals(final Object obj) {

        return (obj instanceof Second) && equals((Second) obj);
    }


    private boolean equals(final Second second) {
        return second.extract(vo -> Optional.of(this.vo.equals(vo))).orElse(false);
    }
}
