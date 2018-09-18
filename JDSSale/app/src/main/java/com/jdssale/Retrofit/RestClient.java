package com.jdssale.Retrofit;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dikhong on 06-07-2018.
 */

public class RestClient {
    public static String BASE_URL = "http://jdskassa.nl/kassa/sales_and_driver_api/";
    public static String BASE_URL2 = "http://jdskassa.nl/kassa/api/";


    public static RestClient apiClient;
    private Retrofit retrofit = null;
    private Retrofit retrofit2=null;

    public static RestClient getInstance() {
        if (apiClient == null) {
            apiClient = new RestClient();
        }
        return apiClient;
    }

//private static Retrofit storeRetrofit = null;

    public Retrofit getClient() {
        return getClient(null);
    }

    public Retrofit getClient2() {
        return getClient2(null);
    }


    private Retrofit getClient(final Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(interceptor);
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }
    private Retrofit getClient2(final Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(interceptor);
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }
}
