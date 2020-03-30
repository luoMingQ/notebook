package com.lsc.notebook.entity;

import java.io.Serializable;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 14:59 2020/3/29
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private String username;
    private String password;
    private String name;
    private String salt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
