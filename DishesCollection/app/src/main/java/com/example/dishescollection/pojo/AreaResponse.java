package com.example.dishescollection.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    @Expose
    private List<Area> meals;

    public List<Area> getMeals() {
        return meals;
    }

    public void setMeals(List<Area> meals) {
        this.meals = meals;
    }
}
