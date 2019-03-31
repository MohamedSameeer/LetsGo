package com.example.letsgo.Reviews;

import android.content.Context;

public interface IPresenter {


    void getReviewAndupLoadReViewToFirebase(Context context, String review,String city,String category,String name);
    void intializeFields();



}
