package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Author: Mario Velasco Casquero
 * Date: 28/6/15
 * Email: m3ario@gmail.com
 */
@ParseClassName("MatchPoints")
public class MatchPoints extends ParseObject {

    public static final int MATCH_POINTS_FIRST = 10;
    public static final int MATCH_POINTS_SECOND = 6;
    public static final int MATCH_POINTS_THIRD = 2;


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
