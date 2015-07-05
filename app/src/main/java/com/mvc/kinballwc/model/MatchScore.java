package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by mario on 8/6/15.
 */
@ParseClassName("Score")
public class MatchScore extends ParseObject {
    public int getPinkScore(){
        return getInt("pinkScore");
    }

    public int getGreyScore(){
        return getInt("greyScore");
    }

    public int getBlackScore(){
        return getInt("blackScore");
    }

    public void setPinkScore(int score){
        put("pinkScore", score);
    }

    public void setGreyScore(int score){
        put("greyScore", score);
    }

    public void setBlackScore(int score){
        put("blackScore", score);
    }
}
