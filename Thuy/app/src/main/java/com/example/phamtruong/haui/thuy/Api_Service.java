package com.example.phamtruong.haui.thuy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api_Service {


    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm::ss").create();

    Api_Service apiSever = new Retrofit.Builder()
            .baseUrl(Data.link)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Api_Service.class);

    @Multipart
    @POST("upload")
    Call<Ans> post_img(
            @Part MultipartBody.Part image
    ) ;
}
