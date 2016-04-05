/**
 * An InputParameter is nearly the same as an ParameterField, but instead
 * the value can be changed at runtime as well as it "translates" some values
 * (like Pool.PROGRAM_EMPTY_PARAMETER_VALUE) so it's easier to decide if 
 * the parameter should be boolean or optional. 
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;

/**
 *
 * @author Mark
 */
public class InputParameter {
    
    private final String name;
    private String value;
    private boolean shown;
    private final boolean isBoolean;
    private boolean booleanValue;
    private final boolean isOptional;
    private String toolTip;
    private final boolean avoidLeadingSpace;
    
    public InputParameter(ParameterField field, boolean shown){
        this.name = field.getName();
        this.value = field.getDefaultValue();
        if(this.value != null &&this.value.equals(ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE)){
            this.isBoolean = true;
        }
        else{
            this.isBoolean = false;
            this.booleanValue = false;
        }
        this.booleanValue = true;
        this.isOptional = field.isOptional();
        this.shown = shown;
        this.toolTip = field.getToolTip();
        this.avoidLeadingSpace = field.isAvoidLeadingSpace();
    }

    public InputParameter(String name, String value, boolean isBoolean, boolean booleanValue, boolean optional, boolean shown, boolean avoidLeadingSpace) {
        this.name = name;
        this.value = value;
        this.isBoolean = isBoolean;
        this.booleanValue = (this.isBoolean && booleanValue);
        this.isOptional = optional;
        this.shown = shown;
        this.toolTip = ParameterPool.TOOLTIP_PARAMETER_DEFAULT;
        this.avoidLeadingSpace = avoidLeadingSpace;
    }
    
    
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if(!this.isBoolean){
            this.value = value;
//            System.out.println("Value of "+this.name+" is now "+this.value);
        }
        else{
            System.err.println("You tried to change a boolean value to "+value);
        }
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }
    
    public boolean isBoolean(){
        return this.isBoolean;
    }
    
    public void setBoolean(boolean bool){
        if(this.isOptional){
            this.booleanValue = bool; 
        }
        else{
            this.booleanValue = true;
        }
//        System.out.println(this.booleanValue? this.name +" is now set selected" : this.name+" is now set unselected");
        
    }
    
    public boolean getBoolean(){
        return this.booleanValue;
    }
    
    public boolean isOptional(){
        return this.isOptional;
    }

    /**
     * @return the avoidLeadingSpace
     */
    public boolean isAvoidLeadingSpace() {
        return avoidLeadingSpace;
    }

    /**
     * @return the toolTip
     */
    public String getToolTip() {
        return toolTip;
    }

    /**
     * @param toolTip the toolTip to set
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
}
