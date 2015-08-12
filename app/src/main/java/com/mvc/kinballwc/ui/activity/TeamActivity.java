package com.mvc.kinballwc.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Player;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.adapter.PlayerRecyclerAdapter;
import com.mvc.kinballwc.utils.Utils;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class TeamActivity extends BaseActivity {

    private static final String TAG = "TeamsActivity";

    public static final String EXTRA_TEAM_ID = "teamId";
    private static final int SHORT_ANIMATION_DURATION = 200;

    private Team mTeam;

    private PlayerRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Animator mCurrentAnimator;
    private View lastThumbImage;
    private Rect startBounds;
    private float startScaleFinal;


    private ImageView imageIV;
    private ImageView expandedImageView;
    private ProgressBar progressBar;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageIV = (ImageView) findViewById(R.id.backdrop);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        expandedImageView = (ImageView) findViewById(R.id.expanded_image);

        imageIV.setOnClickListener(new ExpandOnClickListener(imageIV, null));

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new PlayerRecyclerAdapter(this, new ArrayList<Player>(), null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setClipToPadding(false);

//        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String teamObjectId = getIntent().getStringExtra(EXTRA_TEAM_ID);
        if (teamObjectId != null) {
            getTeam(teamObjectId);
        }
    }

    @Override
    public void onBackPressed() {
        if (expandedImageView.getVisibility() == View.VISIBLE) {
            zoomOutImage(lastThumbImage);
        } else {
            super.onBackPressed();
        }
    }

    private void getTeam(String teamObjectId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Team");
        query.include("players");
        query.include("players.roles");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
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

    private void onTeamReceived(final Team team) {
        if (isActivityDestroyed) {
            Log.d(TAG, "Activity is destroyed after Parse query");
            return;
        }
        mTeam = team;

        mAdapter.setTeam(team);
        mAdapter.setPlayers(team.getPlayers());
        mAdapter.notifyDataSetChanged();
        mRecyclerView.invalidate();
//        mAdapter = new PlayerRecyclerAdapter(this, team.getPlayers());
//        mRecyclerView.setAdapter(mAdapter);

        collapsingToolbar.setTitle(Utils.getTranslatedCountry(this, team.getName()));

        Glide.with(this)
                .load(team.getImage())
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageIV);

        imageIV.setOnClickListener(new ExpandOnClickListener(imageIV, team.getImage()));
    }



    private void zoomImageFromThumb(final View thumbView, String url) {
        if (thumbView == null) { // || TextUtils.isEmpty(url)){
            return;
        }
        lastThumbImage = thumbView;

        progressBar.setVisibility(View.VISIBLE);
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_error)
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        expandedImageView.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(expandedImageView);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.rootLayout)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(SHORT_ANIMATION_DURATION);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomOutImage(thumbView);
            }
        });
    }

    private void zoomOutImage(final View thumbView) {
        progressBar.setVisibility(View.GONE);

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Animate the four positioning/sizing properties in parallel,
        // back to their original values.
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator
                .ofFloat(expandedImageView, View.X, startBounds.left))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.Y,startBounds.top))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.SCALE_X, startScaleFinal))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.SCALE_Y, startScaleFinal));
        set.setDuration(SHORT_ANIMATION_DURATION);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;
    }




    public class ExpandOnClickListener implements View.OnClickListener {

        private View mThumbImage;
        private String mUrl;

        public ExpandOnClickListener(View view, String url) {
            this.mThumbImage = view;
            this.mUrl = url;
        }

        @Override
        public void onClick(View v) {
            zoomImageFromThumb(mThumbImage, mUrl);
        }

    }
}
