package com.mvc.kinballwc.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.ui.adapter.HeaderAdapter;
import com.mvc.kinballwc.ui.view.ControllableAppBarLayout;

public class TeamActivity extends BaseActivity {

    public static final String EXTRA_TEAM_ID = "teamId";

    private ControllableAppBarLayout appBarLayout;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        final String objectId = getIntent().getStringExtra(EXTRA_TEAM_ID);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Les Dragons du Montreal");
        collapsingToolbar.setTitle("A.D. KCB Kin-Ball Team");
//        collapsingToolbar.setExpandedTitleColor(Color.YELLOW);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        loadBackdrop();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        HeaderAdapter mAdapter = new HeaderAdapter(new String[]{"asdf", "ffjl", "ñlj"});
        recyclerView.setAdapter(mAdapter);

//        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        appBarLayout = (ControllableAppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarLayout.expandToolbar(true);
            }
        });
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this)
                .load("http://pbs.twimg.com/media/Bw7te3iIgAA0qdq.jpg")
//                .load("http://kin-ball2015.es/pictures/fotos_jugadores/Fotos%20de%20equipo/Naciones/Femenino/Espana.JPG")
                .centerCrop()
                .into(imageView);
    }

}
