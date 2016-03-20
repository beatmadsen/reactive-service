package com.madsen.keep.service;

import rx.Observable;

import java.util.UUID;

/**
 * Created by erikmadsen on 20/03/2016.
 */
public interface IdService {

    Observable<UUID> newInternalId();

    Observable<UUID> toInternal(String externalId);

    Observable<String> toExternal(UUID internalId);
}
