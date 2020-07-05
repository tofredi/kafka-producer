package com.fredo.martinez.kafkaproducer.controller;

import com.fredo.martinez.kafkaproducer.model.ClusterResponseMetadata;
import com.fredo.martinez.kafkaproducer.model.SampleEvent;
import com.fredo.martinez.kafkaproducer.service.ProducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Api(tags = "Kafka producer")
@RestController
@RequestMapping(value = "/v1")
public class ProducerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);

    private final ProducerService service;

    @Autowired
    public ProducerController(ProducerService service) {
        this.service = service;
    }

    @ApiOperation(value = "Send an event to Kafka cluster", response = ClusterResponseMetadata.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Message sent")
    })
    @PostMapping(value = "/sendSampleEvent/")
    public DeferredResult<ResponseEntity<ClusterResponseMetadata>> sendSampleEvent(
            @RequestBody SampleEvent sampleEvent
    ) {
        LOGGER.info("Sending sample text");
        return service.sendSampleEvent(sampleEvent);
    }


}
