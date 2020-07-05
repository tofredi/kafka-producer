package com.fredo.martinez.kafkaproducer.model;

import io.swagger.annotations.ApiModelProperty;

public class SampleEvent {

    @ApiModelProperty(name = "id", example = "10")
    private String id;

    @ApiModelProperty(name = "name", example = "event-name")
    private String name;

    @ApiModelProperty(name = "description", example = "description of event sample")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
