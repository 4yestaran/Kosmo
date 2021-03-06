package me.ayestaran.kosmo;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface CosmoAPI {
    @POST("/api/v1/login")
    Call<User> login(@Body Credentials credentials);

    //@Headers({"X-AUTH-TOKEN:2yT7QFF+ZYRcRrMzlI8Mrewy0M+IZGQ7UUpwD2ETvUI=", "User-Agent: vince"})
    @GET("/api/v1/projects")
    Call<List<Project>> getProjects(@Header("X-AUTH-TOKEN") String token);
}