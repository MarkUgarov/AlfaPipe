/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mugarov.alfapipe.model.datatypes.Executable;
import com.mugarov.alfapipe.model.Pool;

/**
 *
 * @author Mark
 */
public class ParameterField{
    private String name;
    private String command;
    private String defaultValue;
    private int position;
    private boolean optional;
    
    public ParameterField(){
        this.name= null;
        this.command= null;
        this.defaultValue = null;
        this.position = 0;
        this.optional = true;
    }
    
    /**
     * 
     * @param name should not be null 
     * @param command can be null if there is no command (e.g. only the position
     * matters)
     * @param defaultValue can be Pool.PROGRAM_EMPTY_PARAMETER_VALUE if no  
     * values are allowed or null if there is just no default value
     * @param pos should be 0 if it does not matter, positive +X if it should be
     * on a position X counted from the head of the array or negative -X if 
     * should be on a position X counted from the tail of the array on the
     * command line
     * (the startCommand is only on position in the commandline)
     * @param optional if the parameter is optional 
     */
    public ParameterField(String name, String command, String defaultValue, int pos, boolean optional){
        this.name = name;
        this.command = command;
        this.defaultValue = defaultValue;
        this.position = pos;
        this.optional = optional;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * 
     * @return the default value or Pool.PROGRAM_EMPTY_PARAMETER_VALUE (should 
     * be "/empty") if this should never have a value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 
     * @param defaultValue should be Pool.PROGRAM_EMPTY_PARAMETER_VALUE (should 
     * be "/empty") if this should never have a value
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    public boolean isOptional(){
        return this.optional;
    }
    
    public void setOptional(boolean optional){
        this.optional = optional;
    }
    

}
