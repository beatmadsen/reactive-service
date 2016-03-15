package com.madsen.rx.second.controller;

import com.madsen.rx.second.data.SecondDto;
import com.madsen.rx.second.domain.Second;
import com.madsen.rx.second.service.SecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by erikmadsen on 15/03/2016.
 */
@RestController
public class SecondController {

    private final SecondService service;

    private final ExecutorService executorService = Executors.newCachedThreadPool();


    @Autowired
    public SecondController(final SecondService service) {

        this.service = service;
    }


    @RequestMapping(value = "/second/", method = RequestMethod.GET)
    public DeferredResult<Collection<SecondDto>> getAll() {

        final DeferredResult<Collection<SecondDto>> result = new DeferredResult<>();

        final Function<Second.SecondVo, Optional<SecondDto>> extractor =
                vo -> Optional.of(new SecondDto(vo.name, vo.address));

        executorService.submit(() -> {
            final Set<SecondDto> dtos = service.readAll()
                    .stream()
                    .map(second -> second.extract(extractor))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            result.setResult(dtos);
        });

        return result;
    }


    @RequestMapping(value = "/second/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<SecondDto>> getSingle(@PathVariable("id") final long id) {

        final DeferredResult<ResponseEntity<SecondDto>> result = new DeferredResult<>();

        final Function<Second.SecondVo, Optional<SecondDto>> extractor =
                vo -> Optional.of(new SecondDto(vo.name, vo.address));

        executorService.submit(() -> {

            final ResponseEntity<SecondDto> entity = service.read(id)
                    .flatMap(value -> value.extract(extractor))
                    .map(dto1 -> new ResponseEntity<>(dto1, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            result.setResult(entity);
        });

        return result;
    }
}
