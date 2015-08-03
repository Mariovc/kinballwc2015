package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by mario on 28/6/15.
 */
@ParseClassName("MatchPeriod")
public class MatchPeriod extends ParseObject {

    public int getTeam1Score(){
        return getInt("team1Score");
    }
    public int getTeam2Score(){
        return getInt("team2Score");
    }
    public int getTeam3Score(){
        return getInt("team3Score");
    }



    public void setTeam1Score(int score){
        put("team1Score", score);
    }

    public void setTeam2Score(int score){
        put("team2Score", score);
    }

    public void setTeam3Score(int score){
        put("team3Score", score);
    }
}
