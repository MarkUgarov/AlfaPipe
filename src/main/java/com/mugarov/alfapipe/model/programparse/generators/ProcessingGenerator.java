/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.datatypes.Parseable;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class ProcessingGenerator implements Generator{
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<Parseable> defaultList;
     
    public ProcessingGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = Pool.PATH_PROCESSING_LIST;

        String[] endings = {".fa", ".fq"};
        String outputEnding = ".fa";
        Parseable mainProcessor = new Parseable( Pool.NAME_DEFAULT_PROCESSING,
                                                                "run processing", 
                                                                "-insomething",
                                                                -1,
                                                                "-outsomething",
                                                                0,
                                                                endings,
                                                                outputEnding);
        mainProcessor.setOnlyOutputDirectorySetable(false);
        mainProcessor.setEnterCommand("processing enter command");
        mainProcessor.setExitCommand("processing exit command");
       
        this.defaultList.add(mainProcessor);

        this.core = new GeneratorCore(this.localFilePath, this.defaultList);
    }
    
    @Override
    public String[] getAvailableNames(){
        return this.core.getAvailableNames();
    }
 
    @Override
    public Parseable get(String name){
        return this.core.get(name);
    }
    
    @Override
    public void parseOut() {
        this.core.parseOut();
    }
    
    @Override
     public void parseIn(){ 
        this.core.parseIn();
    }
}
