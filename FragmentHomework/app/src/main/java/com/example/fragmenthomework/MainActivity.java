package com.example.fragmenthomework;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private final String[] tabs = {"BMW", "TOYOTA", "LADA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // initialize the views
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        NavigationView navigationView = findViewById(R.id.nav_view);
        DrawerLayout drawerLayout = findViewById(R.id.main);
        Toolbar toolbar = findViewById(R.id.toolBar);

        // Create an ActionBarDrawerToggle to handle the drawer's open/close state
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        // Add the toggle as a listener to the DrawerLayout
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
        });


        // create a page adapter
        PageAdapter adapter = new PageAdapter(this, tabLayout.getTabCount());

        // set the adapter to the view pager
        viewPager2.setAdapter(adapter);

        // set na animation when the page changed
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());


        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabs[position])).attach();

        navigationView.setNavigationItemSelectedListener(item -> {
            TabLayout.Tab tab;
            if (item.getItemId() == R.id.first_item) {
                tab = tabLayout.getTabAt(0);
                assert tab != null;
                if(!tab.isSelected()) {
                    tab.select();
                }
            }

            if (item.getItemId() == R.id.second_item) {
                tab = tabLayout.getTabAt(1);
                assert tab != null;
                if(!tab.isSelected()) {
                    tab.select();
                }
            }

            if (item.getItemId() == R.id.third_item) {
                tab = tabLayout.getTabAt(2);
                assert tab != null;
                if(!tab.isSelected()) {
                    tab.select();
                }
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private static class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.05f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();
            if (position < -1) {
                page.setAlpha(0f);
            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    page.setTranslationX(horizontalMargin / 2);
                } else {
                    page.setTranslationX(-horizontalMargin / 2);
                }
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_SCALE));
            } else {
                page.setAlpha(0f);
            }
        }
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
                    return new BlankAura();
                case 1:
                    return new BlankVesta();
                case 2:
                    return new BlankGranta();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return tabCount;
        }
    }
}