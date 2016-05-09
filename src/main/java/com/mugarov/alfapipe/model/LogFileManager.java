/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mugarov
 */
public class LogFileManager {
    
    private File logfile;
    private FileWriter writer;
    private BufferedWriter bufferedWriter;
    
    public LogFileManager(String parentDirectory){
        File parent = new File(parentDirectory);
        this.logfile = this.makeNewLog(parent);
        String oldRenamed = "";
        if( !this.logfile.getParentFile().exists() || !this.logfile.getParentFile().isDirectory() ){
            System.out.println("Trying to create directory "+parent.getAbsolutePath());
            parent.mkdirs();
        }
        boolean isOverwritten = false;
        if(!this.logfile.exists() || this.logfile.isDirectory()){
            System.out.println("Trying to create file "+this.logfile.getAbsolutePath());
            try {
                this.logfile.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(LogFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            oldRenamed = this.renameOld(logfile);
            isOverwritten=true;
            this.logfile = this.makeNewLog(parent);
            try {
                this.logfile.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(LogFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(isOverwritten){
            this.appendLine(ParameterPool.LOG_RENAMED_HINT + " to "+oldRenamed, LogFileManager.class.getName());
        }
    }
    
    private final File makeNewLog(File parent){
        return new File(parent, ParameterPool.FILE_LOGFILE_NAME);
    }
    
    private String renameOld(File file){
        System.out.println("Trying to rename "+file.getPath());
        int index = 1;
        boolean found = false;
        String newName = null;
        File newFile;
        while(!found){
            newName= "old"+file.getName()+index;
            newFile = new File(file.getParent(), newName);
            if(!newFile.exists()){
                file.renameTo(newFile);
                found = true;
            }
            else{
                System.out.println("File "+newFile.getName()+" exists in "+newFile.getParent());
                index++;
            }
        }
        return newName;
        
    }
    
    public void appendLine(String args, String source){
        this.appendLine(args, source, true);
    }
    
    public void appendLine(String args, String source, boolean check){
        //  System.out.println("Trying to write "+args+" to "+this.logfile.getAbsolutePath());
        if(check){
            boolean madeDir = false;
            boolean madeFile = false;
            if( !this.logfile.getParentFile().exists() || !this.logfile.getParentFile().isDirectory() ){ 
                this.logfile.getParentFile().mkdirs();
                madeDir = true;
            }
            if( !this.logfile.exists() || this.logfile.isDirectory()){
                try {
                    this.logfile.createNewFile();
                    madeFile = true;
                } catch (IOException ex) {
                    Logger.getLogger(LogFileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(madeDir){
                this.appendLine(ParameterPool.LOG_WARNING+" Deletion of directory while running! "+this.logfile.getParent(), LogFileManager.class.getName(), false);
            }
            if(madeFile){
                this.appendLine(ParameterPool.LOG_WARNING+" Deletion of logfile while running! "+this.logfile.getPath(), LogFileManager.class.getName(), false);
            }
        }
        try {
            this.writer = new FileWriter(this.logfile.getAbsoluteFile(), true);
            this.bufferedWriter = new BufferedWriter(this.writer);
        
            this.bufferedWriter.write(ParameterPool.LOG_LINE_PREFIX);
            this.bufferedWriter.append(System.currentTimeMillis()+"");
            this.bufferedWriter.append(":");
            this.bufferedWriter.write(args.trim());
            this.bufferedWriter.write(ParameterPool.LOG_SOURCE_HINT);
            this.bufferedWriter.write(source);
            this.bufferedWriter.write(ParameterPool.LOG_LINE_POSTFIX);
            this.bufferedWriter.newLine();
            
            this.bufferedWriter.flush();
            this.bufferedWriter.close();
        } catch (IOException ex) {
            System.err.println("Could not write to Logfile to path "+this.logfile.getAbsolutePath()+". Please check access.");
            Logger.getLogger(LogFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void appendLines(String[] args, String source){
        for(String arg:args){
            this.appendLine(arg, LogFileManager.class.getName());
        }
    }
    
    public File getLogfile(){
        return this.logfile;
    }
    
    public void setParentDirectory(String parentDir){
        String old = this.logfile.getAbsolutePath();
        this.appendLine(ParameterPool.LOG_CHANGE_TO+parentDir, LogFileManager.class.getName());
        File parent = new File(parentDir);
        if( !parent.exists() || !parent.isDirectory() ){
            parent.mkdirs();
        }
        this.logfile = new File(parent, ParameterPool.FILE_LOGFILE_NAME);
        boolean isOverwritten = false;
        if(!this.logfile.exists() || this.logfile.isDirectory()){
            try {
                this.logfile.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(LogFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            this.logfile.delete();
            isOverwritten=true;
            try {
                this.logfile.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(LogFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.appendLine(ParameterPool.LOG_CHANGED_FROM+old, LogFileManager.class.getName());
        if(isOverwritten){
            this.appendLine(ParameterPool.LOG_RENAMED_HINT, LogFileManager.class.getName());
        }
    }
    
}
