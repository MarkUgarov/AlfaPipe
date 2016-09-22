/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark
 */
public class Executioner {

    private final LogFileManager log;
    private File workfile;
    
    private Process currentProcess;
    private final ArrayList<Process> processes;
    private ProcessBuilder currentBuilder;
    private final ArrayList<ProcessBuilder> builders;
    

    public Executioner(LogFileManager logManager) {
        this.log = logManager;
        this.processes = new ArrayList<>();
        this.builders = new ArrayList<>();
        this.setWorkFile(null);

    }
    
    public void setWorkFile(String path){
        if(path == null){
            this.setWorkFile(ParameterPool.WORKING_DIRECTORY+File.separator+System.getProperty("user.name"));
            return;
        }
        this.workfile = new File(path);
        if(!this.workfile.exists() || !this.workfile.isDirectory()){
            this.workfile.mkdirs();
        }
    }
    
    /**
     * You can give any string, but it should be executable on unix. 
     * Commands will be separated by newline ("\n").
     * @param precommand can be any string which will be arranged in front of the 
     * first line of the input command - useful to run on Cluster
     * @param input can be any number of unix-command lines
     * @return true if succeeded (ExitCode = 0) or input was empty
     */
    public boolean execute(String precommand, String input){
        return this.execute(precommand, input, true);
    }
            
    /**
     * You can give any string, but it should be executable on unix. 
     * Commands will be separated by newline ("\n").
     * @param precommand can be any string which will be arranged in front of the 
     * first line of the input command - useful to run on Cluster
     * @param input can be any number of unix-command lines
     * @param wait if this should wait for exit code before continuing
     * @return true if succeeded (ExitCode = 0) or input was empty (does not work
     * without waiting)
     */
    public boolean execute(String precommand, String input, boolean wait){
        boolean precommandUsed = false;
        
        if(input == null || input.length() == 0){
            this.log.appendLine("Null has been seen selected. Waiting for the next input.", Executioner.class.getName());
            return true;
        }
        boolean success=true;
        
        for(String step:input.split("\n")){
            if(!precommandUsed && precommand != null){
                step = precommand + " " + step;
                this.log.appendLine("This step will be run on cluster ", Executioner.class.getName());
                precommandUsed = true;
            }
            String[] commandsArray = step.split(" ");
            ArrayList<String> commandList = new ArrayList<>(commandsArray.length);
            for(String command:commandsArray){
                if(command.trim().length()>0){
                    commandList.add(command.trim());
                }
            }

            this.currentBuilder = this.createBuilder(commandList, step);

            
            this.currentProcess=this.createProcess(this.currentBuilder);

            if(wait){
                try {
                    this.currentProcess.waitFor();
                } catch (InterruptedException ex) {
                    if(this.currentProcess != null){
                        this.currentProcess.destroy();
                    }
//                    Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
                    this.log.appendLine("Interrupted process", Executioner.class.getName());
                    return false;
                }
                this.log.appendLine("Exit Value:"+currentProcess.exitValue(), Executioner.class.getName());
                success = (this.currentProcess.exitValue()==0);
            }
            else{
                this.log.appendLine("Avoid waiting. Continue", Executioner.class.getName());
            }
        }
        return success;
    }
    
    public void interrupt(){
        for(Process p:this.processes){
            if(p != null){
                p.destroy();
            }
        }
    }
    
    private Process createProcess(ProcessBuilder pb ){
        Process proc = null;
        try {
            proc = pb.start();
        } catch (IOException ex) {
            if(proc != null){
                proc.destroy();
            }
            this.log.appendLine("Error while creating Process.", Executioner.class.getName());
            Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.processes.add(proc);
        return proc;
    }
    
    private ProcessBuilder createBuilder(ArrayList<String> commandList, String step){
        ProcessBuilder pb = new ProcessBuilder(commandList);
        this.log.appendLine(ParameterPool.LOG_COMMAND_PREFIX+step, Executioner.class.getName());
        pb.redirectErrorStream(true);
        pb.redirectOutput(Redirect.appendTo(this.log.getLogfile()));
        pb.directory(this.workfile);
        this.builders.add(pb);
        return pb;
    }
}
