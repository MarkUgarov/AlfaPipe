/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

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

    private LogFileManager log;
    

    public Executioner(LogFileManager logManager) {
        this.log = logManager;
    }
    /**
     * You can give any string, but it should be executable on unix. 
     * Commands will be separated by newline ("\n").
     * @param input
     * @return 
     */
    public boolean execute(String input){
        
        if(input == null || input.length() == 0){
            this.log.appendLine("Null has been seen selected. Waiting for the next input.", Executioner.class.getName());
            return true;
        }
        boolean success=true;
        
        for(String step:input.split("\n")){
            String[] commandsArray = step.split(" ");
            ArrayList<String> commandList = new ArrayList<>(commandsArray.length);
//            commandList.add("pwd");
            for(String command:commandsArray){
                if(command.trim().length()>0){
                    commandList.add(command.trim());
                }
            }

            ProcessBuilder pb = new ProcessBuilder(commandList);
            log.appendLine(Pool.LOG_COMMAND_PREFIX+step, Executioner.class.getName());
            pb.redirectErrorStream(true);
            pb.redirectOutput(Redirect.appendTo(this.log.getLogfile()));

            
            Process process=null;
            try {
                process = pb.start();
            } catch (IOException ex) {
                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            try {
                process.waitFor();
                log.appendLine("Exit Value:"+process.exitValue(), Executioner.class.getName());
                success = (success&&(process.exitValue()==0));
            } catch (InterruptedException ex) {
                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            String out = null; 
//            try {
//                out = IOUtils.toString(process.getInputStream());
//                FileUtils.writeStringToFile(this., out,true);
//            } catch (IOException ex) {
//                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            if(out==null){
//                System.err.println("ERROR by getting input stream and writing it.");
//            }

//            String err = "noError";
//            try {
//                err=IOUtils.toString(process.getErrorStream());
//            } catch (IOException ex) {
//                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            if(!err.equals("noError")&&err.length()>0){
//                success=false;
//                System.out.println("Error stream: "+err);
//            }
        }

        return success;
    }
    
}
