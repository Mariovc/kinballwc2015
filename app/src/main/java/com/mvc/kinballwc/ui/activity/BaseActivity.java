package com.mvc.kinballwc.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.utils.SocialNetworkUtils;

/**
 * Author: Mario Velasco Casquero
 * Date: 19/07/2015
 * Email: m3ario@gmail.com
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
                finish();
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

    public void setToolbarTitle(CharSequence title) {
        Log.d(TAG, "Setting toolbar title to " + title);
        if (title == null) {
            title = getString(R.string.app_name);
        }
        setTitle(title);
    }

}
