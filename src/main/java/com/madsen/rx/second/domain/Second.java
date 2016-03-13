package com.madsen.rx.second.domain;

import com.madsen.rx.second.data.SecondDto;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface Second {

    boolean dtoMatches(SecondDto dto);

    boolean nameMatches(String name);

    boolean addressMatches(String address);

    <T> Optional<T> extract(Function<SecondDto, Optional<T>> strategy);
}
