package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by mario on 28/6/15.
 */
@ParseClassName("MatchPoints")
public class MatchPoints extends ParseObject {

    public int getWonPeriods(){
        return getInt("wonPeriods");
    }

    public int getMatchPoints(){
        return getInt("matchPoints");
    }

    public int getSportsmanshipPoints(){
        return getInt("sportsmanshipPoints");
    }

    public int getPenaltyPoints(){
        return getInt("penaltyPoints");
    }

    public int getTotalPoints(){
        return getInt("totalPoints");
    }

    public void setWonPeriods(int wonPeriods){
        put("wonPeriods", wonPeriods);
    }

    public void setMatchPoints(int matchPoints){
        put("matchPoints", matchPoints);
    }

    public void setSportsmanshipPoints(int sportsmanshipPoints){
        put("sportsmanshipPoints", sportsmanshipPoints);
    }

    public void setPenaltyPoints(int penaltyPoints){
        put("penaltyPoints", penaltyPoints);
    }

    public void setTotalPoints(int totalPoints){
        put("totalPoints", totalPoints);
    }
}
