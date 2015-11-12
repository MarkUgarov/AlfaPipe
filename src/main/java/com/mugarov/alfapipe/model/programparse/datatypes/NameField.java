/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.datatypes;

/**
 *
 * @author mugarov
 */
public class NameField {
    
    private boolean dynamic;
    private String essentialFor;
    // for non-dynamic
    private String name;
    // for dynamic
    private String regex;
    private String prefix;
    private String postfix;
    private int lowerbound;
    private int upperbound;
    
    public NameField(){
        this.dynamic = false;
    }

    /**
     * Returns if this is dynamic.
     * Choose a non-dynamic name if you want to select a specific file from the
     * output as essential for the following process. All other files will be 
     * ignored by the chosen program described in essentialFor (or for all if 
     * you set essentialFor null).
     * Choose a dynamic name, if the output can not be set directly but depends
     * on the input, if you choose 
     * prefix = "ABC_", postfix = "_XYZ", lowerBound = "0", upperBound="-1",
     * regex = "."and the input has name "example.23", the dynamic name should 
     * be build to "ABC_example_XYZ". 
     * The dynamic name is build in the ProgramParameterSet.
     * @return if dynamic
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * If it's not a fixed string, you can set this dynamic.
     * Choose a non-dynamic name if you want to select a specific file from the
     * output as essential for the following process. All other files will be 
     * ignored by the chosen program described in essentialFor (or for all if 
     * you set essentialFor null).
     * Choose a dynamic name, if the output can not be set directly but depends
     * on the input, if you choose 
     * prefix = "ABC_", postfix = "_XYZ", lowerBound = "0", upperBound="-1",
     * regex = "."and the input has name "example.23", the dynamic name should 
     * be build to "ABC_example_XYZ". 
     * The dynamic name is build in the ProgramParameterSet.
     * @param dynamic the dynamic to set
     */
    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
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
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the postfix
     */
    public String getPostfix() {
        return postfix;
    }

    /**
     * @param postfix the postfix to set
     */
    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    /**
     * @return the lowerbound
     */
    public int getLowerbound() {
        return lowerbound;
    }

    /**
     * @param lowerbound the lowerbound to set
     */
    public void setLowerbound(int lowerbound) {
        this.lowerbound = lowerbound;
    }

    /**
     * @return the upperbound
     */
    public int getUpperbound() {
        return upperbound;
    }

    /**
     * @param upperbound the upperbound to set
     */
    public void setUpperbound(int upperbound) {
        this.upperbound = upperbound;
    }

    /**
     * @return the essentialFor
     */
    public String getEssentialFor() {
        return essentialFor;
    }

    /**
     * @param essentialFor the essentialFor to set
     */
    public void setEssentialFor(String essentialFor) {
        this.essentialFor = essentialFor;
    }

    /**
     * @return the regex
     */
    public String getRegex() {
        return regex;
    }

    /**
     * @param regex the regex to set
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }
    
}
