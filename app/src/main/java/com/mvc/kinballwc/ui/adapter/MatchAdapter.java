package com.mvc.kinballwc.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.model.Player;
import com.mvc.kinballwc.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 28/6/15.
 */
public class MatchAdapter extends ArrayAdapter<Match> {

    private static final String SEPARATOR = " - ";

    private Context context;
    private LayoutInflater mInflater;

    public MatchAdapter(final Context context, final List<Match> items) {
        super(context, R.layout.item_team, items);
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_match2, null);

            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.matchNameTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Match match = getItem(position);

        Resources res = context.getResources();
        TextView tv = viewHolder.nameTextView;

//        String team1Name = "Les Dragons de Montréal"; // match.getTeam1().getName();
//        String team2Name = "A.D. KCB Kin-Ball Team"; //match.getTeam2().getName();
//        String team3Name = "Kin-Ball Hannut Lincent"; //match.getTeam3().getName();
//        int startOfTeam2 = team1Name.length() + SEPARATOR.length();
//        int startOfTeam3 = startOfTeam2 + team2Name.length() + SEPARATOR.length();
//        Spannable wordtoSpan = new SpannableString(team1Name + SEPARATOR +
//                team2Name + SEPARATOR + team3Name);
//        wordtoSpan.setSpan(new ForegroundColorSpan(res.getColor(R.color.team1)),
//                0, team1Name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setSpan(new ForegroundColorSpan(res.getColor(R.color.team2)),
//                startOfTeam2, startOfTeam2 + team2Name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setSpan(new ForegroundColorSpan(res.getColor(R.color.team3)),
//                startOfTeam3, startOfTeam3 + team3Name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv.setText(wordtoSpan);

        //viewHolder.listItemNameTextView.setText(team.getName());


        return convertView;
    }

    static class ViewHolder {

        TextView nameTextView;
    }

}
