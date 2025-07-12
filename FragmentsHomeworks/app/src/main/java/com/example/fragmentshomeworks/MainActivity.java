package com.example.fragmentshomeworks;

import android.os.Bundle;
import android.view.WindowManager;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    private final String[] tabs = {"Cities", "Notebook", "Tab3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // for changing pages with navigation view
        DrawerLayout drawerLayout = findViewById(R.id.main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolBar);

        // for changing pages with tab bar
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);

        // navigation view methods
        //------------------------------------------------------------------------------------------

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            TabLayout.Tab tab;
            if (item.getItemId() == R.id.firstPage) {
                tab = tabLayout.getTabAt(0);
                assert tab != null;
                if (!tab.isSelected()) {
                    tab.select();
                }
            }

            if (item.getItemId() == R.id.secondPage) {
                tab = tabLayout.getTabAt(1);
                assert tab != null;
                if (!tab.isSelected()) {
                    tab.select();
                }
            }

            if (item.getItemId() == R.id.thirdPage) {
                tab = tabLayout.getTabAt(2);
                assert tab != null;
                if (!tab.isSelected()) {
                    tab.select();
                }
            }
            drawerLayout.closeDrawers();
            return true;
        });

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

        // tab layout methods
        //------------------------------------------------------------------------------------------

        PageAdapter adapter = new PageAdapter(this, tabLayout.getTabCount());
        viewPager2.setAdapter(adapter);
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
                    return new CitiesFragment();
                case 1:
                    return new Notebook();
                case 2:
                    return new ThirdFragment();
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