package com.madsen.keep.service;

import com.madsen.keep.OutcomeHandler;
import com.madsen.keep.domain.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

/**
 * Created by erikmadsen on 20/03/2016.
 */
@Service
public class KeepServiceImpl implements KeepService {

    private final IdService idService;
    private final DocsService docsService;
    private final MetadataService metadataService;


    @Autowired
    public KeepServiceImpl(
            final IdService idService,
            final DocsService docsService,
            final MetadataService metadataService
    ) {

        this.idService = idService;
        this.docsService = docsService;
        this.metadataService = metadataService;
    }


    @Override
    public Observable<Void> upvote(final String externalId) {

        /* TODO:
        - Acquire internal id
        - Update metadata
         */

        return null;
    }


    @Override
    public <T> Observable<T> read(final String externalId, final OutcomeHandler<Document, T> outcomeHandler) {

        /* TODO
        - Acquire internal id
        - Load docVo
        - Load metadataVo
        - Create document instance
         */
        return null;
    }
}
