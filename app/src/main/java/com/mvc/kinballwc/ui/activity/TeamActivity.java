package com.mvc.kinballwc.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Player;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.adapter.PlayerRecyclerAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class TeamActivity extends BaseActivity {

    public static final String EXTRA_TEAM_ID = "teamId";

    private CollapsingToolbarLayout collapsingToolbar;

    private Team mTeam;
    private PlayerRecyclerAdapter mAdapter;
    private ImageView imageIV;
    private ImageView logoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logoIV = (ImageView) findViewById(R.id.teamLogo);
        imageIV = (ImageView) findViewById(R.id.backdrop);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setExpandedTitleColor(Color.YELLOW);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new PlayerRecyclerAdapter(new ArrayList<Player>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setClipToPadding(false);

//        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        String teamObjectId = getIntent().getStringExtra(EXTRA_TEAM_ID);
        if (teamObjectId != null) {
            getTeam(teamObjectId);
        }
    }



    private void getTeam(String teamObjectId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Team");
        query.include("players");
        query.include("players.roles");
        query.getInBackground(teamObjectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Team team = (Team) object;
                    onTeamReceived(team);
                } else {
                    // TODO something went wrong
                }
            }
        });
    }

    private void onTeamReceived(Team team) {
        mTeam = team;
        mAdapter.setPlayers(team.getPlayers());
        mAdapter.notifyDataSetChanged();

        collapsingToolbar.setTitle(team.getName());

        Glide.with(this)
                .load(team.getImage())
                .centerCrop()
                .into(imageIV);
        Glide.with(this)
                .load(team.getLogo())
                .placeholder(R.drawable.placeholder)
                .error(R.color.transparent)
                .fitCenter()
                .into(logoIV);
    }



}
