        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.ParameterSorter;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ParseableProgram{
    
    private String name;
    private String startCommand;
    private String[] validEndings;
    private String[] outputEndings;
    private OutputField outputSettings;
    private String enterCommand;
    private String exitCommand;
    
    private boolean disableCluster;
    private boolean skipWaiting;
    private boolean removeFilesAfterSetCompletion;
    private String forceWorkingDirectory;
    
    private ArrayList<ParameterField> additionalClusterParameters;
    private ArrayList<ParameterField> localPrependParameters;
    private ArrayList<ParameterField> parameters;
    private ParameterField inputPathCommand;
    private ParameterField outputPathCommand;
    private ParameterField pairedCommand;
    private PairedInputConditions pairedConditions;
    
    private ArrayList<NameField> essentialOutputs; 
   
    private ParameterSorter sorter;
   
    
    
    public ParseableProgram(){
        this.parameters = new ArrayList<>();
        this.name = null;
        this.startCommand = null;
        this.inputPathCommand = null;
        this.outputPathCommand = null;
        this.pairedCommand = null;
        this.validEndings = new String[0];
        this.outputEndings = new String[0]; 
        this.enterCommand = null;
        this.exitCommand = null;
        this.outputSettings = new OutputField();
        this.essentialOutputs = new ArrayList<>();
        this.additionalClusterParameters = new ArrayList<>();
        this.localPrependParameters = new ArrayList<>();
        this.disableCluster = false;
        this.skipWaiting = false;
        this.removeFilesAfterSetCompletion = false;
        this.forceWorkingDirectory = null;
        this.sorter = new ParameterSorter(this);
       
        
    }
    
    public ParseableProgram( String name, 
                                String startCommand, 
                                String inputPathCommand,
                                int inputPathPosition,
                                String outputPathCommand,
                                int outputPathPosition,
                                String[] validEndings,
                                String[] outputEnding
                                ){
        this();
        this.parameters = new ArrayList<>();
        this.name = name;
        this.startCommand = startCommand;
        this.setInputPathCommand(inputPathCommand, inputPathPosition);
        this.setOuputPathCommand(outputPathCommand, outputPathPosition);
        this.validEndings = validEndings;
        this.outputEndings = outputEnding;
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
            if(p.getName().equals(ParameterPool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME)){
                this.inputPathCommand = p;
            }
            else if(p.getName().equals(ParameterPool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME)){
                this.outputPathCommand = p;
            }
            else if(p.getName().equals(ParameterPool.PROGRAM_PAIRED_PARAMETER_NAME)){
                this.pairedCommand = p;
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
                                    ParameterPool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME, 
                                    command,
                                    null,
                                    position,
                                    false
                                 )
        );
    }

    @JsonIgnore
    public ParameterField getOutputPathCommand() {
        return this.outputPathCommand;
    }

    @JsonIgnore
    public void setOutputPathCommand(ParameterField outputPathCommand) {
        this.outputPathCommand = outputPathCommand;
        this.addParameter(this.outputPathCommand);
    }
    
    @JsonIgnore
    public void setOuputPathCommand(String command, int position){
         this.setOutputPathCommand(new ParameterField(
                                    ParameterPool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME, 
                                    command,
                                    null,
                                    position,
                                    false
                                 )
        );
    }
    
    @JsonIgnore
    public ParameterField getPairedCommand() {
        return this.pairedCommand;
    }
    
    @JsonIgnore
    public void setPairedCommand(ParameterField pairedCommand) {
        this.pairedCommand = pairedCommand;
        this.addParameter(this.pairedCommand);
    }
    
    @JsonIgnore
    public void setPairedCommand(String command, int position){
         this.setPairedCommand(new ParameterField(
                                    ParameterPool.PROGRAM_PAIRED_PARAMETER_NAME, 
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
     * @param defaultValue can be empty or ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE if you want a boolean
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
    
    /**
     * Add a parameter
     * @param name the shown label 
     * @param command the executed command
     * @param defaultValue can be empty or ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE if you want a boolean
     * @param position can be positive (count 0->position-> end ) or negative (count 0<-position<-X)
     * @param optional if this parameter does not need a value but it can be set
     * @param tooltip can be any String which can be a tooltip for this parameter
     */
    @JsonIgnore
    public void addParameter(String name, String command, String defaultValue, int position, boolean optional, String tooltip) {
        int i= 0; 
        while(i<this.parameters.size()){
            if(this.parameters.get(i).getName().equals(name)){
                this.parameters.remove(i);
            }
            else{
                i++;
            }
        }
        this.parameters.add(new ParameterField(name, command, defaultValue, position, optional, tooltip));
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

    @JsonIgnore
    public void sortParameters() {
            this.sorter.sort();
    }
    
    @JsonIgnore
    public void setOutputSettings(boolean isDir, boolean makeDir){
        this.outputSettings.setDirectory(isDir);
        this.outputSettings.setMakeDirectory(makeDir);
    }
    
    /**
     * @return the essentialOutputs
     */
    public ArrayList<NameField> getEssentialOutputs() {
        return essentialOutputs;
    }

    /**
     * @param essentialOutputs the essentialOutputs to set
     */
    public void setEssentialOutputs(ArrayList<NameField> essentialOutputs) {
        this.essentialOutputs = essentialOutputs;
    }
   
    /**
     * For fixed names.
     * @param name is a fixed string
     * @param targetProgram is a specific program / tool this output (which is 
     * part of the output of this program) is meant for or leave null if you 
     * want this for every program / tool following 
     */
    @JsonIgnore
    public void addEssentialOutput(String name, String targetProgram){
        NameField field = new NameField();
        field.setDynamic(false);
        field.setFileName(name);
        field.setEssentialFor(targetProgram);
        this.essentialOutputs.add(field);
    }
    
    @JsonIgnore
    public void addEssentialOutput(NameField field){
        if(this.essentialOutputs == null){
            this.essentialOutputs = new ArrayList<>();
        }
//        else{
//            for(int i =0; i<this.essentialOutputs.size();i++){
//                if(this.essentialOutputs.get(i).getEssentialFor().equals(field.getEssentialFor())){
//                    System.out.println("Remove "+this.essentialOutputs.get(i).getEssentialFor()+" because it equals "+field.getEssentialFor());
//                    this.essentialOutputs.remove(i);
//                }
//            } 
//        }
        this.essentialOutputs.add(field);
    }
    
    /**
     * For dynamic names. 
     * Choose a dynamic name, if the output can not be set directly but depends
     * on the input, if you choose 
     * prefix = "ABC_", postfix = "_XYZ", lowerBound = "0", upperBound="-1",
     * regex = "."and the input has name "example.23", the dynamic name should 
     * be build to "ABC_example_XYZ". 
     * The dynamic name is build in the ProgramParameterSet.
     * @param prefix
     * @param lowerBound
     * @param postfix
     * @param upperBound
     * @param targetProgram 
     */
    @JsonIgnore
    public void addEssentialOutput(String prefix, int lowerBound, String postfix, int upperBound, String regex, String targetProgram){
        NameField field = new NameField();
        field.setDynamic(true);
        field.setRegex(regex);
        field.setPrefix(prefix);
        field.setPostfix(postfix);
        field.setLowerbound(lowerBound);
        field.setUpperbound(upperBound);
        field.setEssentialFor(targetProgram);
        this.essentialOutputs.add(field);
    }

    /**
     * @return the pairedConditions
     */
    public PairedInputConditions getPairedConditions() {
        return pairedConditions;
    }

    /**
     * @param pairedConditions the pairedConditions to set
     */
    public void setPairedConditions(PairedInputConditions pairedConditions) {
        this.pairedConditions = pairedConditions;
    }

    /**
     * @return the outputSettings
     */
    public OutputField getOutputSettings() {
        return outputSettings;
    }

    /**
     * @param outputSettings the outputSettings to set
     */
    public void setOutputSettings(OutputField outputSettings) {
        this.outputSettings = outputSettings;
    }

    /**
     * @return the additionalClusterParameters
     */
    public ArrayList<ParameterField> getAdditionalClusterParameters() {
        return additionalClusterParameters;
    }

    /**
     * @param additionalClusterParameters the additionalClusterParameters to set
     */
    public void setAdditionalClusterParameters(ArrayList<ParameterField> additionalClusterParameters) {
        this.additionalClusterParameters = additionalClusterParameters;
    }
    
    @JsonIgnore
    public void addAdditionalClusterParameter(String name, String command, String defaultValue, int position, boolean optional, String tooltip){
        int i= 0; 
        while(i<this.additionalClusterParameters.size()){
            if(this.additionalClusterParameters.get(i).getName().equals(name)){
                this.additionalClusterParameters.remove(i);
            }
            else{
                i++;
            }
        }
        this.additionalClusterParameters.add(new ParameterField(name, command, defaultValue, position, optional, tooltip));
    }
    
    @JsonIgnore
    public void addAdditionalClusterParameter(ParameterField parameter){
        if(parameter == null){
            System.err.println("Trying to add null as additional cluster parameter.");
            return;
        }
        int i= 0; 
        while(i<this.additionalClusterParameters.size()){
            if(this.additionalClusterParameters.get(i).getName().equals(parameter.getName())){
                this.additionalClusterParameters.remove(i);
            }
            else{
                i++;
            }
        }
        this.additionalClusterParameters.add(parameter);
    }

    /**
     * @return the disableCluster
     */
    public boolean isDisableCluster() {
        return disableCluster;
    }

    /**
     * @param disableCluster the disableCluster to set
     */
    public void setDisableCluster(boolean disableCluster) {
        this.disableCluster = disableCluster;
    }

    /**
     * @return the localPrependParamter
     */
    public ArrayList<ParameterField> getLocalPrependParameters() {
        return localPrependParameters;
    }

    /**
     * @param localPrependParamter the localPrependParamter to set
     */
    public void setLocalPrependParameters(ArrayList<ParameterField> localPrependParamter) {
        this.localPrependParameters = localPrependParamter;
    }
    
    @JsonIgnore
    public void addLocalPrependParameter(String name, String command, String defaultValue, int position, boolean optional, String tooltip){
        int i= 0; 
        while(i<this.localPrependParameters.size()){
            if(this.localPrependParameters.get(i).getName().equals(name)){
                this.localPrependParameters.remove(i);
            }
            else{
                i++;
            }
        }
        this.localPrependParameters.add(new ParameterField(name, command, defaultValue, position, optional, tooltip));
    }
    
    @JsonIgnore
    public void addLocalPrependParameter(ParameterField parameter){
        if(parameter == null){
            System.err.println("Trying to add null as local prepend parameter.");
            return;
        }
        int i= 0; 
        while(i<this.localPrependParameters.size()){
            if(this.localPrependParameters.get(i).getName().equals(parameter.getName())){
                this.localPrependParameters.remove(i);
            }
            else{
                i++;
            }
        }
        this.localPrependParameters.add(parameter);
    }

    /**
     * @return the skipWaiting
     */
    public boolean isSkipWaiting() {
        return skipWaiting;
    }

    /**
     * @param skipWaiting the skipWaiting to set
     */
    public void setSkipWaiting(boolean skipWaiting) {
        this.skipWaiting = skipWaiting;
    }

    /**
     * @return the removeFilesAfterPipeCompletion
     */
    public boolean isRemoveFilesAfterSetCompletion() {
        return removeFilesAfterSetCompletion;
    }

    /**
     * @param removeFilesAfterPipeCompletion the removeFilesAfterPipeCompletion to set
     */
    public void setRemoveFilesAfterSetCompletion(boolean removeFilesAfterPipeCompletion) {
        this.removeFilesAfterSetCompletion = removeFilesAfterPipeCompletion;
    }

    /**
     * @return the forceWorkingDirectory
     */
    public String getForceWorkingDirectory() {
        return forceWorkingDirectory;
    }

    /**
     * @param forceWorkingDirectory the forceWorkingDirectory to set
     */
    public void setForceWorkingDirectory(String forceWorkingDirectory) {
        this.forceWorkingDirectory = forceWorkingDirectory;
    }
}
