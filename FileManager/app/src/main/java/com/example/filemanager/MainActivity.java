package com.example.filemanager;

import android.os.Bundle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.filemanager.fragments.CardFragment;
import com.example.filemanager.fragments.InternalFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitleTextAppearance(this, R.style.NewTitleFont);
        setSupportActionBar(toolbar);

        // to open side menu
        NavigationView navigationView = findViewById(R.id.navView);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);

        // open first fragment
        replaceFragment(new InternalFragment());

        // set listener to the navigation view
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navInternal) {
                replaceFragment(new InternalFragment());
            } else if (item.getItemId() == R.id.navCard) {
                replaceFragment(new CardFragment());
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}