/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ProgramSet {
    
    private final String name;
    
    private final ParseableProgram program;
    private final ArrayList<InputParameter> parameters;
    private final ArrayList<InputParameter> additionalClusterParameters;
    private final ArrayList<InputParameter> localPrependParamters;
    private boolean isSelected;
    private boolean isEmpty;
    
    public ProgramSet(ParseableProgram program){
        if(program != null){
            this.program = program;
            this.name = this.program.getName();
            this.isSelected = (program.getStartCommand() != null);
            this.parameters = new ArrayList<>();
            this.additionalClusterParameters = new ArrayList<>();
            this.localPrependParamters = new ArrayList<>();
            this.isEmpty = false;

            boolean shown = true;
            for(ParameterField pF: program.getParameters()){
                if(pF == null){
                    System.err.println("Program Parameter was null in "+this.name);
                }
                else{
                    shown = !(pF.getName().equals(ParameterPool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME)||pF.getName().equals(ParameterPool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME)||pF.isHidden());
                    this.parameters.add(new InputParameter(pF, shown));
                }
                
            }
            for(ParameterField pF: program.getAdditionalClusterParameters()){
                if(pF == null){
                    System.err.println("Cluster Parameter was null in "+this.name);
                }
                else{
                    shown = !(pF.getName().equals(ParameterPool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME)||pF.getName().equals(ParameterPool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME)||pF.isHidden());
                    this.additionalClusterParameters.add(new InputParameter(pF, shown));
                }
            }
            for(ParameterField pF: program.getLocalPrependParameters()){
                if(pF == null){
                    System.err.println("Local Prepend Parameter was null in "+this.name);
                }
                else{
                    shown = !(pF.getName().equals(ParameterPool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME)||pF.getName().equals(ParameterPool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME)||pF.isHidden());
                    this.localPrependParamters.add(new InputParameter(pF, shown));
                }
            }
        }
        else{
            this.program = null;
            this.name = null;
            this.isSelected = false;
            this.isEmpty = true;
            this.parameters = new ArrayList<>();
            this.additionalClusterParameters = new ArrayList<>();
            this.localPrependParamters = new ArrayList<>();
        }
    }
    
    public ProgramSet(){
        this.program = null;
        this.name = null;
        this.isSelected = false;
        this.parameters = new ArrayList<>();
        this.additionalClusterParameters = new ArrayList<>();
        this.localPrependParamters = new ArrayList<>();
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
    
    public ArrayList<InputParameter> getClusterParameters(){
        return this.additionalClusterParameters;
    }
    
    public ArrayList<InputParameter> getLocalPrependParamters(){
        return this.localPrependParamters;
    }
    
    public ParseableProgram getParsedParameters(){
        return this.program;
    }
    
    public boolean specificOutputDefinedFor(String followingName){
        for(NameField field:this.program.getEssentialOutputs()){
            if(field.getFileName().equals(followingName)){
                return true;
            }
        }
        return false;
    }
    
    public boolean isEmpty(){
        return this.isEmpty;
    }
    
}
