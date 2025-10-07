package com.example.dishescollection.screens.details;


import com.example.dishescollection.api.ApiService;
import com.example.dishescollection.api.ApiStringFactory;
import com.example.dishescollection.api.ParseJSONObject;
import com.example.dishescollection.pojo.DetailMeal;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MealDetailPresenter {

    public MealDetailView detailView;

    public MealDetailPresenter(MealDetailView detailView) {
        this.detailView = detailView;
    }

    public void loadData(String mealId) {
        ApiStringFactory stringFactory = ApiStringFactory.getInstance();
        ApiService apiService = stringFactory.getApiService();
        assert mealId != null;
        apiService.mealDetailAsLine(Integer.parseInt(mealId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<>() {
                    @Override
                    public void onNext(String s) {
                        DetailMeal result;
                        try {
                            result = ParseJSONObject.getDetailMeal(new JSONObject(s));
                            detailView.showData(result);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        detailView.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
