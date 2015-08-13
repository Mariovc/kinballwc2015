package com.mvc.kinballwc.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Referee;
import com.mvc.kinballwc.ui.fragment.RefereesFragment;
import com.mvc.kinballwc.utils.Utils;

import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 02/08/2015
 * Email: m3ario@gmail.com
 */
public class RefereeRecyclerAdapter
        extends RecyclerView.Adapter<RefereeRecyclerAdapter.ViewHolder> {

    private static final String TAG = "TeamRecyclerAdapter";
    private RefereesFragment mFragment;
    private List<Referee> mReferees;
    private BitmapPool mPool;

    public RefereeRecyclerAdapter(RefereesFragment fragment, List<Referee> referees) {
        this.mFragment = fragment;
        this.mReferees = referees;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_referee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Referee referee = mReferees.get(position);
        holder.mTextView.setText(referee.getName());

        loadImage(holder.mImageView, referee.getImage());

        View.OnClickListener onClickListener =
                mFragment.new ExpandOnClickListener(holder.mImageView, referee.getImage());
        holder.mView.setOnClickListener(onClickListener);
    }

    private void loadImage(final ImageView imageView, final String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.d(TAG, "Glide failed loading image " + url);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageView.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mReferees.size();
    }
}
