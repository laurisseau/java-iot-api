package com.laurisseau.iotapi.model;

public class User {
    private String email;
    private String userName;
    private String dataWebsite;
    private String dataLink;
    private String dataRole;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getDataWebsite() {
        return dataWebsite;
    }

    public void setDataWebsite(String dataWebsite) {
        this.dataWebsite = dataWebsite;
    }

    public String getDataLink() {
        return dataLink;
    }

    public void setDataLink(String dataLink) {
        this.dataLink = dataLink;
    }

    public String getDataRole() {
        return dataRole;
    }

    public void setDataRole(String dataRole) {
        this.dataRole = dataRole;
    }
}
