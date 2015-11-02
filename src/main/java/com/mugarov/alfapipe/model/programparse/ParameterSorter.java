/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse;

import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;
import com.mugarov.alfapipe.model.programparse.datatypes.Parseable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Mark
 */
public class ParameterSorter implements Comparator<ParameterField> {
    
    private final Parseable program;
    
    public ParameterSorter(Parseable program){
        this.program = program;
    }
    
    public void sort(){
        ArrayList<ParameterField> parameters = this.program.getParameters();
        if(!parameters.isEmpty()){
             Collections.sort(parameters, this);
            this.program.setParameters(parameters);
//            System.out.println("Sorted to:");
//            for(ParameterField p:parameters){
//                System.out.println("\t"+ p.getName());
//            }
        }
//        else{
//            System.out.println("No Parameters.");
//        }
//       
    }
    
    

    @Override
    public int compare(ParameterField p1, ParameterField p2) {
        int value1 = p1.getPosition();
        int value2 = p2.getPosition();
        if(value1<0){
            value1 = value1+this.program.getParameterListSize();
        }
        if(value2<0){
            value2 = value2+this.program.getParameterListSize();
        }  
        return value1-value2;
        
    }
    
    

    
    
    
}
