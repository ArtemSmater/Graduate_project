package com.example.dishescollection.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx. appcompat. widget. Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dishescollection.R;
import com.example.dishescollection.screens.fragments.fragments.areas.AreaFragment;
import com.example.dishescollection.screens.fragments.fragments.categories.CategoriesFragment;
import com.example.dishescollection.screens.fragments.fragments.ingredients.IngredientsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private final String[] tabs = {"Categories", "Areas", "Ingredients"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        // init views
        tabLayout = findViewById(R.id.tlTabs);
        viewPager2 = findViewById(R.id.vpMain);
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle(getString(R.string.dishes_library));
        toolbar.setTitleTextAppearance(this, R.style.NewTitleFont);
        setSupportActionBar(toolbar);

        // view fragments
        viewFragments();
    }

    private void viewFragments() {
        PageAdapter pageAdapter = new PageAdapter(this, tabLayout.getTabCount());
        viewPager2.setAdapter(pageAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabs[position])).attach();
    }

    public static class PageAdapter extends FragmentStateAdapter {
        private final int tabCount;
        public PageAdapter(@NonNull FragmentActivity fragmentActivity, int tabCount) {
            super(fragmentActivity);
            this.tabCount = tabCount;
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new CategoriesFragment();
                case 1:
                    return new AreaFragment();
                case 2:
                    return new IngredientsFragment();
            }
            return new CategoriesFragment();
        }

        @Override
        public int getItemCount() {
            return tabCount;
        }
    }
}