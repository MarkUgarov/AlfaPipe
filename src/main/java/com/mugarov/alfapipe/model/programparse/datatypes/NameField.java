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
    private boolean useAll;
    private boolean useOnly;
    // for non-dynamic
    private String fileName;
    // for dynamic
    private String regex;
    private String prefix;
    private String postfix;
    private int lowerbound;
    private int upperbound;
    
    /**
     * Example: a static output is named "test" for an application "none",
     * no other output will be used for "none".
     */
    public NameField(){
        this.fileName = null;
        this.essentialFor = null;
        this.dynamic = false;
        this.useAll = false;
        this.useOnly = false;
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
    public String getFileName() {
        return fileName;
    }

    /**
     * @param name the name to set
     */
    public void setFileName(String name) {
        this.fileName = name;
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
     *  for example: 
     * "file_name_1.exe " with regex "_" and lowerbound "1" will lead to 
     * "name_1.exe"
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
     * for example: 
     * "file_name_1.exe " with regex "_" and upperbound "-1" or "1" will lead to 
     * "file_name"
     */
    public void setUpperbound(int upperbound) {
        this.upperbound = upperbound;
    }

    /**
     * @return the Name of the Program this output is essential for
     */
    public String getEssentialFor() {
        return essentialFor;
    }

    /**
     * @param essentialFor the Name of the Program this output is essential for
     */
    public void setEssentialFor(String essentialFor) {
        this.essentialFor = essentialFor;
    }

    /**
     * @return the regex = where to distinguish the positions (should be
     * anything like "_" or "-" for most cases, but also can be an 
     * empty String "" )
     */
    public String getRegex() {
        return regex;
    }

    /**
     * @param regex the regex to set = where to distinguish the positions 
     * (should be anything like "_" or "-" for most cases , but also can be an 
     * empty String "" )
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * @return the useAll can be used to "sneak" outputs around the pipe for tools
     * example
     *    |Preprocessor|
     *          |
     * (preprocessing output)
     *          |
     *      |Processor|
     *          |
     *  (processing output)
     *          |
     *      |Assmbler|
     *          |
     *   (assembler output) --------|----------------|
     *          |                   |                |
     *    |ReadsVsContigs|          |                |
     *          |                   |        (useOnly && useAll)
     *(readsVsContigs output)       |                |
     *          |               (useAll)             | 
     *       |Prodigal|             |                |
     *          |                   |                |
     *  (prodigal output)-----------+                |
     *       ___|___                |                |
     *      /       \               |                |
     *  Tool1       Tool2         Tool3             Tool4
     *      
     */
    public boolean isUseAll() {
        return useAll;
    }

    /**
     * @param useAll can be used to "sneak" outputs around the pipe for tools
     * example
     *    |Preprocessor|
     *          |
     * (preprocessing output)
     *          |
     *      |Processor|
     *          |
     *  (processing output)
     *          |
     *      |Assmbler|
     *          |
     *   (assembler output) --------|----------------|
     *          |                   |                |
     *    |ReadsVsContigs|          |                |
     *          |                   |        (useOnly && useAll)
     *(readsVsContigs output)       |                |
     *          |               (useAll)             | 
     *       |Prodigal|             |                |
     *          |                   |                |
     *  (prodigal output)-----------+                |
     *       ___|___                |                |
     *      /       \               |                |
     *  Tool1       Tool2         Tool3             Tool4
     *      
     */
    public void setUseAll(boolean useAll) {
        this.useAll = useAll;
    }

    /**
     * @return can be used to "sneak" outputs around the pipe for tools
     * example
     *    |Preprocessor|
     *          |
     * (preprocessing output)
     *          |
     *      |Processor|
     *          |
     *  (processing output)
     *          |
     *      |Assmbler|
     *          |
     *   (assembler output) --------|----------------|
     *          |                   |                |
     *    |ReadsVsContigs|          |                |
     *          |                   |        (useOnly && useAll)
     *(readsVsContigs output)       |                |
     *          |               (useAll)             | 
     *       |Prodigal|             |                |
     *          |                   |                |
     *  (prodigal output)-----------+                |
     *       ___|___                |                |
     *      /       \               |                |
     *  Tool1       Tool2         Tool3             Tool4
     *      
     */
    public boolean isUseOnly() {
        return useOnly;
    }

    /**
     * @param useOnly can be used to "sneak" outputs around the pipe for tools
     * example
     *    |Preprocessor|
     *          |
     * (preprocessing output)
     *          |
     *      |Processor|
     *          |
     *  (processing output)
     *          |
     *      |Assmbler|
     *          |
     *   (assembler output) --------|----------------|
     *          |                   |                |
     *    |ReadsVsContigs|          |                |
     *          |                   |        (useOnly && useAll)
     *(readsVsContigs output)       |                |
     *          |               (useAll)             | 
     *      |Prodigal|              |                |
     *          |                   |                |
     *  (prodigal output)-----------+                |
     *       ___|___                |                |
     *      /       \               |                |
     *  Tool1       Tool2         Tool3             Tool4
     *      
     */
    public void setUseOnly(boolean useOnly) {
        this.useOnly = useOnly;
    }
    
}
