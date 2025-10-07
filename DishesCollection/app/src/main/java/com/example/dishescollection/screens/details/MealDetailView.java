package com.example.dishescollection.screens.details;

import com.example.dishescollection.pojo.DetailMeal;

public interface MealDetailView {
    void showData(DetailMeal detailMeal);

    void showError();
}
