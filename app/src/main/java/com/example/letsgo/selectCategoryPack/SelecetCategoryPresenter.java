package com.example.letsgo.selectCategoryPack;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.letsgo.categoryOfPlacePack.categoryOfPlace;

public class SelecetCategoryPresenter {

        Context context;
    SelecetCategoryPresenter(Context context)
    {

        this.context=context;

    }



    void sendUserToSelectedCategory(String category,String city){

        Intent i = new Intent(context, categoryOfPlace.class);

        i.putExtra("city",city);

        i.putExtra("category",category);
        Log.e(city+"seif bysb7 ",category+" seif bymsy");
        context.startActivity(i);

    }



}
