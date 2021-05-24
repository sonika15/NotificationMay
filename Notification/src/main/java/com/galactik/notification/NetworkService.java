package com.galactik.notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetworkService {
    @Headers(
            "id:transasia"
    )
    @POST("open/push/subscribe")
    Call<ApiResponse> savePost(@Body DeviceSubscribe subscribe);


}
