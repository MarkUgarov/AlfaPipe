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
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark
 */
public class GeneratorCore {
    
    private ArrayList<ParseableProgram> available;
    private final File localFile;
    private final String path;
     
    public GeneratorCore(String path, ArrayList<ParseableProgram> defaultList){
        this.available = new ArrayList<>();
        this.path = ParameterPool.CONFIG_PREFIX + System.getProperty("user.name")+ File.separatorChar+ParameterPool.CONFIG_INFIX+File.separatorChar  + path;
   

        this.localFile = new File(this.path);
        if(!this.localFile.getParentFile().exists()){
            this.localFile.getParentFile().mkdirs();
        }
        if(!localFile.exists()){
            System.out.println("File does not exist! Creating default List in "+this.path);
            this.available = defaultList;
            this.parseOut();
        }
        else{
            this.parseIn();
        }
        
        
    }
    
    public String[] getAvailableNames(){
        String[] ret = new String[this.available.size()];   
        for(int i= 0; i<this.available.size(); i++){
            ret[i] = this.available.get(i).getName();
        }
        return ret;
    }
 
    public ParseableProgram get(String name){
        ParseableProgram ret = null;
        for(ParseableProgram ass: this.available){
            if(name.equals(ass.getName())){
                ret=ass;
            }
        }
        return ret;
    }
    
    public void parseOut() {
        if(this.available != null){
            try {
                if (!localFile.exists()) {
                    localFile.createNewFile();
                }

                YAMLFactory factory = new YAMLFactory();
                ObjectMapper yamlmap = new ObjectMapper(factory);
                yamlmap.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                FileOutputStream fos = new FileOutputStream(localFile);

                factory.createGenerator(fos).writeObject(this.available);
            } catch (IOException ex) {
                Logger.getLogger(GeneratorCore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     public void parseIn(){ 
        if(this.localFile.length() >0){
            try {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                ParseableProgram[] result =   mapper.readValue(this.localFile, ParseableProgram[].class);                             
                for(ParseableProgram ass:result){
                    this.available.add(ass);
                }
            } catch (IOException ex) {
                Logger.getLogger(GeneratorCore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
