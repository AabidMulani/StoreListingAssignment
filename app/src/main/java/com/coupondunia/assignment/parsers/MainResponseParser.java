package com.coupondunia.assignment.parsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Aabid Mulani
 * {04-10-2015}
 */
public class MainResponseParser {

    @SerializedName("status")
    @Expose
    public StatusParser status;
    @SerializedName("data")
    @Expose
    public Map<String, ContentParser> data;
    @SerializedName("hash")
    @Expose
    public String hash;

}
