package com.mvc.kinballwc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Author: Mario Velasco Casquero
 * Date: 05/08/2015
 * Email: m3ario@gmail.com
 */
@ParseClassName("Role")
public class Role extends ParseObject {

    public String getName(){
        return getString("name");
    }

    public void setName(String name){
        put("name", name);
    }


}
