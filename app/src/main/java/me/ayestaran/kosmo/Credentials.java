package me.ayestaran.kosmo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vince on 4/11/15.
 */
public class Credentials {
    @SerializedName("username")
    String userName;
    String password;

    public Credentials(String name, String pass) {
        this.userName = name;
        this.password = pass;
    }
}
