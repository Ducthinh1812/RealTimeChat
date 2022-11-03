package com.example.realtimechat.RetrofitApi;

import com.example.realtimechat.SendNotificotion.MyResponse;
import com.example.realtimechat.SendNotificotion.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAARpsI-y0:APA91bHrYPo3IaM3goRByz8e3Scy9grD8aSvom7P1yreHZ2o39kzbgrWxCm1ALKQIaz7Bknci-35x2NS1K76WAyyj5vZPglIjyqQn5EfTQ-HC-eqNTErUtKGwrDO3NlWXltofJ5kygW2"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
