package com.madsen.rx.third.domain;

import com.madsen.rx.third.data.ThirdDto;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by erikmadsen on 17/03/2016.
 */
public interface Third {

    <T> Optional<T> extract(Function<ThirdVo, Optional<T>> strategy);

    class ThirdVo {

        public final long id;
        public final int age;
        public final double risk;
        public final String name;


        public ThirdVo(final long id, final int age, final double risk, final String name) {

            this.id = id;

            this.age = age;
            this.risk = risk;
            this.name = name;
        }
    }
}
