package com.techelevator.tenmo.model;

public class UsernameDTO {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UsernameDTO(String username) {
        this.username = username;
    }

    public UsernameDTO(){

    }

    @Override
    public String toString() {
        return "Username{" +
                "username='" + username + '\'' +
                '}';
    }
}
