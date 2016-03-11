package com.madsen.rx.first.domain;

import com.madsen.rx.first.data.FirstDto;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public class FirstImpl implements First {

    private final FirstDto dto;


    public FirstImpl(final FirstDto dto) {
        this.dto = dto;
    }


    @Override
    public boolean isMatch(final FirstDto dto) {
        return this.dto.equals(dto);
    }


    @Override
    public boolean isMatch(final String name) {
        return dto.name.equals(name);
    }


    @Override
    public boolean isMatch(final long id) {
        return dto.id == id;
    }


    @Override
    public <T> Optional<T> extract(final Function<FirstDto, Optional<T>> strategy) {
        return strategy.apply(dto);
    }
}
