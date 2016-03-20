package com.madsen.keep.data;

import java.util.UUID;

/**
 * Created by erikmadsen on 20/03/2016.
 */
public class MetadataVo {

    public final UUID id;
    public final long expiryTime;


    public MetadataVo(final UUID id, final long expiryTime) {

        this.id = id;

        this.expiryTime = expiryTime;
    }
}
