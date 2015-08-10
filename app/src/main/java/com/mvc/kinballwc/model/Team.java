package com.mvc.kinballwc.model;

import com.mvc.kinballwc.application.App;
import com.mvc.kinballwc.utils.Utils;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Comparator;
import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 27/6/15
 * Email: m3ario@gmail.com
 */
@ParseClassName("Team")
public class Team extends ParseObject {

    public String getName(){
        return getString("name");
    }

    public String getLogo(){
        return getString("logo");
    }

    public String getImage(){
        return getString("image");
    }

    public List<Player> getPlayers(){
        return getList("players");
    }

    public String getNations() {
        return getString("nations");
    }

    public void setName(String name){
        put("name", name);
    }

    public void setLogo(String logo){
        put("logo", logo);
    }

    public void setImage(String image){
        put("image", image);
    }

    public void setPlayers(List<Player> players) {
        put("players", players);
    }

    public void setNations(String nations){
        put("nations", nations);
    }


    public static class NameComparator implements Comparator<Team> {
        @Override
        public int compare(Team team1, Team team2) {
            String team1Name = Utils.getTranslatedCountry(App.getAppContext(), team1.getName());
            String team2Name = Utils.getTranslatedCountry(App.getAppContext(), team2.getName());
            return team1Name.compareTo(team2Name);
        }
    }
}
