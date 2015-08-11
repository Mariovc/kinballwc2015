package com.mvc.kinballwc.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.ui.fragment.ClassificationFragment;
import com.mvc.kinballwc.ui.fragment.MatchesSelectionFragment;
import com.mvc.kinballwc.ui.fragment.TeamsFragment;
import com.mvc.kinballwc.utils.SocialNetworkUtils;
import com.mvc.kinballwc.utils.Utils;

/**
 * Author: Mario Velasco Casquero
 * Date: 27/6/15
 * Email: m3ario@gmail.com
 */
public class HomeActivity extends AppCompatActivity {

    private static final int INITIAL_SECTION = 1;

    private DrawerLayout mDrawerLayout;
    protected boolean isActivityDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        if (Utils.isFistTime(this)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            Utils.setFistTime(this, false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityDestroyed = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collapsing_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_twitter:
                SocialNetworkUtils.launchTwitter(this);
                return true;
            case R.id.menu_facebook:
                SocialNetworkUtils.launchFacebook(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            onNavigationItemSelection(menuItem);
                            return true;
                        }
                    });
            MenuItem initialMenuItem = navigationView.getMenu().getItem(INITIAL_SECTION);
            onNavigationItemSelection(initialMenuItem);
        }
    }

    private void onNavigationItemSelection(MenuItem menuItem) {
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        changeFragment(menuItem);
    }

    public void changeFragment(MenuItem menuItem) {
        setTitle(menuItem.getTitle());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, getFragmentForMenuItem(menuItem))
                .commit();
//        Toast.makeText(this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
    }


    private Fragment getFragmentForMenuItem(MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_matches:
                fragment = new MatchesSelectionFragment();
                break;
            case R.id.nav_teams:
                fragment = new TeamsFragment();
                break;
            case R.id.nav_classification:
                fragment = new ClassificationFragment();
                break;
        }
        return fragment;
    }

    public void setupToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
