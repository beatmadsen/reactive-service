package com.madsen.rx.first.domain;

import com.madsen.rx.first.data.FirstDto;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public class FirstImpl implements First {

    private final FirstDto state;


    public FirstImpl(final FirstDto state) {
        this.state = state;
    }


    @Override
    public boolean isMatch(final FirstDto dto) {
        return state.equals(dto);
    }


    @Override
    public boolean isMatch(final String name) {
        return state.name.equals(name);
    }
}
