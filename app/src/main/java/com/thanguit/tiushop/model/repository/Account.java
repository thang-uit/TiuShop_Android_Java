package com.thanguit.tiushop.model.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("userID")
    @Expose
    private String userID;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("role")
    @Expose
    private String role;

    public Account() {
    }

    public Account(String userID, String username, String role) {
        this.userID = userID;
        this.username = username;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
