/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.filetools;

import com.mugarov.alfapipe.model.LogFileManager;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mugarov
 */
public class FileLister {
    
    private final LogFileManager log;
    private final File outputFile;
    private final boolean outputIsDirectory;
    private final File originalFile;
    private final ProgramSet set;
    private boolean addOutputDir;
    private boolean addOutputFile;
    
    public FileLister(LogFileManager logManager, File outputFile, boolean outputIsDir, File originalFile, ProgramSet set){
        this.log = logManager;
        this.outputFile = outputFile;
        this.outputIsDirectory = outputIsDir;
        this.originalFile = originalFile;
        this.set = set;
        this.addOutputDir =false;
        this.addOutputFile = false;
    }
    
     public FileLister(LogFileManager logManager,  File originalFile, ProgramSet set){
        this.log = logManager;
        this.outputFile = null;
        this.outputIsDirectory = false;
        this.originalFile = originalFile;
        this.set = set;
        this.addOutputDir =false;
        this.addOutputFile = false;
    }
    
       
    public ArrayList<File> getSpecifiExistentFilesFor(ProgramSet current, ProgramSet following){
        ArrayList<File> ret;
        ret = new ArrayList<>(current.getParsedParameters().getEssentialOutputs().size());
        if(following.getName() == null){
            this.log.appendLine("No name for following. Returning all files.", FileLister.class.getName());
            return this.getAllFiles();
        }
        for(NameField field:current.getParsedParameters().getEssentialOutputs()){
            if(field.getEssentialFor() == null || field.getEssentialFor().equals(following.getName())){
                if(field.getFileName() ==  null){
                    this.log.appendLine("Return all file list as specific files for "+following.getName()+" - FileName was null.", FileLister.class.getName());
                    return this.getAllFiles();
                }
                this.log.appendLine("Found: "+current.getName()+" has specific file(s) for "+following.getName(), FileLister.class.getName());
                if(field.isUseAll()){
                    this.log.appendLine("Returning all.(UseAll is true)", FileLister.class.getName());
                    return this.getAllFiles();
                }
                else{
                    ArrayList<File> spec = this.getExistentFiles(field);
                    this.log.appendLine("Checking specific files for "+field.getEssentialFor(), FileLister.class.getName());
                    if(spec!= null && !spec.isEmpty()){
                       ret.addAll(spec);
                    }
                    else{
                       this.log.appendLine("No files where found!", FileLister.class.getName());
                    }
                }
               
            }
        }   
        return ret;
    }
    
    /**
     * Get all output files if no specific output was defined.
     * @param current should be the current ProgramSet - should not be null
     * @param following should be the fProgramSet of the program you want to use next
     * @return an ArrayList of files which will be empty if there are specified outputs for following or includes all output files else.
     */
    public ArrayList<File> getAllIfNotSpecified(ProgramSet current, ProgramSet following){
        // return empty if there is specific output for following
        for(NameField field:current.getParsedParameters().getEssentialOutputs()){
            if(field.getEssentialFor() == null || field.getEssentialFor().equals(following.getName())){
                return new ArrayList<>();            
            }
        }   
        // return all datas if there is no specific output
        return this.getAllFiles();
    }
    
    public ArrayList<File> getAllFiles(){
        ArrayList<File> ret = new ArrayList<>();
        if(this.outputFile== null){
            this.log.appendLine("Output File is null - empty ArrayList of File will be returned.", FileLister.class.getName());
            return ret;
        }
        for(File file:this.getFileCompareList()){
            if(file.exists()){
                ret.add(file);
            }
        }
        return ret;
    }
    
    
    private File getDynamicNamedFileOf(String parentDir, NameField field){
        return new File(parentDir, FileNaming.getDynamicNameOf(field, this.originalFile));
    }
    
    /**
     * Checks if all files declared essential in the ParameterSet of this 
     * FileLister do exist. 
     * @return true if all essential files do exist
     */
    public boolean checkEssentialFiles(){
        this.log.appendLine("Check if all essential files of "+this.set.getName()+" do exist", FileLister.class.getName());
        File dir;
        if(this.outputFile== null){
            this.log.appendLine("Output File is null - assuming all essential files do exist (returning 'true')", FileLister.class.getName());
            return true;
        }
        if(this.outputIsDirectory){
            dir = this.outputFile;
        }
        else{
            dir = this.outputFile.getParentFile();
        }
        String regex;
        for(NameField essential:this.set.getParsedParameters().getEssentialOutputs()){
            regex = this.getEssentialRegex(essential);
            if(regex == null || this.getExistentFiles(regex).isEmpty()){
                this.log.appendLine(ParameterPool.LOG_WARNING+"File: "+regex+" does not exist but will be used further! Returning fail of checking files of "+this.set.getName(), FileLister.class.getName());
                return false;
            }
            else{
                this.log.appendLine(essential.getFileName()+" does exist. Continuing.", FileLister.class.getName());
            }
        }
        return true;
    }
    
