package com.example.dishescollection.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    @Expose
    private List<Ingredient> meals;

    public List<Ingredient> getMeals() {
        return meals;
    }
}
