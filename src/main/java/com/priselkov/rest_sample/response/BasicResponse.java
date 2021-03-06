package com.priselkov.rest_sample.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({
        "success",
        "errors"
})
public class BasicResponse implements Serializable {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("errors")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public BasicResponse() {
    }

    public BasicResponse(Boolean success, List<String> errors) {
        this.success = success;
        this.errors = errors;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<String> getErrors() {
        return errors;
    }
}
