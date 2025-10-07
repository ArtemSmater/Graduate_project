package com.example.dishescollection.screens.fragments.fragments.ingredients;

import com.example.dishescollection.api.ApiFactory;
import com.example.dishescollection.api.ApiService;
import com.example.dishescollection.pojo.Ingredient;
import com.example.dishescollection.pojo.IngredientResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class IngredientsPresenter {

    private final IngredientsView ingredientsView;

    public IngredientsPresenter(IngredientsView ingredientsView) {
        this.ingredientsView = ingredientsView;
    }

    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        apiService.ingredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<>() {
                    @Override
                    public void onNext(IngredientResponse ingredientResponse) {
                        List<String> ingredients = new ArrayList<>();
                        for (Ingredient ingredient : ingredientResponse.getMeals())
                            ingredients.add(ingredient.getStrIngredient());
                        ingredientsView.showInfo(ingredients);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ingredientsView.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
