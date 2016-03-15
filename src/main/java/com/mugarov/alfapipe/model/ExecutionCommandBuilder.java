/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ExecutionCommandBuilder {
    
    private final LogFileManager log;
    private ProgramSet set;
    private File inputFile;
    private File outputFile;
    private boolean outputIsDirectory;
    private StringBuilder builder;
    
    public ExecutionCommandBuilder(LogFileManager logManager){
        this.outputFile = null;
        this.outputIsDirectory = false;
        this.builder = null;
        this.log = logManager;
    }
    
    
    /**
     * Creates directory!!!
     * @param parameterSet
     * @param inputFile
     * @param parentOutputDirectory 
     * @param pairedFiles 
     * @param originalFile 
     */
    public void buildString(   ProgramSet parameterSet,
                                File inputFile,
                                String parentOutputDirectory,
                                ArrayList<File> pairedFiles,
                                File originalFile){
        this.builder = new StringBuilder();
        this.set = parameterSet;
        this.inputFile = inputFile;
        /**
         * create output directory and check if it is the value for the 
         * output-command or if it needs a special filepath for the output
         */
        this.log.appendLine("Building command for "+parameterSet.getName()+" with inputFile "+inputFile.getName(), ExecutionCommandBuilder.class.getName());
        if(parameterSet.getParsedParameters().isOnlyOutputDirectorySetable()){
            this.outputFile = new File(parentOutputDirectory, this.getClearName(parameterSet.getParsedParameters().getName())
                                                              +"_"
                                                              +this.getClearName(originalFile));
            this.outputFile.mkdirs();
            this.outputIsDirectory = true;
        }
        else{
            this.outputFile = new File(parentOutputDirectory, this.getClearName(parameterSet.getName())
                                                        + File.separatorChar
                                                        + parameterSet.getParsedParameters().getName()
                                                        + "_"
                                                        + this.getClearName(originalFile)
                                                        + parameterSet.getParsedParameters().getOutputEndings()[0]);
            File outputDirectory = new File(this.outputFile.getParent());
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
                if(pf.getCommand() == null || pf.getCommand().length() == 0 || pf.getCommand().equals(ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE)){
                    writeCommand=false;
                }

                boolean writeValue = true;
                if(pf.getDefaultValue() != null && pf.getDefaultValue().equals(ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE)){
                    writeValue = false;
                }


                if(pf.getName().equals(ParameterPool.PROGRAM_INPUT_PATH_SET_PARAMETER_NAME)){
                    if(writeCommand){
                        builder.append(pf.getCommand()); 
                        if(!pf.isAvoidLeadingSpace()){
                            builder.append(" ");
                        }
                        
                    }
                    

                    builder.append(inputFile.getAbsolutePath());
                    if(!pf.isAvoidLeadingSpace()){
                        builder.append(" ");
                    }
                    if(pairedFiles != null && parameterSet.getParsedParameters().getPairedCommand() == null){
                        for(File file:pairedFiles){
                            builder.append(file.getAbsolutePath());
                            builder.append(" ");
                        }
                    }
                }
                else if(pf.getName().equals(ParameterPool.PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME)){
                    if(writeCommand){
                        builder.append(pf.getCommand()); 
                        if(!pf.isAvoidLeadingSpace()){
                            builder.append(" ");
                        }
                    }
                    builder.append(outputFile.getAbsolutePath());
                    builder.append(" ");
                    
                }
                else if(pf.getName().equals(ParameterPool.PROGRAM_PAIRED_PARAMETER_NAME)){
                    if(writeCommand){
                        builder.append(pf.getCommand()); 
                        if(!pf.isAvoidLeadingSpace()){
                            builder.append(" ");
                        }
                    }
                    if(pairedFiles != null){
                        for(File file:pairedFiles){
                            builder.append(file.getAbsolutePath());
                            builder.append(" ");
                        }
                    }
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
                                        if(!pf.isAvoidLeadingSpace() || !writeValue){
                                            builder.append(" ");
                                        }
                                    }
                                    if(writeValue){
                                        String value = parameterSet.getInputParameters().get(i).getValue();
                                        if(value.contains(ParameterPool.PROGRAM_DIRECTORY_VALUE)){
                                            if(this.outputIsDirectory){
                                                value.replaceAll(ParameterPool.PROGRAM_DIRECTORY_VALUE, outputFile.getPath());
                                            }
                                            else{
                                                value.replaceAll(ParameterPool.PROGRAM_DIRECTORY_VALUE, outputFile.getParent());
                                            }
                                        }
                                        if(value.contains(ParameterPool.PROGRAM_NAME_VALUE)){
                                            value.replaceAll(ParameterPool.PROGRAM_NAME_VALUE, this.getClearName(originalFile));
                                        }
                                        builder.append(value);
                                        builder.append(" ");  
                                    }
                                }
                            }
                            found = true;
                        }
                        i++;
                    }
                    if(!found){
                        this.log.appendLine(ParameterPool.LOG_WARNING+"NO InputFileParameter was found for "+pf.getName(), ExecutionCommandBuilder.class.getName());
                    }
                }
            }
            if(parameterSet.getParsedParameters().getExitCommand()!= null){
                builder.append("\n");
                builder.append(parameterSet.getParsedParameters().getExitCommand());
            }
        }
    }
    
    /**
     * Removes the extension and most of the special characters from a filename.
     * @param file can be any file with a non-empty name
     * @return the name of the file without extensions and most of the 
     * special characters,
     * returns "No_valid_name" if the filename could not be cleared
     */
    private String getClearName(File file){
        return this.getClearName(file.getName());
    }
    
    /**
     * Removes the extension and most of the special characters from a String.
     * @param name can be any non-empty String
     * @return the name of the file without extensions and most of the 
     * special characters,
     * returns "No_valid_name" if the String could not be cleared
     */
    private String getClearName(String name){
        String[] splitname = name.split("\\.",2);
        if(splitname.length== 0){
            return "No_valid_name";
        }
        else{
            String spln = splitname[0];
            for(String reg:ParameterPool.REPLACE_REGEX){
                spln.replaceAll(reg, ParameterPool.REPLACE_REPLACEMENT);
            }
            return name;
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
    
    /**
     * Be careful! OutputFile may not exist, especially if the outputpath can 
     * not be set manually - better user "getRelevantOutputFor()"-method.
     * @return the absolute output path 
     */
    public String getOutputPath(){
        return this.outputFile.getAbsolutePath();
    }
    
    /**
     * This is an old method! It does only return the relevant output if there 
     * is no other ExecutionCommandBuilder than this and the following. 
     * Use buildString before! Make sure the execution of the set (= the execution 
     * of the command given this.buildString(...) ) worked and the output files are
     * written already.
     * @param following ist the ProgramSet which an output could be defined for
     * @param originalFile is the original input-file at the beginning of the pipe
     * @return different parameters:
     *  -all files if there is no specific output defined for ANY program at all
     *  -OR the specific output defined if it is defined for the set "following"
     *  -OR all files if there is no specific output defined for this specific set "following"
     */
    public ArrayList<File> getRelevantOutputFor(ProgramSet following, File originalFile){
        if(this.set == null){
            this.log.appendLine(ParameterPool.LOG_WARNING+"Commandbuilder: no set! ", ExecutionCommandBuilder.class.getName());
            return null;
        }
        ArrayList<File> ret;
        if(this.set.getParsedParameters().getEssentialOutputs() == null){
           ret= this.getAllFiles();
        }
        else{
            boolean found = false;
            ret = new ArrayList<>(this.set.getParsedParameters().getEssentialOutputs().size());
            for(NameField field:this.set.getParsedParameters().getEssentialOutputs()){
                if(field.getEssentialFor() == null || field.getEssentialFor().equals(following.getName())){
                    if(field.isUseAll()){
                        return this.getAllFiles();
                    }
                    ret.add(this.getFileFor(field, originalFile));
                    found = false;
                }
            }   
            // 
            /**
             * if no specific output was declared for the following set (but 
             * for other possible sets)
             * Example: this.set has the role of an assembler and after that 
             * a and b 
             */
            if(!found){
                ret = this.getAllFiles();
            }
        }
        if(ret== null||ret.isEmpty()){
            this.log.appendLine(ParameterPool.LOG_WARNING+"Commandbuilder: empty or null returns for "+following.getName(), ExecutionCommandBuilder.class.getName());
        }
        return ret;
    }
    
    public ArrayList<File> getSpecifiFilesFor(ProgramSet following, File originalFile){
        ArrayList<File> ret;
        ret = new ArrayList<>(this.set.getParsedParameters().getEssentialOutputs().size());
        if(following.getName() == null){
            this.log.appendLine("No name for following. Returning all files.", ExecutionCommandBuilder.class.getName());
            return this.getAllFiles();
        }
        for(NameField field:this.set.getParsedParameters().getEssentialOutputs()){
            if(field.getEssentialFor() == null || field.getEssentialFor().equals(following.getName())){
                if(field.getName() ==  null){
                    this.log.appendLine("Return empty file list as specific files for "+following.getName()+" - NameField was null.", ExecutionCommandBuilder.class.getName());
                    return new ArrayList<File>();
                }
                this.log.appendLine("Found: "+this.set.getName()+" has specific file(s) for "+following.getName(), ExecutionCommandBuilder.class.getName());
                if(field.isUseAll()){
                    this.log.appendLine("Returning all.(UseAll is true)", ExecutionCommandBuilder.class.getName());
                    return this.getAllFiles();
                }
                File spec = this.getFileFor(field, originalFile);
                if(spec!= null){
                   ret.add(this.getFileFor(field, originalFile));
                }
            }
        }   
        return ret;
    }
    
    /**
     * Get all output files if no specific output was defined.
     * @param following should be the fProgramSet of the program you want to use next
     * @param originalFile should be the file you used as input in the very first place (most likely any *.fastq.gz)
     * @return an ArrayList of files which will be empty if there are specified outputs for following or includes all output files else.
     */
    public ArrayList<File> getAllIfNotSpecified(ProgramSet following, File originalFile){
        // return empty if there is specific output for following
        for(NameField field:this.set.getParsedParameters().getEssentialOutputs()){
            if(field.getEssentialFor() == null || field.getEssentialFor().equals(following.getName())){
                return new ArrayList<File>();            
            }
        }   
        // return all datas if there is no specific output
        return this.getAllFiles();
    }
    
    private ArrayList<File> getAllFiles(){
        ArrayList<File> ret = new ArrayList<>();
           if(this.outputIsDirectory){
               for(File f:this.outputFile.listFiles()){
                   ret.add(f);
               }
           } 
           else{
               ret.add(this.outputFile);
           }
           return ret;
    }
    
    private File getFileFor(NameField field, File originalFile){
        File ret=null;
        if(field.isDynamic()){
            if(this.outputIsDirectory){
                ret = this.getDynamicNameOf(this.outputFile.getPath(),field, originalFile);
            } 
            else{
                ret = this.getDynamicNameOf(this.outputFile.getParent(), field,originalFile);
            }
        }
        else{
            if(field.getName().equals(ParameterPool.PROGRAM_DIRECTORY_VALUE)){
                if(this.outputIsDirectory){
                    return this.outputFile;
                }
                else{
                    return this.outputFile.getParentFile();
                }
            }
            else if(this.outputIsDirectory){
                ret =  new File(this.outputFile.getAbsolutePath()+File.separatorChar+field.getName());
            }
            else{
                ret= new File(this.outputFile.getParent()+File.separatorChar+field.getName());
            }
        }
        if(!ret.exists()){
            this.log.appendLine(ParameterPool.LOG_WARNING+"File: "+ret.getName()+" does not exist but will be used further!", ExecutionCommandBuilder.class.getName());
        }
        this.log.appendLine("Returning "+ret.getName(), ExecutionCommandBuilder.class.getName());
        return ret;
    }
    
    private File getDynamicNameOf(String parentDir, NameField field, File originalFile){
        String[] splitname = originalFile.getName().split(field.getRegex());
        StringBuilder newName = new StringBuilder();
        int low = field.getLowerbound();
        int up;
        if(field.getUpperbound()>0){
            up = field.getUpperbound();
        }
        else{
            up = splitname.length-field.getUpperbound();
        }
        
        if(field.getPrefix() != null){
            newName.append(field.getPrefix());
        }
        for(int i= low; i<up; i++){
            newName.append(splitname[i]);
        }
        if(field.getPostfix() != null){
            newName.append(field.getPostfix());
        }
        return new File(parentDir, newName.toString());
    }
    
    public boolean useOnlyThisOutput(ProgramSet following){
        if(this.set == null){
           this.log.appendLine(ParameterPool.LOG_WARNING+"Trying to access an ProgramParameterSet which is 'null'!", ExecutionCommandBuilder.class.getName());
            return false;
        }
        else if(following.getName() == null){
            this.log.appendLine(ParameterPool.LOG_WARNING+"The ProgramParameterSet has no name!", ExecutionCommandBuilder.class.getName());
            return false;
        }
        else{
            for(NameField field:this.set.getParsedParameters().getEssentialOutputs()){
                if(field.getEssentialFor() == null || following.getName().equals(field.getEssentialFor())){
                    return field.isUseOnly();
                }
            }
            return false;
        }
    }
    
    
}
