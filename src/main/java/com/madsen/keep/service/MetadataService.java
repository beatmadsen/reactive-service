package com.madsen.keep.service;

import com.madsen.keep.OutcomeHandler;
import com.madsen.keep.data.MetadataVo;
import rx.Observable;

import java.util.UUID;

/**
 * Created by erikmadsen on 20/03/2016.
 */
public interface MetadataService {

    <T> Observable<T> read(UUID id, MOutcomeHandler<T> outcomeHandler);

    interface MOutcomeHandler<T> extends OutcomeHandler<MetadataVo, T> {

    }
}
