package com.madsen.keep.service;

import com.madsen.keep.data.ClaimsVo;
import rx.Observable;

/**
 * Created by erikmadsen on 20/03/2016.
 */
public interface SecurityService {

    Observable<ClaimsVo> claims(String token);
}
