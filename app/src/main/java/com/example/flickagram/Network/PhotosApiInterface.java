package com.example.flickagram.Network;

import com.example.flickagram.Model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotosApiInterface {
    @GET("rest/?api_key=01de15108d082cf05bcb4b4e87b9f648&format=json&nojsoncallback=1")
    Call<Root> getMethodData(@Query("method") String method,
                             @Query("user_id") String userId,
                             @Query("text") String text);


}
