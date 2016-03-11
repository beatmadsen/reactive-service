package com.madsen.rx.first.repository;

import com.madsen.rx.Repository;
import com.madsen.rx.first.domain.First;

import java.util.Collection;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface FirstRepository extends Repository<First> {

    Collection<First> all();

    Collection<First> findBy(String name);
}
