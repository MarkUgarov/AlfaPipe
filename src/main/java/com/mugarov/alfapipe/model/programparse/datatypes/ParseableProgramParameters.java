/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.ParameterSorter;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ParseableProgramParameters{
    
    private String name;
    private String startCommand;
    private String[] validEndings;
    private String[] outputEndings;
    private boolean onlyOutputDirectorySetable;
    private String enterCommand;
    private String exitCommand;
    
    private ArrayList<ParameterField> parameters;
    private ParameterField inputPathCommand;
    private ParameterField outputPathCommand;
   
    private final ParameterSorter sorter;
    
    
    public ParseableProgramParameters(){
        this.parameters = new ArrayList<>();
        this.name = null;
        this.startCommand = null;
        this.inputPathCommand = null;
        this.outputPathCommand = null;
        this.validEndings = null;
        this.outputEndings = null;
        this.enterCommand = null;
        this.exitCommand = null;
        this.onlyOutputDirectorySetable = false;
        this.sorter = new ParameterSorter(this);
    }
    
    public ParseableProgramParameters( String name, 
                                String startCommand, 
                                String inputPathCommand,
                                int inputPathPosition,
                                String outputPathCommand,
                                int outputPathPosition,
                                String[] validEndings,
                                String[] outputEnding
                                ){
        this.parameters = new ArrayList<>();
        this.name = name;
        this.startCommand = startCommand;
        this.setInputPathCommand(inputPathCommand, inputPathPosition);
        this.setOuputPathCommand(outputPathCommand, outputPathPosition);
        this.validEndings = validEndings;
        this.outputEndings = outputEnding;
        this.onlyOutputDirectorySetable = false;
        
        this.enterCommand = null;
        this.exitCommand = null;
        this.sorter = new ParameterSorter(this);
        this.sorter.sort();
    }
    
    public String getName() {
       return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setEnterCommand(String commandLine) {
        this.enterCommand = commandLine;
    }

    public String getEnterCommand() {
        return this.enterCommand;
    }
    
    public void setExitCommand(String commandLine) {
        this.exitCommand = commandLine;
    }

    public String getExitCommand() {
        return this.exitCommand;
    }
    
    public String getStartCommand() {
        return this.startCommand;
    }

    public void setStartCommand(String startCommand) {
        this.startCommand = startCommand;
    }

    public String[] getValidInputEndings() {
        return this.validEndings;
    }

    public void setValidInputEndings(String[] validEndings) {
        this.validEndings = validEndings;
    }

    public String[] getOutputEndings() {
        return this.outputEndings;
    }

    public void setOutputEndings(String outputEndings[]) {
        this.outputEndings = outputEndings;
    }
    
    public void setParameters(ArrayList<ParameterField> parameters) {
        this.parameters = parameters;
        for (ParameterField p:this.parameters){
            if(p.getName().equals(Pool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME)){
                this.inputPathCommand = p;
            }
            else if(p.getName().equals(Pool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME)){
                this.outputPathCommand = p;
            }
        }
    }

    public ArrayList<ParameterField> getParameters() {
        return this.parameters;
    }

    @JsonIgnore
    public ParameterField getInputPathCommand() {
        return this.inputPathCommand;
    }

    @JsonIgnore
    public void setInputPathCommand(ParameterField inputPathCommand) {
        this.inputPathCommand = inputPathCommand;
        this.addParameter(inputPathCommand);
    }
    
    @JsonIgnore
    public void setInputPathCommand(String command, int position){
        this.setInputPathCommand(new ParameterField(
                                    Pool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME, 
                                    command,
                                    null,
                                    position,
                                    false
                                 )
        );
    }

    public ParameterField getOutputPathCommand() {
        return this.outputPathCommand;
    }

    public void setOutputPathCommand(ParameterField outputPathCommand) {
        this.outputPathCommand = outputPathCommand;
        this.addParameter(this.outputPathCommand);
    }
    
    @JsonIgnore
    public void setOuputPathCommand(String command, int position){
         this.setOutputPathCommand(new ParameterField(
                                    Pool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME, 
                                    command,
                                    null,
                                    position,
                                    false
                                 )
        );
    }

    @JsonIgnore
    public int getParameterListSize() {
        return this.parameters.size();
    }

    /**
     * Add a parameter
     * @param name the shown label 
     * @param command the executed command
     * @param defaultValue can be empty or Pool.PROGRAM_EMPTY_PARAMETER_VALUE if you want a boolean
     * @param position can be positive (count 0->position-> end ) or negative (count 0<-position<-X)
     * @param optional if this parameter does not need a value but it can be set
     */
    @JsonIgnore
    public void addParameter(String name, String command, String defaultValue, int position, boolean optional) {
        int i= 0; 
        while(i<this.parameters.size()){
            if(this.parameters.get(i).getName().equals(name)){
                this.parameters.remove(i);
            }
            i++;
        }
        this.parameters.add(new ParameterField(name, command, defaultValue, position, optional));
    }

    @JsonIgnore
    public void addParameter(ParameterField parameter) {
        int i= 0;
        while(i<this.parameters.size()){
            if(this.parameters.get(i).getName().equals(parameter.getName())){
                this.parameters.remove(i);
            }
            i++;
        }
        this.parameters.add(parameter);
    }

    public void sortParameters() {
            this.sorter.sort();
    }
    
    public boolean isOnlyOutputDirectorySetable(){
        return this.onlyOutputDirectorySetable;
    }
    
    public void setOnlyOutputDirectorySetable(boolean setable){
        this.onlyOutputDirectorySetable = setable;
    }
}
