package com.coupondunia.assignment.interfaces;

import com.coupondunia.assignment.parsers.ContentParser;
import com.coupondunia.assignment.parsers.MainResponseParser;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Aabid Mulani
 * {04-10-2015}
 */
public interface RetrofitAdapterInterface {

    @GET("/task_data.txt")
    void makeTaskDataCall(Callback<MainResponseParser> callback);

}
