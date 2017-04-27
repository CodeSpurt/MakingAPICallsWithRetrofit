package com.codespurt.makingapicallswithretrofit.engine;

import com.codespurt.makingapicallswithretrofit.models.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiEndpointInterface {

    @GET("posts/{number_of_posts}")
    Call<Result> getDataFromGet(@Path("number_of_posts") String number_of_posts);

    @Headers({"Content-Type:application/json", "Application-Type:ANDROID"})
    @POST("YOUR_NON-BASE_POST_URL_HERE")
    Call<Result> getDataFromPost(@Body Result result);
}