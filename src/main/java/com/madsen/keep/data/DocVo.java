package com.madsen.keep.data;

import java.util.UUID;

/**
 * Created by erikmadsen on 20/03/2016.
 */
public class DocVo {

    public final UUID id;
    public final String document;


    public DocVo(final UUID id, final String document) {

        this.id = id;

        this.document = document;
    }
}
