package com.fredo.martinez.kafkaproducer.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ClusterResponseMetadata {

    @ApiModelProperty(name = "topic", example = "my-topic")
    private String topic;

    @ApiModelProperty(name = "partition", example = "1")
    private Integer partition;

    @ApiModelProperty(name = "offset", example = "10")
    private Long offset;

    public ClusterResponseMetadata(RecordMetadata recordMetadata) {
        this.topic = recordMetadata.topic();
        this.partition = recordMetadata.partition();
        this.offset = recordMetadata.offset();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
