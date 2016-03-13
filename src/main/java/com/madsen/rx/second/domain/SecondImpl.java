package com.madsen.rx.second.domain;

import com.madsen.rx.second.data.SecondDto;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by erikmadsen on 13/03/2016.
 */
public class SecondImpl implements Second {

    private final SecondDto dto;


    public SecondImpl(final SecondDto dto) {

        this.dto = dto;
    }


    @Override
    public boolean dtoMatches(final SecondDto dto) {

        return this.dto.equals(dto);
    }


    @Override
    public boolean nameMatches(final String name) {

        return dto.name.equals(name);
    }


    @Override
    public boolean addressMatches(final String address) {

        return dto.address.equals(address);
    }


    @Override
    public <T> Optional<T> extract(final Function<SecondDto, Optional<T>> strategy) {

        return strategy.apply(dto);
    }


    @Override
    public int hashCode() {

        return dto.hashCode();
    }


    @Override
    public boolean equals(final Object obj) {

        return (obj instanceof Second) && ((Second) obj).dtoMatches(dto);
    }
}
