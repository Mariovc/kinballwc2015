package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Created by mario on 28/6/15.
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
}
