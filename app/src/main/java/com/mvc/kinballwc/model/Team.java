package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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

    public String getMotto(){
        return getString("motto");
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

    public void setName(String name){
        put("name", name);
    }

    public void setMotto(String motto){
        put("motto", motto);
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
}
