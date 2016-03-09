/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse;

import com.mugarov.alfapipe.model.datatypes.ProgramSetList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class ProgramListSorter implements Comparator<ProgramSetList>{
    
private final ArrayList<ProgramSetList> programs;
    
    public ProgramListSorter(ArrayList<ProgramSetList> programs){
        this.programs = programs;
    }
    
    public void sort(){
        if(!this.programs.isEmpty()){
             Collections.sort(programs, this);
        }
    }
    
    

    @Override
    public int compare(ProgramSetList p1, ProgramSetList p2) {
        int value1 = p1.getIndex();
        int value2 = p2.getIndex();
        if(value1<0){
            value1 = value1+this.programs.size();
        }
        if(value2<0){
            value2 = value2+this.programs.size();
        }  
        return value1-value2;
        
    }
}
