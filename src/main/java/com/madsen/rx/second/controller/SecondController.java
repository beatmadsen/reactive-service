package com.madsen.rx.second.controller;

import com.madsen.rx.CrudService;
import com.madsen.rx.second.data.SecondDto;
import com.madsen.rx.second.domain.Second;
import com.madsen.rx.second.domain.SecondImpl;
import com.madsen.rx.second.service.SecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponentsBuilder;

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


    @RequestMapping(value = "/second/", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<Void>> postNew(
            @RequestBody final SecondDto dto,
            final UriComponentsBuilder builder
    ) {

        final DeferredResult<ResponseEntity<Void>> result = new DeferredResult<>();
        executorService.submit(() -> {
            final Second s = new SecondImpl(new Second.SecondVo(-1, dto.name, dto.address));
            final ResponseEntity<Void> entity = service.create(s, new CrudService.OutcomeHandler<ResponseEntity<Void>, Second>() {
                @Override
                public ResponseEntity<Void> onAbsentValue(final Second value, final String messsage) {

                    return null;
                }


                @Override
                public ResponseEntity<Void> onPresentValue(final Second value, final String messsage) {

                    return null;
                }


                @Override
                public ResponseEntity<Void> onSuccess(final Second value) {

                    long id = value.extract(vo -> Optional.of(vo.id)).get();

                    final HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(builder.path("/second/{id}").buildAndExpand(id).toUri());
                    return new ResponseEntity<>(headers, HttpStatus.CREATED);
                }
            });
            result.setResult(entity);
        });

        return result;
    }


    @RequestMapping(value = "/second/{id}", method = RequestMethod.PATCH)
    public DeferredResult<ResponseEntity<Void>> patch(
            @PathVariable final long id, @RequestBody final SecondDto dto
    ) {

        final DeferredResult<ResponseEntity<Void>> result = new DeferredResult<>();
        executorService.submit(() -> {
            final Second s = new SecondImpl(new Second.SecondVo(id, dto.name, dto.address));
            final ResponseEntity<Void> entity = service.update(s, new UpdateOutcomeHandler());
            result.setResult(entity);
        });

        return result;
    }


    @RequestMapping(value = "/second/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Void>> delete(@PathVariable final long id) {

        final DeferredResult<ResponseEntity<Void>> result = new DeferredResult<>();
        executorService.submit(() -> {
            final ResponseEntity<Void> entity = service.delete(id, new UpdateOutcomeHandler() {

            });
            result.setResult(entity);
        });

        return result;
    }


    private class UpdateOutcomeHandler implements CrudService.OutcomeHandler<ResponseEntity<Void>, Second> {

        @Override
        public ResponseEntity<Void> onAbsentValue(final Second value, final String messsage) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        @Override
        public ResponseEntity<Void> onPresentValue(final Second value, final String messsage) {

            return null;
        }


        @Override
        public ResponseEntity<Void> onSuccess(final Second value) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
