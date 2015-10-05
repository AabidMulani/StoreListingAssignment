package com.coupondunia.assignment.parsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Aabid Mulani
 * {04-10-2015}
 */
public class ContentParser {

    @SerializedName("SubFranchiseID")
    @Expose
    public String SubFranchiseID;
    @SerializedName("OutletID")
    @Expose
    public String OutletID;
    @SerializedName("OutletName")
    @Expose
    public String OutletName;
    @SerializedName("BrandID")
    @Expose
    public String BrandID;
    @SerializedName("Address")
    @Expose
    public String Address;
    @SerializedName("NeighbourhoodID")
    @Expose
    public String NeighbourhoodID;
    @SerializedName("CityID")
    @Expose
    public String CityID;
    @SerializedName("Email")
    @Expose
    public String Email;
    @SerializedName("Timings")
    @Expose
    public String Timings;
    @SerializedName("CityRank")
    @Expose
    public String CityRank;
    @SerializedName("Latitude")
    @Expose
    public String Latitude;
    @SerializedName("Longitude")
    @Expose
    public String Longitude;
    @SerializedName("Pincode")
    @Expose
    public String Pincode;
    @SerializedName("Landmark")
    @Expose
    public String Landmark;
    @SerializedName("Streetname")
    @Expose
    public String Streetname;
    @SerializedName("BrandName")
    @Expose
    public String BrandName;
    @SerializedName("OutletURL")
    @Expose
    public String OutletURL;
    @SerializedName("NumCoupons")
    @Expose
    public Integer NumCoupons;
    @SerializedName("NeighbourhoodName")
    @Expose
    public String NeighbourhoodName;
    @SerializedName("PhoneNumber")
    @Expose
    public String PhoneNumber;
    @SerializedName("CityName")
    @Expose
    public String CityName;
    @SerializedName("Distance")
    @Expose
    public Double Distance;
    @SerializedName("Categories")
    @Expose
    public List<Category> Categories = new ArrayList<>();
    @SerializedName("LogoURL")
    @Expose
    public String LogoURL;
    @SerializedName("CoverURL")
    @Expose
    public String CoverURL;


}
