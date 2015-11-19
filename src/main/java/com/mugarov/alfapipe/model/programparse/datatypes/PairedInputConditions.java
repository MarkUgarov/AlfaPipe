/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author mugarov
 */
public class PairedInputConditions {
    
    private boolean usedPaired;
    private String regex;
    private int upperIndex;
    
    public PairedInputConditions(){
        this.usedPaired = false;
        this.regex = "_";
        this.upperIndex = 0;
    }
    
    @JsonIgnore 
    public PairedInputConditions(boolean use, String regex, int index){
        this.usedPaired = use;
        this.regex = regex;
        this.upperIndex = index;
    }

    /**
     * @return the usedPaired
     */
    public boolean isUsedPaired() {
        return usedPaired;
    }

    /**
     * @param usedPaired should be true if the program needs all files paired 
     * in its parameters
     * or false if there are files paired, but e.g. the program does only need 
     * one file in its parameters to find the others by itself
     */
    public void setUsedPaired(boolean usedPaired) {
        this.usedPaired = usedPaired;
    }

    /**
     * @return the regex
     */
    public String getRegex() {
        return regex;
    }

    /**
     * @param regex the regex to set
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * @return the upperIndex
     */
    public int getUpperIndex() {
        return upperIndex;
    }

    /**
     * @param upperIndex the upperIndex to set
     */
    public void setUpperIndex(int upperIndex) {
        this.upperIndex = upperIndex;
    }
    
}
