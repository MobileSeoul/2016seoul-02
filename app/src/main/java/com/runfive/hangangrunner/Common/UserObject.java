package com.runfive.hangangrunner.Common;

import java.io.Serializable;

/**
 * Created by JunHo on 2016-09-03.
 */
public class UserObject implements Serializable{
    private String user_id = null;
    private String email = null;
    private String name = null;
    private String gender = null;
    private String birth = null;

    public UserObject(String user_id, String email, String name, String gender, String birth) {
        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
