/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.datatypes;

import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class ParseableProgramList {
    
    private int index;
    private String name;
    private ArrayList<ParseableProgram> programs;

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
     /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the programs
     */
    public ArrayList<ParseableProgram> getPrograms() {
        return programs;
    }

    /**
     * @param parameters the programs to set
     */
    public void setPrograms(ArrayList<ParseableProgram> parameters) {
        this.programs = parameters;
    }

   
    
}
