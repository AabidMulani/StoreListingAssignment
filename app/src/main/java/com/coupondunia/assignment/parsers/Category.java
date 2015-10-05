package com.coupondunia.assignment.parsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Aabid Mulani
 * {04-10-2015}
 */
public class Category {

    @SerializedName("OfflineCategoryID")
    @Expose
    public String OfflineCategoryID;
    @SerializedName("Name")
    @Expose
    public String Name;
    @SerializedName("ParentCategoryID")
    @Expose
    public Object ParentCategoryID;
    @SerializedName("CategoryType")
    @Expose
    public String CategoryType;

}