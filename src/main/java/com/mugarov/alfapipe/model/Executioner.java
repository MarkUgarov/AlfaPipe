/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import com.mugarov.alfapipe.model.cluster.ClusterJobWatcher;
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
    private final File workfile;
    

    public Executioner(LogFileManager logManager) {
        this.log = logManager;
        this.workfile = new File(ParameterPool.WORKING_DIRECTORY);
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
     * @return 
     */
    public boolean execute(String precommand, String input){
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

            ProcessBuilder pb = new ProcessBuilder(commandList);
            log.appendLine(ParameterPool.LOG_COMMAND_PREFIX+step, Executioner.class.getName());
            pb.redirectErrorStream(true);
            pb.redirectOutput(Redirect.appendTo(this.log.getLogfile()));
            
            
            pb.directory(this.workfile);

            
            Process process=null;
            try {
                process = pb.start();
            } catch (IOException ex) {
                if(process != null){
                    process.destroy();
                }
                
                this.log.appendLine("Error while executing.", Executioner.class.getName());
                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            try {
                process.waitFor();
            } catch (InterruptedException ex) {
                if(process != null){
                    process.destroy();
                }
                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            log.appendLine("Exit Value:"+process.exitValue(), Executioner.class.getName());
            success = (process.exitValue()==0);
            
        }

        return success;
    }
    
}
