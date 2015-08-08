package com.mvc.kinballwc.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Player;
import com.mvc.kinballwc.model.Role;
import com.mvc.kinballwc.ui.activity.TeamActivity;
import com.mvc.kinballwc.utils.Utils;

import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 04/08/2015
 * Email: m3ario@gmail.com
 */
public class PlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "PlayerRecyclerAdapter";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private TeamActivity activity;
    private List<Player> mPlayers;

    public PlayerRecyclerAdapter(TeamActivity activity, List<Player> players) {
        this.activity = activity;
        this.mPlayers = players;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_player, parent, false);
            return new VHItem(view);
        } else if (viewType == TYPE_HEADER) {
            TextView tv = new TextView(parent.getContext());
//            ImageView iv = new ImageView(parent.getContext());
//            iv.setImageResource(R.drawable.placeholder);
//            tv.setText("header");
            return new VHHeader(tv);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            VHItem itemHolder = (VHItem) holder;
            try {
                final Player player = mPlayers.get(position - 1);

                itemHolder.mName.setText(getCompleteName(player));
                String rolesString = getRoleString(itemHolder.mRole.getContext(), player.getRoles());
                itemHolder.mRole.setText(rolesString);

                View.OnClickListener onClickListener =
                        activity.new ExpandOnClickListener(itemHolder.mImageView, player.getImage());
                itemHolder.mView.setOnClickListener(onClickListener);
//                itemHolder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                Context context = v.getContext();
////                Intent intent = new Intent(context, TeamActivity.class);
////                intent.putExtra(TeamActivity.EXTRA_TEAM_ID, team.getObjectId());
////                context.startActivity(intent);
//                        Log.d(TAG, getCompleteName(player));
//                        Toast.makeText(v.getContext(), getCompleteName(player), Toast.LENGTH_SHORT).show();
//                    }
//                });
                loadImage(itemHolder.mImageView, player.getImage());
            } catch (Exception e) {
                return;
            }

        } else if (holder instanceof VHHeader) {
            //cast holder to VHHeader and set data for header.
        }
    }

    @Override
    public int getItemCount() {
        return mPlayers.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Player getItem(int position) {
        return mPlayers.get(position - 1);
    }

    public void setPlayers(List<Player> players) {
        this.mPlayers = players;
    }

    class VHItem extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView mName;
        public final TextView mRole;

        public VHItem(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.playerImage);
            mName = (TextView) view.findViewById(R.id.playerName);
            mRole = (TextView) view.findViewById(R.id.playerRole);
        }

    }

    class VHHeader extends RecyclerView.ViewHolder {
        Button button;

        public VHHeader(View itemView) {
            super(itemView);
        }
    }

    private void loadImage(final ImageView imageView, final String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
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

    public String getRoleString(Context context, List<Role> roles) {
        StringBuilder rolesString = new StringBuilder();
        if (roles != null) {
            for (int i = 0; i < roles.size(); i++) {
                if (i > 0) {
                    rolesString.append(" | ");
                }
                String roleName = Utils.getTranslatedRole(context, roles.get(i).getName());
                rolesString.append(roleName);
            }
        }
        return rolesString.toString();
    }

    public String getCompleteName(Player player) {
        StringBuilder completeName = new StringBuilder();
        if (player.getName() != null) {
            completeName.append(player.getName());
        }
//        if (player.getSurname() != null) {
//            completeName.append(" ");
//            completeName.append(player.getSurname());
//        }
        return completeName.toString();
    }
}
