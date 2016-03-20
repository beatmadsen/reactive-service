package com.madsen.keep.service;

import com.madsen.keep.OutcomeHandler;
import com.madsen.keep.data.DocVo;
import rx.Observable;

import java.util.UUID;

/**
 * Created by erikmadsen on 20/03/2016.
 */
public interface DocsService {

    <T> Observable<T> create(DocVo docVo, DOutcomeHandler<T> outcomeHandler);

    <T> Observable<T> read(UUID id, DOutcomeHandler<T> outcomeHandler);

    <T> Observable<T> update(DocVo docVo, DOutcomeHandler<T> outcomeHandler);

    <T> Observable<T> delete(UUID id, DOutcomeHandler<T> outcomeHandler);

    interface DOutcomeHandler<T> extends OutcomeHandler<DocVo, T> {

    }
}
