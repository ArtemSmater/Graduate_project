package com.example.dishescollection.screens.meals;

import com.example.dishescollection.pojo.Category;
import com.example.dishescollection.pojo.Meal;

import java.util.List;

public interface MealsListView {
    void showInfo(List<Meal> meals);
    void showError();
}
