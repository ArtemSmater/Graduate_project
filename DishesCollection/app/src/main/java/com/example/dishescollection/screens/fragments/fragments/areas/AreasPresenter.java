package com.example.dishescollection.screens.fragments.fragments.areas;

import com.example.dishescollection.api.ApiFactory;
import com.example.dishescollection.api.ApiService;
import com.example.dishescollection.pojo.Area;
import com.example.dishescollection.pojo.AreaResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AreasPresenter {

    private final AreasView areasView;

    public AreasPresenter(AreasView areasView) {
        this.areasView = areasView;
    }

    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        apiService.areas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<>() {
                    @Override
                    public void onNext(AreaResponse areaResponse) {
                        List<String> areas = new ArrayList<>();
                        for (Area area : areaResponse.getMeals()) areas.add(area.getStrArea());
                        areasView.showData(areas);
                    }

                    @Override
                    public void onError(Throwable e) {
                        areasView.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
