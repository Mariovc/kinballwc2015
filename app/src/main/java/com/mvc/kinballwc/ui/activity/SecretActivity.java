package com.mvc.kinballwc.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.application.App;

public class SecretActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        TextView modeTV = (TextView) findViewById(R.id.modeTV);
        TextView refreshTimeTV = (TextView) findViewById(R.id.refreshTimeTV);
        TextView appVersionTV = (TextView) findViewById(R.id.appVersionTV);
        modeTV.setText(String.valueOf(App.useGCM));
        refreshTimeTV.setText(String.valueOf(App.refreshTime));
        appVersionTV.setText(getAppVersion());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("chancleta")) {
                    App.allowEdit = true;
                    finish();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.allowEdit = false;
                finish();
            }
        });

        if (App.allowEdit) {
            editText.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    private String getAppVersion(){
        Context context = App.getAppContext();
        try {
            String versionName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
