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
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        ((HomeActivity) getActivity()).setupToolbar(view);

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MatchesActivity.class);
                intent.putExtra(MatchesActivity.EXTRA_CATEGORY, "CLUBS PRO");
//                intent.putExtra(MatchesActivity.EXTRA_TEAM, "Cn0pwgKidy");
                startActivity(intent);
            }
        });
        return view;
    }




}
