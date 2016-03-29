/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.filetools;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import java.io.File;

/**
 *
 * @author mugarov
 * This class is somehow "simulated static". 
 */
public abstract class FileNaming {
    

    
    /**
     * Removes the extension and most of the special characters from a filename.
     * @param file can be any file with a non-empty name
     * @return the name of the file without extensions and most of the 
     * special characters,
     * returns "No_valid_name" if the filename could not be cleared
     */
    public static String getClearName(File file){
        return FileNaming.getClearName(file.getName());
    }
    
    /**
     * Removes the extension and most of the special characters from a String.
     * @param name can be any non-empty String
     * @return the name of the file without extensions and most of the 
     * special characters,
     * returns "No_valid_name" if the String could not be cleared
     */
    public static String getClearName(String name){
        String[] splitname = name.split("\\.",2);
        if(splitname.length== 0){
            return "No_valid_name";
        }
        else{
            String spln = splitname[0];
            for(String reg:ParameterPool.REPLACE_REGEX){
                spln = spln.replaceAll(reg, ParameterPool.REPLACE_REPLACEMENT);
            }
            return spln;
        }
    }
    
    /**
     * Returns the dynamic name of a file.
     * @param field should be the NameField (out of ParseableProgram) - should
     * not be null
     * @param originalFile should be the original file which will be cut and 
     * complemented
     * @return the processed filename
     */
    public static String getDynamicNameOf(NameField field, File originalFile){
        String[] splitname = originalFile.getName().split(field.getRegex());
        StringBuilder newName = new StringBuilder();
        int low = field.getLowerbound();
        int up;
        if(field.getUpperbound()>0){
            up = field.getUpperbound();
        }
        else{
            up = splitname.length-field.getUpperbound();
        }
        
        if(field.getPrefix() != null){
            newName.append(field.getPrefix());
        }
        for(int i= low; i<up; i++){
            newName.append(splitname[i]);
        }
        if(field.getPostfix() != null){
            newName.append(field.getPostfix());
        }
        return newName.toString();
    }
    
    public static String getName(NameField field){
        return null;
    }
}
