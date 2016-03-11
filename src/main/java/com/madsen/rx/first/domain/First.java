package com.madsen.rx.first.domain;

import com.madsen.rx.first.data.FirstDto;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface First {

    boolean isMatch(FirstDto dto);
    boolean isMatch(String name);

    int index();
}
