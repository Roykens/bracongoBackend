package com.royken.bracongo.survey.service.util;

import com.royken.bracongo.survey.entities.TypeEtat;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ConvertorUtil {
    
    public static TypeEtat getFromString(String etat){
        if(etat.equalsIgnoreCase("M")){
            return TypeEtat.M;
        }
        if(etat.equalsIgnoreCase("B")){
            return TypeEtat.M;
        }
        return TypeEtat.TB;
    }
    
}
