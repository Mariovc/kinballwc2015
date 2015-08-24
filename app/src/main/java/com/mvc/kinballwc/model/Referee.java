package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Author: Mario Velasco Casquero
 * Date: 27/6/15
 * Email: m3ario@gmail.com
 */
@ParseClassName("Referee")
public class Referee extends ParseObject {

    public String getName(){
        return getString("name");
    }

    public String getNationality(){
        return getString("nationality");
    }

    public String getImage(){
        return getString("image");
    }

    public void setName(String name){
        put("name", name);
    }

    public void setNationality(String nationality){
        put("nationality", nationality);
    }

    public void setImage(String image){
        put("image", image);
    }

}
