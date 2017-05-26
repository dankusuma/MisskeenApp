package com.plbtw.misskeen_app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.plbtw.misskeen_app.Fragment.BookmarkFragment;
import com.plbtw.misskeen_app.Fragment.HomeFragment;
import com.plbtw.misskeen_app.Fragment.ProfileFragment;
import com.plbtw.misskeen_app.Fragment.RecipeListFragment;
import com.plbtw.misskeen_app.Fragment.SearchFragment;
import com.plbtw.misskeen_app.R;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;
    private Toolbar toolbar;

    public static int navItemIndex = 0;

    private String[] activityTitles;

    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_RECIPE = "recipe";
    private static final String TAG_BOOKMARK = "bookmark";
    private static final String TAG_SEARCH = "search";
    public static String CURRENT_TAG = TAG_HOME;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navHeader = navigationView.getHeaderView(0);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }


    }

    private void loadHomeFragment() {
        selectNavMenu();

        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawer.closeDrawers();

        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // profile
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 2:
                // recipe fragment
                RecipeListFragment recipeListFragment = new RecipeListFragment();
                return recipeListFragment;
            case 3:
                // bookmark fragment
                BookmarkFragment bookmarkFragment = new BookmarkFragment();
                return bookmarkFragment;

            case 4:
                //search
                SearchFragment searchFragment = new SearchFragment();
                return searchFragment;

            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_recipe:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_RECIPE;
                        break;
                    case R.id.nav_bookmark:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_BOOKMARK;
                        break;
                    case R.id.nav_search:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SEARCH;
                        break;
                    case R.id.nav_about_us:
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                    case R.id.nav_privacy_logout:
                        SharedPreferences ListRecipeSharedPrefs = PreferenceManager
                                .getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor prefsEditor =  ListRecipeSharedPrefs.edit();
                        prefsEditor.clear();
                        prefsEditor.commit();
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        if (navItemIndex == 4) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

}