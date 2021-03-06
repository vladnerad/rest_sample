package com.priselkov.rest_sample.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@JsonFilter("userFilter")
@Entity
@Table(name = "users")
public class User {

    @Id
    @JsonProperty("login")
    private String login;
    @JsonProperty("pass")
    private String pass;
    @JsonProperty("name")
    private String name;
    @JsonProperty("roles")
    @ManyToMany(fetch = FetchType.EAGER, cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
//                    CascadeType.ALL
            }
    )
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "u_login", referencedColumnName = "login"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    public User() {
    }

    public User(String login, String pass, String name) {
        this.login = login;
        this.pass = pass;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
