package com.madsen.rx.third.controller;

import com.madsen.rx.ReactiveCrudService;
import com.madsen.rx.third.data.ThirdDto;
import com.madsen.rx.third.domain.Third;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by erikmadsen on 16/03/2016.
 */
@RestController
public class ThirdController {

    private final ReactiveCrudService<Third> service; // TODO: sub-interface to ease auto-wiring


    @Autowired
    public ThirdController(final ReactiveCrudService<Third> service) {

        this.service = service;
    }


    @RequestMapping(value = "/third/", method = RequestMethod.GET)
    public DeferredResult<Collection<ThirdDto>> getAll() {

        final DeferredResult<Collection<ThirdDto>> result = new DeferredResult<>();

        service.readAll().subscribeOn(Schedulers.io()).subscribe(new Subscriber<Third>() {
            final Set<ThirdDto> set = new HashSet<>();

            @Override
            public void onCompleted() {

                result.setResult(set);
            }


            @Override
            public void onError(final Throwable e) {

                result.setErrorResult(e);
            }


            @Override
            public void onNext(final Third third) {

                third.extract(vo -> Optional.of(new ThirdDto(vo.age, vo.risk, vo.name))).map(set::add);
            }
        });

        return result;
    }
}
