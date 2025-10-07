package com.example.dishescollection.screens.fragments.fragments.categories;

import com.example.dishescollection.api.ApiFactory;
import com.example.dishescollection.api.ApiService;
import com.example.dishescollection.pojo.CategoryResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CategoriesPresenter {

    private final CategoriesView categoriesView;

    public CategoriesPresenter(CategoriesView categoriesView) {
        this.categoriesView = categoriesView;
    }

    public void loadData() {

        // get web info
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        apiService.categories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<>() {
                    @Override
                    public void onNext(CategoryResponse categoryResponse) {
                        categoriesView.showInfo(categoryResponse.getCategories());
                    }

                    @Override
                    public void onError(Throwable e) {
                        categoriesView.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
