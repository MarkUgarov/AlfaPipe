/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import com.mugarov.alfapipe.model.datatypes.InputFile;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.filetools.FileLister;
import com.mugarov.alfapipe.model.filetools.FileNaming;
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
    private File originalFile;
    private boolean originalIsInputFileType;
    private File inputFile;
    private File outputFile;
    private boolean outputIsDirectory;
    private StringBuilder builder;
    private boolean useClusterParameters;
    private ProgramSet programSet;
    
    private FileLister fileLister;
    
    private String programName;
    
    public ExecutionCommandBuilder(LogFileManager logManager){
        this.outputFile = null;
        this.outputIsDirectory = false;
        this.builder = null;
        this.log = logManager;
        this.fileLister = null;
        this.useClusterParameters = false;
        this.programName = null;
    }
    
    public void setName(String name){
        this.programName = name;
    }
    
    public void useClusterParameters(boolean use){
        this.useClusterParameters = use;
    }
    
    
    public void buildString(   ProgramSet parameterSet,
                                File inputFile,
                                String parentOutputDirectory,
                                ArrayList<File> pairedFiles,
                                File originalFile){
        this.buildString(parameterSet, inputFile, parentOutputDirectory, pairedFiles, originalFile, (originalFile instanceof InputFile));
    }
    
    /**
     * Creates directory!!!
     * @param parameterSet
     * @param inputFile
     * @param parentOutputDirectory 
     * @param pairedFiles 
     * @param originalFile is for the source for the finding of a proper name
     * @param originalIsInputFileType is for the specific method of finding for a proper name
     */
    public void buildString(   ProgramSet parameterSet,
                                File inputFile,
                                String parentOutputDirectory,
                                ArrayList<File> pairedFiles,
                                File originalFile,
                                boolean originalIsInputFileType){
        this.builder = new StringBuilder();
        this.set = parameterSet;
        this.inputFile = inputFile;
        this.programSet = parameterSet;
        this.originalFile = originalFile;
        this.originalIsInputFileType = originalIsInputFileType;
        if(this.programName == null){
            this.programName = parameterSet.getName();
        }
        
        String clearFileName = this.getClearName(originalFile, originalIsInputFileType);
        /**
         * create output directory and check if it is the value for the 
         * output-command or if it needs a special filepath for the output
         */
        this.log.appendLine("Building command for "+parameterSet.getName()+" with inputFile "+inputFile.getName(), ExecutionCommandBuilder.class.getName());
        if(parameterSet.getParsedParameters().getOutputSettings().isDirectory()){
            this.outputFile = new File(parentOutputDirectory, this.getClearName(parameterSet.getParsedParameters().getName())
                                                              +"_"
                                                              +clearFileName);
            
            if(parameterSet.getParsedParameters().getOutputSettings().isMakeDirectory()){
                this.outputFile.mkdirs();
            } 
            this.outputIsDirectory = true;
        }
        else{
            if(parameterSet.getParsedParameters().getOutputEndings() != null && parameterSet.getParsedParameters().getOutputEndings().length>0){
                this.outputFile = new File(parentOutputDirectory, this.getClearName(parameterSet.getName())
                                                       + File.separatorChar
                                                       + this.getClearName(parameterSet.getParsedParameters().getName())
                                                       + "_"
                                                       + clearFileName
                                                       + parameterSet.getParsedParameters().getOutputEndings()[0]);
            }
            else{
                this.outputFile = new File(parentOutputDirectory, this.getClearName(parameterSet.getName())
                                                       + File.separatorChar
                                                       + this.getClearName(parameterSet.getParsedParameters().getName())
                                                       + "_"
                                                       + clearFileName);
            }
            File outputDirectory = new File(this.outputFile.getParent());
            if(!outputDirectory.exists() && parameterSet.getParsedParameters().getOutputSettings().isMakeDirectory()){
                outputDirectory.mkdirs();
            }

            this.outputIsDirectory = false;
        }
        this.fileLister = new FileLister(this.log, this.outputFile, this.outputIsDirectory, originalFile, this.set);
        if(parameterSet.getParsedParameters().getStartCommand() != null){
            if(this.useClusterParameters){
               // System.out.println("BUILD PROGRAM FOR CLUSTER: "+this.set.getName());
                for(ParameterField cP:parameterSet.getParsedParameters().getAdditionalClusterParameters()){
                    this.buildParameterCommand(cP, null, null, parameterSet.getClusterParameters(), originalFile, originalIsInputFileType);
                }
            }
            else{
                for(ParameterField lP:parameterSet.getParsedParameters().getLocalPrependParameters()){
                    this.buildParameterCommand(lP, null, null, parameterSet.getLocalPrependParamters(), originalFile, originalIsInputFileType);
                }
            }
            
            if(parameterSet.getParsedParameters().getEnterCommand() != null){
                builder.append(parameterSet.getParsedParameters().getEnterCommand());
                builder.append("\n");
            }
            builder.append(parameterSet.getParsedParameters().getStartCommand());
            builder.append(" ");
            for(ParameterField pf:parameterSet.getParsedParameters().getParameters() ){
                this.buildParameterCommand(pf, parameterSet.getParsedParameters().getPairedCommand(), pairedFiles, parameterSet.getInputParameters(), originalFile, originalIsInputFileType);
            }
            if(parameterSet.getParsedParameters().getExitCommand()!= null){
                builder.append("\n");
                builder.append(parameterSet.getParsedParameters().getExitCommand());
            }
        }
    }
    
    public void buildParameterCommand(ParameterField pf, ParameterField pairedCommand, ArrayList<File> pairedFiles, ArrayList<InputParameter> parameters, File originalFile, boolean originalIsInputFileType){
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
            if(pairedFiles != null && pairedCommand == null){
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
            while(!found && i<parameters.size()){
                if(parameters.get(i).getName().equals(pf.getName())){
                    if(parameters.get(i).isBoolean()){
                        // Hint for UI: Make sure that optional parameters are NOT selectable in the GUI.
                        if((writeCommand && parameters.get(i).getBoolean())||!parameters.get(i).isOptional()){
                            builder.append(pf.getCommand()); 
                            builder.append(" ");
                        }
                    }
                    else{
                        // Hint for UI: Make sure that optional parameters are NOT selectable in the GUI.
                        if(parameters.get(i).getBoolean()||!parameters.get(i).isOptional()){
                            if(writeCommand){
                                builder.append(pf.getCommand()); 
                                if(!pf.isAvoidLeadingSpace() || !writeValue){
                                    builder.append(" ");
                                }
                            }
                            if(writeValue){
                                builder.append(this.getReplacedValue(parameters.get(i).getValue()));
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
    
    private String getReplacedValue(String value){
        String replacedValue = value;
        if(replacedValue.contains(ParameterPool.PROGRAM_PATH_VALUE)){
            if(this.outputIsDirectory){
                replacedValue = replacedValue.replaceAll(ParameterPool.PROGRAM_PATH_VALUE, outputFile.getPath());
            }
            else{
                replacedValue = replacedValue.replaceAll(ParameterPool.PROGRAM_PATH_VALUE, outputFile.getParent());
            }
        }
        if(replacedValue.contains(ParameterPool.PROGRAM_NAME_VALUE)){
            replacedValue = replacedValue.replaceAll(ParameterPool.PROGRAM_NAME_VALUE, this.getClearName(this.originalFile, this.originalIsInputFileType));
        }
        if(replacedValue.contains(ParameterPool.PROGRAM_USER_VALUE)){
            replacedValue = replacedValue.replaceAll(ParameterPool.PROGRAM_USER_VALUE, System.getProperty("user.name"));
        }
        if(replacedValue.contains(ParameterPool.PROGRAM_PROGRAM_NAME_VALUE)){
            replacedValue = replacedValue.replaceAll(ParameterPool.PROGRAM_PROGRAM_NAME_VALUE,  this.getClearName(this.programName));
        }
        return replacedValue;
    }
    
    /**
     * Removes the extension and most of the special characters from a filename.
     * @param file can be any file with a non-empty name
     * @return the name of the file without extensions and most of the 
     * special characters,
     * returns "No_valid_name" if the filename could not be cleared
     */
    private String getClearName(File file, boolean isInputFileType){
        if(isInputFileType){
            return ((InputFile) file).getClearName();
        }
        else{
            return FileNaming.getClearName(file);
        }
    }
    
    /**
     * Removes the extension and most of the special characters from a String.
     * @param name can be any non-empty String
     * @return the name of the file without extensions and most of the 
     * special characters,
     * returns "No_valid_name" if the String could not be cleared
     */
    private String getClearName(String name){
        return FileNaming.getClearName(name);

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
     * 
     * @return the file itself if the output is a directory or the parent 
     * directory of the output if it is not a directory
     */
    public File getWorkingDirectory(){
        File ret;
        
        if(this.programSet.getParsedParameters().getForceWorkingDirectory() != null){
            return new File(this.getReplacedValue(this.programSet.getParsedParameters().getForceWorkingDirectory()));
        }
        else if(this.outputIsDirectory){
            ret = (this.programSet.getParsedParameters().getOutputSettings().isMakeDirectory())?this.outputFile:this.outputFile.getParentFile();
        }
        else{
            ret = (this.programSet.getParsedParameters().getOutputSettings().isMakeDirectory())?this.outputFile.getParentFile():this.outputFile.getParentFile().getParentFile();
        }
        return ret;
    }
    
    /**
     * Be careful! OutputFile may not exist, especially if the outputpath can 
     * not be set manually - better user "getRelevantOutputFor()"-method.
     * @return the absolute output path 
     */
    public String getOutputPath(){
        return this.outputFile.getAbsolutePath();
    }
    
    public File getOutputFile(){
        return this.outputFile;
    }
    
//    /**
//     * This is an old method! It does only return the relevant output if there 
//     * is no other ExecutionCommandBuilder than this and the following. 
//     * Use buildString before! Make sure the execution of the set (= the execution 
//     * of the command given this.buildString(...) ) worked and the output files are
//     * written already.
//     * @param following ist the ProgramSet which an output could be defined for
//     * @param originalFile is the original input-file at the beginning of the pipe
//     * @return different parameters:
//     *  -all files if there is no specific output defined for ANY program at all
//     *  -OR the specific output defined if it is defined for the set "following"
//     *  -OR all files if there is no specific output defined for this specific set "following"
//     */
//    public ArrayList<File> getRelevantOutputFor(ProgramSet following, File originalFile){
//        if(this.set == null){
//            this.log.appendLine(ParameterPool.LOG_WARNING+"Commandbuilder: no set! ", ExecutionCommandBuilder.class.getName());
//            return null;
//        }
//        ArrayList<File> ret;
//        if(this.set.getParsedParameters().getEssentialOutputs() == null){
//           ret= this.getAllFiles();
//        }
//        else{
//            boolean found = false;
//            ret = new ArrayList<>(this.set.getParsedParameters().getEssentialOutputs().size());
//            for(NameField field:this.set.getParsedParameters().getEssentialOutputs()){
//                if(field.getEssentialFor() == null || field.getEssentialFor().equals(following.getName())){
//                    if(field.isUseAll()){
//                        return this.getAllFiles();
//                    }
//                    ret.add(this.getFileFor(field, originalFile));
//                    found = false;
//                }
//            }   
//            // 
//            /**
//             * if no specific output was declared for the following set (but 
//             * for other possible sets)
//             * Example: this.set has the role of an assembler and after that 
//             * a and b 
//             */
//            if(!found){
//                ret = this.getAllFiles();
//            }
//        }
//        if(ret== null||ret.isEmpty()){
//            this.log.appendLine(ParameterPool.LOG_WARNING+"Commandbuilder: empty or null returns for "+following.getName(), ExecutionCommandBuilder.class.getName());
//        }
//        return ret;
//    }
    
    public ArrayList<File> getSpecificExistentFilesFor(ProgramSet following){
        return this.fileLister.getSpecifiExistentFilesFor(this.set, following);
    }
    
    /**
     * Get all output files if no specific output was defined.
     * @param following should be the fProgramSet of the program you want to use next
     * @param originalFile should be the file you used as input in the very first place (most likely any *.fastq.gz)
     * @return an ArrayList of files which will be empty if there are specified outputs for following or includes all output files else.
     */
    public ArrayList<File> getAllIfNotSpecified(ProgramSet following){
        return this.fileLister.getAllIfNotSpecified(this.set, following);
    }
    
    private ArrayList<File> getAllFiles(){
        return this.fileLister.getAllFiles();
    }
    
//    private File getFileFor(NameField field, File originalFile){
//        return this.fileLister.getFileFor(field);
//    }
    
    private File getDynamicNamedFileOf(String parentDir, NameField field, File originalFile){
        return new File(parentDir, FileNaming.getDynamicNameOf(field, originalFile));
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
    
    public FileLister getFileLister(){
        return this.fileLister;
    }
    
    public ProgramSet getProgramSet(){
        return this.programSet;
    }
    
}
