package com.madsen.rx.first.domain;

import com.madsen.rx.first.data.FirstDto;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface First {

    boolean isMatch(FirstDto dto);

    boolean isMatch(String name);

    boolean isMatch(long id);

    <T> Optional<T> extract(Function<FirstDto, Optional<T>> strategy);
}
