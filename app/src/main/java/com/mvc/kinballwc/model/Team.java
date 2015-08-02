package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by mario on 27/6/15.
 */
@ParseClassName("Team")
public class Team extends ParseObject {
    public enum Equipo
    {
        PRO_CATEGORY("",1), REAL_MADRID("Real Madrid",2),
        SEVILLA("Sevilla FC",4), VILLAREAL("Villareal",7);

        private String nombreClub;
        private int puestoLiga;

        private Equipo (String nombreClub, int puestoLiga){
            this.nombreClub = nombreClub;
            this.puestoLiga = puestoLiga;
        }

        public String getNombreClub() {
            return nombreClub;
        }

        public int getPuestoLiga() {
            return puestoLiga;
        }

    }

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
