package com.madsen.rx.second.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by erikmadsen on 13/03/2016.
 */
@Service
public class InMemoryIdService implements IdService {

    private final AtomicLong current = new AtomicLong(1);

    @Override
    public long nextAvailableId() {

        return current.getAndIncrement();
    }
}
