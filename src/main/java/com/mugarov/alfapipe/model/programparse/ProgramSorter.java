/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse;

import com.mugarov.alfapipe.model.programparse.generators.ExtendedCore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author mugarov
 */
public class ProgramSorter implements Comparator<ExtendedCore>{
    private final ArrayList<ExtendedCore> programs;
    
    public ProgramSorter(ArrayList<ExtendedCore> programs){
        this.programs = programs;
    }
    
    /**
     * Sorts a List of ExtendedCores (can contain negativ values) after their
     * indices
     * @return the sorted cores
     */
    public ArrayList<ExtendedCore> sort(){
        if(programs != null && !programs.isEmpty()){
            Collections.sort(this.programs, this);
        }
        return this.programs;
    }
    
    

    @Override
    public int compare(ExtendedCore p1, ExtendedCore p2) {
        int value1 = p1.getList().getIndex();
        int value2 = p2.getList().getIndex();
        if(value1<0){
            value1 = value1+this.programs.size();
        }
        if(value2<0){
            value2 = value2+this.programs.size();
        }  
        return value1-value2;
        
    }
}
