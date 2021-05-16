package com.stem303.tnampet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.DynamicsProcessing;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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
    AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locale = Locale.getDefault();
        setContentView(R.layout.activity_main);

        //Add advertise
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //End Add advertise

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
                }else if(id == R.id.nav_contact_us){
                    Uri uri = Uri.parse("https://github.com/vichhika");
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }else if(id == R.id.nav_rate){
                    Uri uri = Uri.parse("https://www.facebook.com/%E1%9E%90%E1%9F%92%E1%9E%93%E1%9E%B6%E1%9F%86%E1%9E%96%E1%9F%81%E1%9E%91%E1%9F%92%E1%9E%99-App-103047508634587");
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }else if(id == R.id.nav_share){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String title = " \uD83D\uDC49 \uD83D\uDC49 \uD83D\uDC49តោះ! ដោនលូតកម្មវិធីថ្នាំពេទ្យទាំងអស់គ្នា ដើម្បីចំណេះដឹងសុខភាពទាំងអស់គ្នា។\uD83D\uDE0E \n\n \uD83D\uDC47\uD83D\uDC47\uD83D\uDC47";
                    String content = "https://cutt.ly/SbSlOT1";
                    intent.putExtra(Intent.EXTRA_SUBJECT,title);
                    intent.putExtra(Intent.EXTRA_TEXT,content);
                    startActivity(Intent.createChooser(intent,"Share through"));
                }else if(id == R.id.nav_help){
                    helpPopup();
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

    public void helpPopup(){
        Button button;
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_help);
        button = dialog.findViewById(R.id.ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}