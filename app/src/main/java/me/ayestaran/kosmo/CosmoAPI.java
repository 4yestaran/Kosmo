package me.ayestaran.kosmo;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface CosmoAPI {
    @POST("/api/v1/login")
    Call<User> login(@Body Credentials credentials);

    @Headers({"X-AUTH-TOKEN: kC0sNEYicPx+T479UVZqKFNZVxUYkiuzB24fzQK75TI=", "User-Agent: vince"})
    @GET("/api/v1/projects")
    Call<List<Project>> getProjects();
}