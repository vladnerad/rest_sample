package com.priselkov.rest_sample.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({
        "success",
        "message"
})
public class BasicResponse implements Serializable {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    public BasicResponse() {
    }

    public BasicResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
