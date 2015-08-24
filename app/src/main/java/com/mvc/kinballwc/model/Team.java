package com.mvc.kinballwc.model;

import android.content.Context;
import android.text.TextUtils;

import com.mvc.kinballwc.R;
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

    private static final String COUNTRY_BELGIUM = "BELGIUM";
    private static final String COUNTRY_CZECH_REPUBLIC = "CZECH REPUBLIC";
    private static final String COUNTRY_SWITZERLAND = "Switzerland";
    private static final String COUNTRY_JAPAN = "JAPAN";
    private static final String COUNTRY_CANADA = "CANADA";
    private static final String COUNTRY_SLOVAKIA = "SLOVAKIA";
    private static final String COUNTRY_AUSTRIA = "AUSTRIA";
    private static final String COUNTRY_SOUTH_KOREA = "SOUTH KOREA";
    private static final String COUNTRY_SPAIN = "SPAIN";
    private static final String COUNTRY_FRANCE = "FRANCE";
    private static final String COUNTRY_DENMARK = "DENMARK";
    private static final String COUNTRY_CHINA = "CHINA";


    public String getName() {
        String name = getString("name");
        if (!TextUtils.isEmpty(name)) {
            Context context = App.getAppContext();
            getTranslatedCountry(context, name);
        }
        return name;
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




    private static String getTranslatedCountry(Context context, String country) {
        String countryName;
        if (country.equalsIgnoreCase(COUNTRY_AUSTRIA)) {
            countryName = context.getString(R.string.country_austria);
        } else if (country.equalsIgnoreCase(COUNTRY_BELGIUM)) {
            countryName = context.getString(R.string.country_belgium);
        } else if (country.equalsIgnoreCase(COUNTRY_CANADA)) {
            countryName = context.getString(R.string.country_canada);
        } else if (country.equalsIgnoreCase(COUNTRY_CZECH_REPUBLIC)) {
            countryName = context.getString(R.string.country_czech_republic);
        } else if (country.equalsIgnoreCase(COUNTRY_CHINA)) {
            countryName = context.getString(R.string.country_china);
        } else if (country.equalsIgnoreCase(COUNTRY_DENMARK)) {
            countryName = context.getString(R.string.country_denmark);
        } else if (country.equalsIgnoreCase(COUNTRY_FRANCE)) {
            countryName = context.getString(R.string.country_france);
        } else if (country.equalsIgnoreCase(COUNTRY_JAPAN)) {
            countryName = context.getString(R.string.country_japan);
        } else if (country.equalsIgnoreCase(COUNTRY_SLOVAKIA)) {
            countryName = context.getString(R.string.country_slovakia);
        } else if (country.equalsIgnoreCase(COUNTRY_SOUTH_KOREA)) {
            countryName = context.getString(R.string.country_south_korea);
        } else if (country.equalsIgnoreCase(COUNTRY_SPAIN)) {
            countryName = context.getString(R.string.country_spain);
        } else if (country.equalsIgnoreCase(COUNTRY_SWITZERLAND)) {
            countryName = context.getString(R.string.country_switzerland);
        } else {
            countryName = country;
        }
        return countryName;
    }

    public static class NameComparator implements Comparator<Team> {
        @Override
        public int compare(Team team1, Team team2) {
            String team1Name = getTranslatedCountry(App.getAppContext(), team1.getName());
            String team2Name = getTranslatedCountry(App.getAppContext(), team2.getName());
            return team1Name.compareTo(team2Name);
        }
    }
}
