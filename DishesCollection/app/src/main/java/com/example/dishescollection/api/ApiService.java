package com.example.dishescollection.api;

import com.example.dishescollection.pojo.AreaResponse;
import com.example.dishescollection.pojo.CategoryResponse;
import com.example.dishescollection.pojo.IngredientResponse;
import com.example.dishescollection.pojo.MealResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // parameters
    // categories
    @GET("categories.php")
    Observable<CategoryResponse> categories();

    // areas
    @GET("list.php?a=list")
    Observable<AreaResponse> areas();

    // ingredients
    @GET("list.php?i=list")
    Observable<IngredientResponse> ingredients();

    // dishes list
    // to get meals by category
    @GET("filter.php")
    Observable<MealResponse> mealsByCategory(@Query("c") String category);

    // to get meals by area
    @GET("filter.php")
    Observable<MealResponse> mealsByArea(@Query("a") String area);

    // to get meals by ingredient
    @GET("filter.php")
    Observable<MealResponse> mealsByIngredient(@Query("i") String ingredient);

    // to get meal detail
    @GET("lookup.php")
    Observable<String> mealDetailAsLine(@Query("i") int id);
}
