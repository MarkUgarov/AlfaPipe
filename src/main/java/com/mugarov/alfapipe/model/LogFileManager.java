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
        this.logfile = new File(parent, ParameterPool.FILE_LOGFILE_NAME);
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
            this.logfile.delete();
            isOverwritten=true;
            try {
                this.logfile.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(LogFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(isOverwritten){
            this.appendLine(ParameterPool.LOG_OVERWRITTEN_HINT, LogFileManager.class.getName());
        }
    }
    
    public void appendLine(String args, String source){
        //  System.out.println("Trying to write "+args+" to "+this.logfile.getAbsolutePath());
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
            this.appendLine(ParameterPool.LOG_OVERWRITTEN_HINT, LogFileManager.class.getName());
        }
    }
    
}
