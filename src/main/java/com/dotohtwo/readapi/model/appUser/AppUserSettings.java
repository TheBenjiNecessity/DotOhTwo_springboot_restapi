package com.dotohtwo.readapi.model.appUser;
 
import java.io.Serializable;

/**
 * Translate to the json:
 * {
 *      "test": "string"
 * }
 */
public class AppUserSettings implements Serializable {
    
    public String test;
    
}

// TODO what if the passed json does not follow this format? Validation?