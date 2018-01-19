package com.example.rakesh.eatvm;

/**
 * Created by rakesh on 31/12/17.
 */

public class Hotel {
    private String HotelName;
    private String ImageUrl;
    private String Description;
    private String Latitude;
    private String Longitude;


    public Hotel() {

    }

    public Hotel(String hotelName, String imageUrl, String description, String latitude, String longitude) {
        HotelName = hotelName;
        ImageUrl = imageUrl;
        Description = description;
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getHotelName() {

        return HotelName;
    }

    public void setHotelName(String HotelName) {

        this.HotelName = HotelName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {

        this.ImageUrl = ImageUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {

        this.Description = Description;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {

        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {

        this.Longitude = Longitude;
    }
}
