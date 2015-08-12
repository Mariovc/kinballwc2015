package com.mvc.kinballwc.model;

import android.content.Context;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.application.App;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 28/6/15
 * Email: m3ario@gmail.com
 */
@ParseClassName("Match")
public class Match extends ParseObject{

    public String getState(){
        return getString("state");
    }

    public Team getTeam1() {
        return (Team) getParseObject("team1");
    }

    public Team getTeam2() {
        return (Team) getParseObject("team2");
    }

    public Team getTeam3() {
        return (Team) getParseObject("team3");
    }

    public String getCategory() {
        return getString("category");
    }

    public String getTitle() {
        return getString("title");
    }

    public Date getDate() {
        return getDate("date");
    }

    public MatchPoints getTeam1Points() {
        return (MatchPoints) getParseObject("team1Points");
    }

    public MatchPoints getTeam2Points() {
        return (MatchPoints) getParseObject("team2Points");
    }

    public MatchPoints getTeam3Points() {
        return (MatchPoints) getParseObject("team3Points");
    }

    public List<MatchPeriod> getPeriods(){
        return getList("periods");
    }

    public int getCourt() {
        return  getInt("court");
    }

    public String getCourtString() {
        Context context = App.getAppContext();
        String[] courts = context.getResources().getStringArray(R.array.courts);
        String court = courts[getCourt()-1];
        return court;
    }

    public void setState(String state){
        put("state", state);
    }

    public void setTeam1(Team team1) {
        put("team1", team1);
    }

    public void setTeam2(Team team1) {
        put("team1", team1);
    }

    public void setTeam3(Team team1) {
        put("team1", team1);
    }

    public void setCategory(String category) {
        put("category", category);
    }

    public void setTitle(String title){
        put("title", title);
    }

    public void setDate(Date date) {
        put("date", date);
    }

    public void setTeam1Points(MatchPoints matchPoints1) {
        put("team1Points", matchPoints1);
    }

    public void setTeam2Points(MatchPoints matchPoints2) {
        put("team2Points", matchPoints2);
    }

    public void setTeam3Points(MatchPoints matchPoints3) {
        put("team3Points", matchPoints3);
    }

    public void setPeriods(List<MatchPeriod> periods) {
        put("periods", periods);
    }

    public void setCourt(int court) {
        put("court", court);
    }

    public static class MatchComparator implements Comparator<Match> {
        @Override
        public int compare(Match match1, Match match2) {
            int result = match1.getDate().compareTo(match2.getDate());
            if (result == 0) {
                result = match1.getCourt() - match2.getCourt();
                if (result == 0) {
                    result = match1.getTitle().compareTo(match2.getTitle());
                }
            }
            return result;
        }
    }
}
