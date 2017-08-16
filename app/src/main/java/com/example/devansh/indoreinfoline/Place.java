package com.example.devansh.indoreinfoline;

/**
 * Created by Devansh on 17-07-2017.
 */

public class Place {
    private String title,description,image,lat,lng;

    public Place(String title, String description, String image,String lat,String lng) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.lat=lat;
        this.lng=lng;
    }

    public Place() {
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
