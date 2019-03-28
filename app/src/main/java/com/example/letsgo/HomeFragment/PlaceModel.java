package com.example.letsgo.HomeFragment;

public class PlaceModel {

  private Object img,name,description;

    public PlaceModel(Object img, Object name, Object description) {
        this.img = img;
        this.name = name;
        this.description = description;
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
