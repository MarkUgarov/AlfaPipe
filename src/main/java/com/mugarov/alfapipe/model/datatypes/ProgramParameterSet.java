/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.datatypes.Parseable;
import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ProgramParameterSet {
    
    private final String name;
    
    private final Parseable program;
    private final ArrayList<InputParameter> parameters;
    private boolean isSelected;
    
    public ProgramParameterSet(Parseable program){
        this.program = program;
        this.name = this.program.getName();
        this.isSelected = (program.getStartCommand() != null);
        this.parameters = new ArrayList<>();
        
        for(ParameterField pF: program.getParameters()){
            boolean shown = !(pF.getName().equals(Pool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME)||pF.getName().equals(Pool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME));
            this.parameters.add(new InputParameter(pF, shown));
        }
    }
    
    public ProgramParameterSet(){
        this.program = null;
        this.name = null;
        this.isSelected = false;
        this.parameters = new ArrayList<>();
    }
    
    public void select(){
        this.isSelected = true;
    }
    public void unselect(){
        this.isSelected = false;
    }
    
    public boolean isSelected(){
        return this.isSelected;
    }
    
    public String getName(){
        return this.name;
    }
    
    public InputParameter getParameter(int i){
        return this.parameters.get(i);
    }
    
    
    public ArrayList<InputParameter> getInputParameters(){
        return this.parameters;
    }
    
    public Parseable getParsedParameters(){
        return this.program;
    }
    
}
