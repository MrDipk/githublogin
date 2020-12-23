package com.dip.rest;

import com.dip.model.GitHunUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserEndPoint {
    @GET("/users/{user}")
    Call<GitHunUser> getUser(@Path("user") String user);
}
