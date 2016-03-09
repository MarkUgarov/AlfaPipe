/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class ProgramSetList {
    
    private int index;
    private String name;
    private ArrayList<ProgramSet> programSets;
    private boolean empty;
    
    /**
     * Creates a new ProgramSetList. 
     * @param programList can be null, but this will cause the index to be -1
     */
    public ProgramSetList(ParseableProgramList programList){
        if(programList != null){
            this.empty = false;
            this.programSets = new ArrayList<>(programList.getPrograms().size());
            for(ParseableProgram prog:programList.getPrograms()){
                this.programSets.add(new ProgramSet(prog));
            }
            if(this.programSets.isEmpty()){
                this.empty = true;
            }
            this.index = programList.getIndex();
            this.name = programList.getName();
        }
        else{
            this.empty = true;
            this.index = -1;
        }
        
        
    }

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
     * @return the programSets
     */
    public ArrayList<ProgramSet> getProgramSets() {
        return programSets;
    }

    /**
     * @param parameterSets the programSets to set
     */
    public void setProgramSets(ArrayList<ProgramSet> parameterSets) {
        this.programSets = parameterSets;
    }

    /**
     * @return the isEmpty
     */
    public boolean isEmpty() {
        return empty;
    }


    
}
