package com.example.cookHelper.client.rest;

import com.example.cookHelper.client.rest.DTOs.PostRequest;
import com.example.cookHelper.client.rest.DTOs.PostResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
//    @GET("/bins/ovdb8")
    @POST("/spring-test/cookHelper/recipeList")
    public Call<PostResponse> getData(@Body PostRequest Data);


    public interface JSONtest {
        @POST("/alaram")
        public Call<PostResponse> getData(@Body PostRequest Data);
    }
}

