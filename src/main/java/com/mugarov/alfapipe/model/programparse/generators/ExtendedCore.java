/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mugarov
 */
public class ExtendedCore {
    private ParseableProgramList parseableList;

    private final File localFile;
    private final String path;
     
    /**
     * 
     * @param path should be a path where to read from or write to
     * @param defaultParseList can be null if the file exists
     * @param index will be set to the defaultParseList if >=0
     */
    public ExtendedCore(String path, ParseableProgramList defaultParseList, int index){
        this.path = path;
        this.localFile = new File(this.path);
        if(!this.localFile.getParentFile().exists()){
            this.localFile.getParentFile().mkdirs();
        }
        if(!localFile.exists()){
            System.out.println("File does not exist! Creating default List in "+this.path);
            
            this.parseableList = defaultParseList;
            if(index >= 0){
                this.parseableList.setIndex(index);
            }
            this.parseOut();
        }
        else{
            this.parseIn();
        }
    }
    
    public String[] getAvailableNames(){
        String[] ret = new String[this.parseableList.getPrograms().size()];   
        for(int i= 0; i<this.parseableList.getPrograms().size(); i++){
            ret[i] = this.parseableList.getPrograms().get(i).getName();
        }
        return ret;
    }
 
    /**
     * 
     * @param name the name of the requested ParseableProgram
     * @return the requested ParseableProgram or null if it does 
 not exist
     */
    public ParseableProgram get(String name){
        ParseableProgram ret = null;
        for(ParseableProgram ass: this.parseableList.getPrograms()){
            if(name.equals(ass.getName())){
                ret=ass;
            }
        }
        return ret;
    }
    
    public final void parseOut() {
        if(this.parseableList != null){
            try {
                if (!localFile.exists()) {
                    localFile.createNewFile();
                }
                YAMLFactory factory = new YAMLFactory();
                ObjectMapper yamlmap = new ObjectMapper(factory);
                yamlmap.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                FileOutputStream fos = new FileOutputStream(localFile);
                
                

                factory.createGenerator(fos).writeObject(this.parseableList);
            } catch (IOException ex) {
                Logger.getLogger(AssemblerGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            System.err.println("Error: Not possible to parse out null with "+ExtendedCore.class);
        }
    }
    
    public final void parseIn(){ 
        if(this.localFile.length() >0){
            try {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                this.parseableList =   mapper.readValue(this.localFile, ParseableProgramList.class);        
            } catch (IOException ex) {
                Logger.getLogger(AssemblerGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    public ParseableProgramList getList(){
        return this.parseableList;
    }

}
