package com.madsen.keep.controller;

import com.madsen.keep.service.KeepService;
import com.madsen.keep.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by erikmadsen on 20/03/2016.
 */
@RestController
public class KeepController {

    private final KeepService keepService;
    private final SecurityService securityService;


    @Autowired
    public KeepController(final KeepService keepService, final SecurityService securityService) {

        this.keepService = keepService;

        this.securityService = securityService;
    }
}
