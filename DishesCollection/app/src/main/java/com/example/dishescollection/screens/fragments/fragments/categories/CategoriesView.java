package com.example.dishescollection.screens.fragments.fragments.categories;

import com.example.dishescollection.pojo.Category;

import java.util.List;

public interface CategoriesView {
    void showInfo(List<Category> categories);

    void showError();
}
