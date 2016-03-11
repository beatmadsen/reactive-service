package com.madsen.rx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class X {

    @RequestMapping("/process-non-blocking")
    public DeferredResult<Integer> nonBlockingProcessing(
            @RequestParam(value = "minMs", required = false, defaultValue = "0") int minMs,
            @RequestParam(value = "maxMs", required = false, defaultValue = "0") int maxMs
    ) {

        // Initiate the processing in another thread
        final DeferredResult<Integer> deferredResult = new DeferredResult<>();

        deferredResult.setResult(minMs + maxMs);

        // Return to let go of the precious thread we are holding on to...
        return deferredResult;
    }
}