package com.example.devansh.indoreinfoline;

/**
 * Created by Devansh on 15-08-2017.
 */

public class Restaurant {
    private String name,url,thumb,image,address,lat,lon;

    public Restaurant(){}




    public Restaurant(String name, String url, String thumb, String image, String address, String lat, String lon) {
        this.name = name;
        this.url = url;
        this.thumb = thumb;
        this.image=image;

        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {return image;}

    public void setImage(String image) { this.image = image;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
