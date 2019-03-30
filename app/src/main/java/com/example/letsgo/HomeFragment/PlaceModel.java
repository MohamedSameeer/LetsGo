package com.example.letsgo.HomeFragment;

public class PlaceModel {

  private Object img,name,description,durationFrom,durationTo,category,city,address,price;

    public PlaceModel(Object img, Object name, Object description) {
        this.img = img;
        this.name = name;
        this.description = description;
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

    public PlaceModel(Object img, Object name, Object description, Object durationFrom, Object durationTo, Object category, Object city, Object address, Object price) {
        this.img = img;
        this.name = name;
        this.description = description;
        this.durationFrom = durationFrom;
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
