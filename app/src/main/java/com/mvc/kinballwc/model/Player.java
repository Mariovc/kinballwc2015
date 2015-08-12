package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 27/6/15
 * Email: m3ario@gmail.com
 */
@ParseClassName("Player")
public class Player extends ParseObject {

    public String getName(){
        return getString("name");
    }

    public String getNationality(){
        return getString("nationality");
    }

    public String getImage(){
        return getString("image");
    }

    public Date getBirthdate(){
        return getDate("birthdate");
    }

    public List<Role> getRoles(){
        return getList("roles");
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

    public void setRoles(List<Role> roles) {
        put("roles", roles);
    }

}
