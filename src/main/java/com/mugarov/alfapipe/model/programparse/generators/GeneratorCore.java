/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
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
    
    private ArrayList<ParseableProgramParameters> available;
    private File localFile;
    private String path;
     
    public GeneratorCore(String path, ArrayList<ParseableProgramParameters> defaultList){
        this.available = new ArrayList<>();
        this.path = path;
       
        this.localFile = new File(this.path);
        if(!localFile.exists()){
            System.out.println("File does not exist! Creating default List. ");
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
 
    public ParseableProgramParameters get(String name){
        ParseableProgramParameters ret = null;
        for(ParseableProgramParameters ass: this.available){
            if(name.equals(ass.getName())){
                ret=ass;
            }
        }
        return ret;
    }
    
    public void parseOut() {
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
            Logger.getLogger(AssemblerGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void parseIn(){ 
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            ParseableProgramParameters[] result =   mapper.readValue(this.localFile, ParseableProgramParameters[].class);                             
            for(ParseableProgramParameters ass:result){
                this.available.add(ass);
            }
        } catch (IOException ex) {
            Logger.getLogger(AssemblerGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
