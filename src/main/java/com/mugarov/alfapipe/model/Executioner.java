/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Mark
 */
public class Executioner {
    
    private final File logfile;
    private final File workingDirectory;
    
    /**
     * 
     * @param outputDirectory
     * @throws IOException if outputPath does not exist or file can not be created
     * because of other reasons
     */
    public Executioner(String outputDirectory) throws IOException{
        this.workingDirectory = new File(outputDirectory);
        this.logfile = new File(outputDirectory+File.separator+Pool.FILE_LOGFILE_NAME);
        if(!this.logfile.exists() || this.logfile.isDirectory()){
            this.logfile.createNewFile();
        }
        else if(this.logfile.exists()){
            this.logfile.delete();
            this.logfile.createNewFile();
        }
    }
    /**
     * You can give any string, but it should be executable on unix. 
     * Commands will be separated by newline ("\n").
     * @param input
     * @return 
     */
    public boolean execute(String input){
        if(input == null){
            return false;
        }
        boolean success=true;
        
        for(String step:input.split("\n")){
            String[] commandsArray = step.split(" ");
            ArrayList<String> commandList = new ArrayList<>(commandsArray.length);
            for(String command:commandsArray){
                if(command.trim().length()>0){
                    commandList.add(command.trim());
                }
            }

            ProcessBuilder pb = new ProcessBuilder(commandList);
            pb.directory(this.workingDirectory);
            pb.redirectErrorStream(true);
            pb.redirectOutput(this.logfile);

            Process process=null;
            try {
                process = pb.start();
            } catch (IOException ex) {
                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            try {
                process.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
            }

            String out = null; 
            try {
                out = IOUtils.toString(process.getInputStream());
                FileUtils.writeStringToFile(this.logfile, out,true);
            } catch (IOException ex) {
                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(out==null){
                System.err.println("ERROR by getting input stream and writing it.");
            }

            String err = "noError";
            try {
                err=IOUtils.toString(process.getErrorStream());
            } catch (IOException ex) {
                Logger.getLogger(Executioner.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!err.equals("noError")&&err.length()>0){
                success=false;
                System.out.println("Error stream: "+err);
            }
        }
        

        
        return success;
    }
    
}
