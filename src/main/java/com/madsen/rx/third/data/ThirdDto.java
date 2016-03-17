package com.madsen.rx.third.data;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by erikmadsen on 17/03/2016.
 */
public class ThirdDto {

    public final int age;
    public final double risk;
    public final String name;


    public ThirdDto(final int age, final double risk, final String name) {

        this.age = age;
        this.risk = risk;
        this.name = name;
    }
}
