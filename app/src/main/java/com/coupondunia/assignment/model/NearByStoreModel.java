package com.coupondunia.assignment.model;

import android.location.Location;

import com.coupondunia.assignment.parsers.Category;
import com.coupondunia.assignment.parsers.ContentParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Aabid Mulani
 * {05-10-2015}
 */
public class NearByStoreModel {

    private String categoryString;
    private String couponCountMsg;
    private String latitude;
    private String longitude;
    private String brandName;
    private Integer numCoupons;
    private String cityName;
    private List<String> categoriesList = new ArrayList<>();
    private String logoURL;
    private String coverURL;
    private double computeDistanceFromCurrentLocation;
    private String computeDistanceFromCurrentLocationString;

    public NearByStoreModel(ContentParser contentParser) {
        this.latitude = contentParser.Latitude;
        this.longitude = contentParser.Longitude;
        this.brandName = contentParser.BrandName;
        this.numCoupons = contentParser.NumCoupons;

        // compute the offers counts
        this.couponCountMsg = "No Offers";
        if (this.numCoupons != null) {
            if ((int) numCoupons > 0) {
                couponCountMsg = contentParser.NumCoupons + " Offers";
            }
        }

        this.cityName = contentParser.CityName;

        // immediately generate the string to be displayed on the list item for categories
        if (contentParser.Categories != null) {
            categoryString = "<p>";
            for (Category category : contentParser.Categories) {
                this.categoriesList.add(category.Name);
                categoryString += " \u2022 " + category.Name;
            }
            categoryString += "</p>";
        }

        this.logoURL = contentParser.LogoURL;
        this.coverURL = contentParser.CoverURL;
    }


    // compute the distance of the store from provided location in the paramater
    public void setComputeDistanceFromCurrentLocation(Location currentLocation) {
        Location storeLocation = new Location("storeLocation");
        storeLocation.setLatitude(Double.parseDouble(latitude));
        storeLocation.setLongitude(Double.parseDouble(longitude));

        this.computeDistanceFromCurrentLocation = storeLocation.distanceTo(currentLocation);
    }

    public double getComputeDistanceFromCurrentLocation() {
        return computeDistanceFromCurrentLocation;
    }


    // get formatted distance i.e. In meters of KiloMeters
    public String getComputeDistanceFromCurrentLocationString() {
        if (computeDistanceFromCurrentLocation > 1000) {
            double valueInMeter = computeDistanceFromCurrentLocation / 1000;
            return String.format("%.2f KM", valueInMeter);
        } else {
            return computeDistanceFromCurrentLocation + " M";
        }
    }

    public String getBrandName() {
        return brandName;
    }

    public String getCouponCountMsg() {
        return couponCountMsg;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public String getLogoURL() {
        return logoURL;
    }
}
