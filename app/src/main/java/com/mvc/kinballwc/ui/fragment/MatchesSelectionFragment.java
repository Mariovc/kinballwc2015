package com.mvc.kinballwc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.ui.activity.HomeActivity;
import com.mvc.kinballwc.ui.activity.MatchesActivity;
import com.mvc.kinballwc.utils.Utils;

/**
 * Author: Mario Velasco Casquero
 * Date: 27/6/15
 * Email: m3ario@gmail.com
 */
public class MatchesSelectionFragment extends Fragment {

    private static final String TAG = "MatchesSelectFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        View view = inflater.inflate(R.layout.fragment_matches_selection, container, false);

        ((HomeActivity) getActivity()).setupToolbar(view);

        Button nationsMenButton = (Button) view.findViewById(R.id.nationsMenButton);
        Button nationsWomenButton = (Button) view.findViewById(R.id.nationsWomenButton);
        Button clubsProButton = (Button) view.findViewById(R.id.clubsProButton);
        Button clubsAmateurButton = (Button) view.findViewById(R.id.clubsAmateurButton);
        Button allButton = (Button) view.findViewById(R.id.allButton);
        nationsMenButton.setOnClickListener(new OnCategoryClick(Utils.NATIONS_MAN));
        nationsWomenButton.setOnClickListener(new OnCategoryClick(Utils.NATIONS_WOMAN));
        clubsProButton.setOnClickListener(new OnCategoryClick(Utils.CLUBS_PRO));
        clubsAmateurButton.setOnClickListener(new OnCategoryClick(Utils.CLUBS_AMATEUR));
        allButton.setOnClickListener(new OnCategoryClick(null));
        return view;
    }


    private void launchMatchesActivity(String category) {
        Intent intent = new Intent(getActivity(), MatchesActivity.class);
        intent.putExtra(MatchesActivity.EXTRA_CATEGORY, category);
//                intent.putExtra(MatchesActivity.EXTRA_TEAM, "Cn0pwgKidy");
        startActivity(intent);
    }

    class OnCategoryClick implements View.OnClickListener {

        private String mCategory;

        public OnCategoryClick(String category) {
            this.mCategory = category;
        }

        @Override
        public void onClick(View v) {
            launchMatchesActivity(mCategory);
        }
    }

}
