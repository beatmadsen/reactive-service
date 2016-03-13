package com.madsen.rx.first.controller;

import com.madsen.rx.CrudService;
import com.madsen.rx.first.data.FirstDto;
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
    public DeferredResult<Collection<FirstDto>> listAll() {

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
    public DeferredResult<ResponseEntity<FirstDto>> getFirst(@PathVariable("id") final long id) {

        final DeferredResult<ResponseEntity<FirstDto>> result = new DeferredResult<>();

        // TODO: thread pool
        new Thread(() -> {

            final ResponseEntity<FirstDto>
                    dto =
                    service.read(id)
                            .flatMap(value -> value.extract(Optional::of))
                            .map(dto1 -> new ResponseEntity<>(dto1, HttpStatus.OK))
                            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            result.setResult(dto);
        }).start();

        return result;
    }


    @RequestMapping(value = "/first/", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<Void>> createNew(
            @RequestBody final FirstDto dto, final UriComponentsBuilder builder
    ) {

        final DeferredResult<ResponseEntity<Void>> result = new DeferredResult<>();

        // TODO: thread pool
        new Thread(() -> {

            service.create(new FirstImpl(dto), new CrudService.ErrorHandler() {
                @Override
                public void onAbsentValue(final String messsage) {

                }


                @Override
                public void onPresentValue(final String messsage) {
                    throw new CreateConflict(); // TODO: This doesn't work yet.
                }
            });

            final HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/user/{id}").buildAndExpand(dto.id).toUri());

            final ResponseEntity<Void> entity = new ResponseEntity<>(headers, HttpStatus.CREATED);

            result.setResult(entity);
        }).start();

        return result;
    }


    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
    public class CreateConflict extends RuntimeException {
    }

    /*

    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getName());

        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);

        User currentUser = userService.findById(id);

        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Users --------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
     */
}