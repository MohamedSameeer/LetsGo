package com.example.letsgo.CategoryOfPlacePack;

public class PlaceModel {

  private Object img,name,description,durationFrom,durationTo,category,city,address,price,lat,lng;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public PlaceModel(Object img, Object name, Object description) {
        this.img = img;
        this.name = name;
        this.description = description;
    }


    public double getLat() {
        Double lat=Double.parseDouble(this.lat+"");
        return lat;
    }

    public void setLat(Object lat) {
        this.lat = lat;
    }

    public double getLng() {
        Double lng=Double.parseDouble(this.lng+"");
        return lng;
    }

    public void setLng(Object lng) {
        this.lng = lng;
    }

    public PlaceModel(){


    }

    public void setName(Object name) {
        this.name = name;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public void setDurationFrom(Object durationFrom) {
        this.durationFrom = durationFrom;
    }

    public void setDurationTo(Object durationTo) {
        this.durationTo = durationTo;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getDurationFrom() {
        return durationFrom;
    }

    public Object getDurationTo() {
        return durationTo;
    }

    public Object getCategory() {
        return category;
    }

    public Object getCity() {
        return city;
    }

    public Object getAddress() {
        return address;
    }

    public Object getPrice() {
        return price;
    }

    public PlaceModel(Object img, Object name, Object description/*, Object durationFrom*/, Object durationTo, Object category, Object city, Object address, Object price) {
        this.img = img;
        this.name = name;
        this.description = description;
    //    this.durationFrom = durationFrom;
        this.durationTo = durationTo;
        this.category = category;
        this.city = city;
        this.address = address;
        this.price = price;
    }

    public Object getImg() {
        return img;
    }

    public Object getName() {
        return name;
    }

    public Object getDescription() {
        return description;
    }
}
