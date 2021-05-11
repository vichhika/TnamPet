package com.stem303.tnampet;

import android.content.Context;
import android.content.res.Configuration;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Config;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.stem303.tnampet.ui.bookmark.BookmarkFragment;
import com.stem303.tnampet.ui.home.DetailFragment;
import com.stem303.tnampet.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    MenuItem menuLang;
    Locale locale;
    Toolbar toolbar;
    NavigationView navigationView;
    HomeFragment homeFragment;
    BookmarkFragment bookmarkFragment;
    InputMethodManager inputMethodManager;
    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locale = Locale.getDefault();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.home_toolbar);
        editText = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        homeFragment = new HomeFragment();
        bookmarkFragment = new BookmarkFragment();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home){
                    goToFragment(homeFragment);
                }
                else if(id == R.id.nav_bookmark){
                    goToFragment(bookmarkFragment);
                }
                drawer.findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                Log.d("navbar", "onNavigationItemSelected: "+navigationView.getCheckedItem().getItemId());
                return true;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                homeFragment.getFilter().filter(editable.toString().toLowerCase());
            }
        });

        goToFragment(homeFragment);


    }

    @Override
    protected void onStart() {
        super.onStart();
        String activeFregment = getSupportFragmentManager().findFragmentById(R.id.nav_fragment_container).getClass().getSimpleName();
        if(activeFregment.equals(HomeFragment.class.getSimpleName())){
            homeFragment.fetchData();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String id;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuLang = menu.findItem(R.id.nav_lang);
        if (locale.toString().equals("en_US")) menuLang.setIcon(getDrawable(R.drawable.ic_united_kingdom));
        else if (locale.toString().equals("km_KH")) menuLang.setIcon(getDrawable(R.drawable.ic_cambodia));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
//        SharedPref.saveState(this,"lang_id",String.valueOf(id));
        if (id == R.id.lang_khmer) {
            menuLang.setIcon(getDrawable(R.drawable.ic_cambodia));
            Toast.makeText(this,"Function not available",Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.lang_english) {
            menuLang.setIcon(getDrawable(R.drawable.ic_united_kingdom));
            Toast.makeText(this,"Function not available",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.action_clear) {
            Toast.makeText(this,"All clear",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        String activeFregment = getSupportFragmentManager().findFragmentById(R.id.nav_fragment_container).getClass().getSimpleName();
        if(!activeFregment.equals(HomeFragment.class.getSimpleName())){
            goToFragment(homeFragment);
            navigationView.setCheckedItem(R.id.nav_home);
        }else{
            super.onBackPressed();
        }
    }

    public void goToFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_fragment_container, fragment);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        editText.getText().clear();
        fragmentTransaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String activeFregment = getSupportFragmentManager().findFragmentById(R.id.nav_fragment_container).getClass().getSimpleName();
        if(activeFregment.equals(BookmarkFragment.class.getSimpleName())){
            menuLang.setVisible(false);
            toolbar.findViewById(R.id.search_toolbar).setVisibility(View.GONE);
            toolbar.setTitle(R.string.menu_bookmark);
        }else if(activeFregment.equals(HomeFragment.class.getSimpleName())){
            menuLang.setVisible(true);
            toolbar.findViewById(R.id.search_toolbar).setVisibility(View.VISIBLE);
            toolbar.setTitle(R.string.menu_home);
        }else if(activeFregment.equals(DetailFragment.class.getSimpleName())){
            menuLang.setVisible(false);
            toolbar.findViewById(R.id.search_toolbar).setVisibility(View.GONE);
            toolbar.setTitle(R.string.definition);
        }
        return super.onPrepareOptionsMenu(menu);
    }

}