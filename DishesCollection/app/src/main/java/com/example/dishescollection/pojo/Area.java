package com.example.dishescollection.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("strArea")
    @Expose
    private String strArea;

    public Area() {
    }

    public Area(Ingredient ingredient) {
        this.strArea = ingredient.getStrIngredient();
    }

    public String getStrArea() {
        return strArea;
    }
}