    private String getEssentialRegex(NameField field){
        String ret = null;
        if(this.outputFile == null){
            this.log.appendLine("Output File is null - null regex will be returned.", FileLister.class.getName());
            return null;
        }
        else if(field.isDynamic()){
            ret = FileNaming.getDynamicNameOf(field, originalFile);
        }
        else if(field.getFileName() == null){
            return this.outputFile.getName();
        }
        else{
            ret = field.getFileName();
        }
        if(field.getFileName().contains(ParameterPool.PROGRAM_DIRECTORY_VALUE)){
            ret = ret.replaceAll(ParameterPool.PROGRAM_DIRECTORY_VALUE, new File(this.getEssentialDirectoryPath()).getName());
            this.addOutputDir = true;
        }
        if(field.getFileName().contains(ParameterPool.PROGRAM_FILE_VALUE)){
            ret = ret.replaceAll(ParameterPool.PROGRAM_FILE_VALUE, this.outputFile.getName());
            this.addOutputFile = true;
        }
        return ret;
    }
    
    private String getEssentialDirectoryPath(){
        if(this.outputIsDirectory){
            return this.outputFile.getPath();
        }
        else{
            return this.outputFile.getParent();
        }
    }
    
    private ArrayList<File> getFileCompareList(){
        ArrayList<File> ret = new ArrayList<>();
        if(this.addOutputFile){
            ret.add(this.outputFile);
        }
        if(this.addOutputDir){
            File outDir = new File(this.getEssentialDirectoryPath());
            if(!this.addOutputFile || !outDir.getAbsolutePath().equals(this.outputFile.getAbsolutePath())){
                ret.add(outDir);
            }
        }
        if(this.outputIsDirectory){
            ret.addAll(Arrays.asList( this.outputFile.listFiles()));
        }
        return ret;
    }
    
    private ArrayList<File> getExistentFiles(NameField field){
        return this.getExistentFiles(this.getEssentialRegex(field));
    }
    
    private ArrayList<File> getExistentFiles(String regex){
        if(regex == null){
             this.log.appendLine("Trying to get File out of regex \"null\".", FileLister.class.getName());
        }
        ArrayList<File> ret = new ArrayList<>();
        Pattern uName = Pattern.compile(regex);
        Matcher mUname;
        for(File file:this.getFileCompareList()){
            mUname = uName.matcher(file.getName());
            if(mUname.matches() && file.exists()){
                this.log.appendLine("Returning file "+file.getName(), FileLister.class.getName());
                ret.add(file);
            }
        }
        return ret;
    }
    
    
    
    /**
     * Returns all endings of files that should be produced by the ProgramSet of 
     * this instance of FileLister which should be forwarded to the receiver.
     * @param receiver
     * @return empty if no files are specified for the receiver
     * or a List of file endings that should be forwarded to the receiver
     */
    public ArrayList<String> shouldProduceOutputFor(String receiver){
        ArrayList<String> outputEndings = new ArrayList<>();
        for(NameField field:this.set.getParsedParameters().getEssentialOutputs()){
            if(field.getEssentialFor() == null || field.getEssentialFor().equals(receiver)){
                if(field.getFileName() == null || field.getFileName().equals(ParameterPool.PROGRAM_FILE_VALUE) || field.getFileName().equals(ParameterPool.PROGRAM_DIRECTORY_VALUE)){
                    outputEndings.addAll(Arrays.asList(this.set.getParsedParameters().getOutputEndings()));
                }
                else{
                    outputEndings.add(FileNaming.getEnding(field.getFileName()));
                }
                        
            }
        }
        return outputEndings;
    }
    
    public ArrayList<String> shouldProduceOutputFor(ProgramSet receiver){
        return this.shouldProduceOutputFor(receiver.getName());
    }

    
    
}
