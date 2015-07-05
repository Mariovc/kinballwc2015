package com.mvc.kinballwc.ui.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Player;
import com.mvc.kinballwc.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 28/6/15.
 */
public class TeamAdapter extends ArrayAdapter<Team> {

    private LayoutInflater mInflater;

    public TeamAdapter(final Context context, final List<Team> items) {

        super(context, R.layout.item_team, items);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_team, null);

            viewHolder = new ViewHolder();
            viewHolder.listItemImageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.listItemNameTextView = (TextView) convertView.findViewById(R.id.textView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Team team = getItem(position);
        viewHolder.listItemNameTextView.setText(team.getName());
        Ion.with(viewHolder.listItemImageView)
                .placeholder(android.R.drawable.ic_popup_sync)
                .error(android.R.drawable.ic_delete)
                .load(team.getLogo());
        if (team.getPlayers() == null) {
            ArrayList<Player> players = new ArrayList<>();
            Player player = new Player();
            player.setName("Sara");
            players.add(player);
            Player player2 = new Player();
            player2.setName("Jaime");
            players.add(player2);
            team.setPlayers(players);
            team.saveInBackground();

        } else {
            // Toast.makeText(getContext(), ""+team.getPlayers().size(), Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }

    static class ViewHolder {

        ImageView listItemImageView;
        TextView listItemNameTextView;
    }

}
