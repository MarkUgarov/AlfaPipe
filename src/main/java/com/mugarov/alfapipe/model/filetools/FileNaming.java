/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.filetools;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
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
    
    
    
    public static String getClearName(String name, ProgramSet progSet){
        String fail = "No_valid_name";
        String regex;
        int distinguishPosition;
        if(progSet == null || progSet.getParsedParameters().getPairedConditions() == null){
            return fail;
        }
        else{
            regex = progSet.getParsedParameters().getPairedConditions().getRegex();
            distinguishPosition = progSet.getParsedParameters().getPairedConditions().getUpperIndex();
        }
        // cutting endings
        String[]  splitName1 = name.split("\\.",2);
        if(splitName1.length<1){
            return fail;
        }
        // split the rest
        String cut = splitName1[0];       
        splitName1 = cut.split(regex);
        int pos1;
        if(distinguishPosition<0){
            pos1 = (splitName1.length)+distinguishPosition;
        }
        else{
            pos1 = distinguishPosition;
        }
        if(splitName1.length < pos1 || pos1<0){
            return fail;
        }
        StringBuilder clear = new StringBuilder();
        boolean should = true;
        for(int i=0; i<pos1; i++){
            clear.append(splitName1[i]);
            if(i<pos1-1){
                clear.append(ParameterPool.REPLACE_REPLACEMENT);
            }    
        }
        String clearName = clear.toString();
        //System.out.println("NAME: "+clearName);
        for(String reg:ParameterPool.REPLACE_REGEX){
            clearName = clearName.replaceAll(reg, ParameterPool.REPLACE_REPLACEMENT);
        }
        return clearName;
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
    
    public static String getEnding(File file){
        return FileNaming.getEnding(file.getName());
    }
    
    public static String getEnding(String fileName){
        if(fileName == null){
            return null;
        }
        String[] split = fileName.split("\\.", 2);
        if(split.length<2){
            System.out.println("Could not split "+fileName);
            return "";
        }
        else{
            return split[1];
        }
        
    }
    

}
