package com.example.dishescollection.screens.meals;


import com.example.dishescollection.api.ApiFactory;
import com.example.dishescollection.api.ApiService;
import com.example.dishescollection.pojo.MealResponse;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MealsListPresenter {

    private ApiService apiService;
    private final MealsListView listView;

    public MealsListPresenter(MealsListView listView) {
        this.listView = listView;
    }

    public void loadData(int tag, String parameter) {

        // get web objects
        ApiFactory apiFactory = ApiFactory.getInstance();
        apiService = apiFactory.getApiService();

        // call method by tag
        if (tag == 1) setCategoriesList(parameter);
        else if (tag == 2) setAreasList(parameter);
        else if (tag == 3) setIngredientList(parameter);
    }

    private void setIngredientList(String ingredient) {
        apiService.mealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<>() {
                    @Override
                    public void onNext(MealResponse mealResponse) {
                        listView.showInfo(mealResponse.getMeals());

                    }

                    @Override
                    public void onError(Throwable e) {
                        listView.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setAreasList(String area) {
        apiService.mealsByArea(area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<>() {
                    @Override
                    public void onNext(MealResponse mealResponse) {
                        listView.showInfo(mealResponse.getMeals());
                    }

                    @Override
                    public void onError(Throwable e) {
                        listView.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setCategoriesList(String category) {
        apiService.mealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<>() {
                    @Override
                    public void onNext(MealResponse mealResponse) {
                        listView.showInfo(mealResponse.getMeals());
                    }

                    @Override
                    public void onError(Throwable e) {
                        listView.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
