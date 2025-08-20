package com.wolfcode.domain;

import lombok.Data;

@Data  //提供类的get、set、equals、hashCode、canEqual、toString方法
public class User {
    private String userid;
    private String username;
    private String password;
    private String email;
    private String userpicture;

    public String getUserId() { return userid; }
    public void setUserId(String userId) { this.userid = String.valueOf(userId); }

}
