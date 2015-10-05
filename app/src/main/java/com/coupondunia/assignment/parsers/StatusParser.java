package com.coupondunia.assignment.parsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Aabid Mulani
 * {04-10-2015}
 */
public class StatusParser {
    @SerializedName("rcode")
    @Expose
    public Integer rcode;
    @SerializedName("message")
    @Expose
    public String message;
}
