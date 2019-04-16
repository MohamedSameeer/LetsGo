package com.example.letsgo.selectCategoryPack;

import android.content.Context;
import android.content.Intent;

import com.example.letsgo.categoryOfPlacePack.categoryOfPlace;

public class SelecetCategoryPresenter {

        Context context;
    SelecetCategoryPresenter(Context context)
    {

        this.context=context;

    }



    void sendUserToSelectedCategory(String category){

        Intent i = new Intent(context, categoryOfPlace.class);
        i.putExtra("category",category);
        context.startActivity(i);

    }



}
