package com.madsen.keep.service;

import com.madsen.keep.OutcomeHandler;
import com.madsen.keep.domain.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

/**
 * Created by erikmadsen on 20/03/2016.
 */
public interface KeepService {

    Observable<Void> upvote(String externalId);

    <T> Observable<T> read(String externalId, OutcomeHandler<Document, T> outcomeHandler);
}
