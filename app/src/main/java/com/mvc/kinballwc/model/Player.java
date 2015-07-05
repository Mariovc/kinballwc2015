package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by mario on 27/6/15.
 */
@ParseClassName("Player")
public class Player extends ParseObject {

    public String getName(){
        return getString("name");
    }

    public String getSurname(){
        return getString("surname");
    }

    public String getNationality(){
        return getString("nationality");
    }

    public void setName(String name){
        put("name", name);
    }

    public void setSurname(String surname){
        put("surname", surname);
    }

    public void setNationality(String nationality){
        put("nationality", nationality);
    }

}
