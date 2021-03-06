/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse;

import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Mark
 */
public class ParameterSorter implements Comparator<ParameterField> {
    
    private final ParseableProgram program;
    
    public ParameterSorter(ParseableProgram program){
        this.program = program;
    }
    
    public void sort(){
        ArrayList<ParameterField> parameters = this.program.getParameters();
        ArrayList<ParameterField> clusterParameters = this.program.getAdditionalClusterParameters();
        if(!parameters.isEmpty()){
             Collections.sort(parameters, this);
            this.program.setParameters(parameters);
        }
        if(!clusterParameters.isEmpty()){
            Collections.sort(clusterParameters, this);
            this.program.setAdditionalClusterParameters(clusterParameters);
        }
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
