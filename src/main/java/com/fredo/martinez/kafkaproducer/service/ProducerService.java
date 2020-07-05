package com.fredo.martinez.kafkaproducer.service;

import com.fredo.martinez.kafkaproducer.model.ClusterResponseMetadata;
import com.fredo.martinez.kafkaproducer.model.SampleEvent;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class ProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

    private final Producer<String, SampleEvent> producer;

    @Value("${sample-topic}")
    private String TOPIC;

    @Autowired
    public ProducerService(Producer producer) {
        this.producer = producer;
    }

    public DeferredResult<ResponseEntity<ClusterResponseMetadata>> sendSampleEvent(SampleEvent event) {
        final DeferredResult<ResponseEntity<ClusterResponseMetadata>> result = new DeferredResult<>();

        producer.send(new ProducerRecord<>(TOPIC, event), (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                result.setErrorResult(exception);
            } else {
                LOGGER.info("Produced record to topic {} partition [{}] @ offset {}.",
                        metadata.topic(), metadata.partition(), metadata.offset());
                result.setResult(ResponseEntity.ok(new ClusterResponseMetadata(metadata)));
                producer.close();
            }
        });
        return result;
    }

}
