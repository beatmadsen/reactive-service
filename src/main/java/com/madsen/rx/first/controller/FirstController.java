package com.madsen.rx.first.controller;

import com.madsen.rx.CrudService;
import com.madsen.rx.first.data.FirstDto;
import com.madsen.rx.first.domain.First;
import com.madsen.rx.first.domain.FirstImpl;
import com.madsen.rx.first.service.FirstService;
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
import java.util.stream.Collectors;

@RestController
public class FirstController {

    private final FirstService service;


    @Autowired
    public FirstController(final FirstService service) {

        this.service = service;
    }


    @RequestMapping("/process-non-blocking")
    public DeferredResult<Integer> nonBlockingProcessing(
            @RequestParam(value = "minMs", required = false, defaultValue = "0") final int minMs,
            @RequestParam(value = "maxMs", required = false, defaultValue = "0") final int maxMs
    ) {

        // Initiate the processing in another thread
        final DeferredResult<Integer> deferredResult = new DeferredResult<>();

        deferredResult.setResult(minMs + maxMs);

        // Return to let go of the precious thread we are holding on to...
        return deferredResult;
    }


    @RequestMapping(value = "/first/", method = RequestMethod.GET)
    public DeferredResult<Collection<FirstDto>> getAll() {

        final DeferredResult<Collection<FirstDto>> result = new DeferredResult<>();

        // TODO: thread pool
        new Thread(() -> {

            final Set<FirstDto> dtos = service.readAll().stream().map(value -> {
                final Optional<FirstDto> mDto = value.extract(Optional::of);
                return mDto.get();
            }).collect(Collectors.toSet());

            result.setResult(dtos);
        }).start();

        return result;
    }


    @RequestMapping(value = "/first/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<FirstDto>> getSingle(@PathVariable("id") final long id) {

        final DeferredResult<ResponseEntity<FirstDto>> result = new DeferredResult<>();

        // TODO: thread pool
        new Thread(() -> {

            final ResponseEntity<FirstDto> dto = service.read(id)
                    .flatMap(value -> value.extract(Optional::of))
                    .map(dto1 -> new ResponseEntity<>(dto1, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            result.setResult(dto);
        }).start();

        return result;
    }


    @RequestMapping(value = "/first/", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<Void>> postNew(
            @RequestBody final FirstDto dto, final UriComponentsBuilder builder
    ) {

        final DeferredResult<ResponseEntity<Void>> result = new DeferredResult<>();

        final CrudService.OutcomeHandler<ResponseEntity<Void>, First> outcomeHandler =
                new CrudService.OutcomeHandler<ResponseEntity<Void>, First>() {

                    @Override
                    public ResponseEntity<Void> onAbsentValue(final First value, final String messsage) {

                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }


                    @Override
                    public ResponseEntity<Void> onPresentValue(final First value, final String messsage) {

                        return null;
                    }


                    @Override
                    public ResponseEntity<Void> onSuccess(final First value) {

                        final HttpHeaders headers = new HttpHeaders();
                        headers.setLocation(builder.path("/user/{id}").buildAndExpand(dto.id).toUri());

                        return new ResponseEntity<>(headers, HttpStatus.CREATED);
                    }
                };

        // TODO: thread pool
        new Thread(() -> {
            final ResponseEntity<Void> responseEntity = service.create(new FirstImpl(dto), outcomeHandler);

            result.setResult(responseEntity);
        }).start();

        return result;
    }


    @RequestMapping(value = "/first/{id}", method = RequestMethod.PATCH)
    public DeferredResult<ResponseEntity<Void>> patch(
            @PathVariable final long id, @RequestBody final FirstDto dto
    ) {

        final DeferredResult<ResponseEntity<Void>> result = new DeferredResult<>();

        // TODO: thread pool
        new Thread(() -> {

            if (id != dto.id) {
                result.setResult(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
                return;
            }

            final ResponseEntity<Void> responseEntity = service.update(new FirstImpl(dto), new UpdateOutcomeHandler());
            result.setResult(responseEntity);
        }).start();

        return result;
    }


    @RequestMapping(value = "/first/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<Void>> delete(@PathVariable final long id) {

        final DeferredResult<ResponseEntity<Void>> result = new DeferredResult<>();

        // TODO: thread pool
        new Thread(() -> {

            final ResponseEntity<Void> responseEntity = service.delete(id, new UpdateOutcomeHandler());
            result.setResult(responseEntity);
        }).start();

        return result;
    }



    /**
     * An outcome handler that can be used for update and delete operations.
     * <p/>
     * Returns status 204: NO_CONTENT on success
     * and 404: NOT_FOUND, when nothing is found to update/delete.
     */
    private class UpdateOutcomeHandler implements CrudService.OutcomeHandler<ResponseEntity<Void>, First> {

        @Override
        public ResponseEntity<Void> onAbsentValue(final First value, final String messsage) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        @Override
        public ResponseEntity<Void> onPresentValue(final First value, final String messsage) {

            return null;
        }


        @Override
        public ResponseEntity<Void> onSuccess(final First value) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}