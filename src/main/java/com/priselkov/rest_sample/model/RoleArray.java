package com.priselkov.rest_sample.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleArray {

    @JsonProperty("roles")
    private String[] roles;

    public RoleArray() {
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
