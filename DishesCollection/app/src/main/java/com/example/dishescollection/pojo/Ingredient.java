package com.example.dishescollection.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("idIngredient")
    @Expose
    private String idIngredient;
    @SerializedName("strIngredient")
    @Expose
    private String strIngredient;
    @SerializedName("strDescription")
    @Expose
    private Object strDescription;
    @SerializedName("strType")
    @Expose
    private Object strType;

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public Object getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(Object strDescription) {
        this.strDescription = strDescription;
    }

    public Object getStrType() {
        return strType;
    }

    public void setStrType(Object strType) {
        this.strType = strType;
    }
}
