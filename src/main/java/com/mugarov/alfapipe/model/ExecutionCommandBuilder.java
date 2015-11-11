/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import com.mugarov.alfapipe.model.datatypes.InputFile;
import com.mugarov.alfapipe.model.datatypes.ProgramParameterSet;
import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Mark
 */
public class ExecutionCommandBuilder {
    
    private File outputFile;
    private boolean outputIsDirectory;
    private StringBuilder builder;
    
    public ExecutionCommandBuilder(){
        this.outputFile = null;
        this.outputIsDirectory = false;
        this.builder = null;
    }
    
    
    /**
     * Creates directory!!!
     * @param parameterSet
     * @param inputFile
     * @param parentOutputDirectory 
     */
    public void buildString(   ProgramParameterSet parameterSet,
                                File inputFile,
                                String parentOutputDirectory,
                                ArrayList<File> pairedFiles){
        this.builder = new StringBuilder();
        
        /**
         * create output directory and check if it is the value for the 
         * output-command or if it needs a special filepath for the output
         */
        if(parameterSet.getParsedParameters().isOnlyOutputDirectorySetable()){
            this.outputFile = new File(parentOutputDirectory, parameterSet.getName()
                                                              +"_"
                                                              + inputFile.getName());
            this.outputFile.mkdirs();
            this.outputIsDirectory = true;
        }
        else{
            this.outputFile = new File(parentOutputDirectory, parameterSet.getName()
                                                        + File.separatorChar
                                                        + parameterSet.getParsedParameters().getName()
                                                        + "_"
                                                        + FilenameUtils.removeExtension(inputFile.getName())
                                                        + parameterSet.getParsedParameters().getOutputEndings()[0]);
            File outputDirectory = new File(parentOutputDirectory);
            if(!outputDirectory.exists()){
                outputDirectory.mkdirs();
            }
            this.outputIsDirectory = false;
        }
        if(parameterSet.getParsedParameters().getStartCommand() != null){
            if(parameterSet.getParsedParameters().getEnterCommand() != null){
                builder.append(parameterSet.getParsedParameters().getEnterCommand());
                builder.append("\n");
            }
            builder.append(parameterSet.getParsedParameters().getStartCommand());
            builder.append(" ");
            for(ParameterField pf:parameterSet.getParsedParameters().getParameters() ){
                boolean writeCommand =true;
                if(pf.getCommand() == null){
                    writeCommand=false;
                }

                boolean writeValue = true;
                if(pf.getDefaultValue() != null && pf.getDefaultValue().equals(Pool.PROGRAM_EMPTY_PARAMETER_VALUE)){
                    writeValue = false;
                }


                if(pf.getName().equals(Pool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME)){
                    if(writeCommand){
                        builder.append(pf.getCommand()); 
                        builder.append(" ");
                    }

                    builder.append(inputFile.getAbsolutePath());
                    builder.append(" ");
                    if(pairedFiles != null){
                        for(File file:pairedFiles){
                            builder.append(file.getAbsolutePath());
                            builder.append(" ");
                        }
                    }
                    
                }
                else if(pf.getName().equals(Pool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME)){
                    if(writeCommand){
                        builder.append(pf.getCommand()); 
                        builder.append(" ");
                    }
                    builder.append(this.outputFile.getAbsolutePath());
                    builder.append(" ");
                }
                else{
                    int i = 0;
                    boolean found = false;
                    while(!found && i<parameterSet.getInputParameters().size()){
                        if(parameterSet.getInputParameters().get(i).getName().equals(pf.getName())){
                            if(parameterSet.getInputParameters().get(i).isBoolean()){
                                // Hint for UI: Make sure that optional parameters are NOT selectable in the GUI.
                                if((writeCommand && parameterSet.getInputParameters().get(i).getBoolean())||!parameterSet.getInputParameters().get(i).isOptional()){
                                    builder.append(pf.getCommand()); 
                                    builder.append(" ");
                                }
                            }
                            else{
                                // Hint for UI: Make sure that optional parameters are NOT selectable in the GUI.
                                if(parameterSet.getInputParameters().get(i).getBoolean()||!parameterSet.getInputParameters().get(i).isOptional()){
                                    if(writeCommand){
                                        builder.append(pf.getCommand()); 
                                        builder.append(" ");
                                    }
                                    if(writeValue){
                                        builder.append(parameterSet.getInputParameters().get(i).getValue());
                                        builder.append(" ");  
                                    }
                                }
                            }
                            found = true;
                        }
                        i++;
                    }
                    if(!found){
                        this.builder.append("NO InputFileParameter was found for "+pf.getName());
                    }
                }
            }
            if(parameterSet.getParsedParameters().getExitCommand()!= null){
                builder.append("\n");
                builder.append(parameterSet.getParsedParameters().getExitCommand());
            }
        }
    }
    
    public String getExecutionCommand(){
        if(this.builder.length()==0){
            return null;
        }
        else{
            return this.builder.toString();
        }
        
    }
    
    public boolean outputIsDirectory(){
        return this.outputIsDirectory;
    }
    
    public String getOutputPath(){
        return this.outputFile.getAbsolutePath();
    }
    
}
