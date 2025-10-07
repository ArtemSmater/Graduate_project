package com.example.dishescollection.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailResponse {
    @SerializedName("meals")
    @Expose
    private List<DetailMeal> meals;

    public List<DetailMeal> getMeals() {
        return meals;
    }
}
