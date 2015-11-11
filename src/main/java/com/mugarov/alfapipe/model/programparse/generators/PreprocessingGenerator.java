/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class PreprocessingGenerator implements Generator{
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgramParameters> defaultList;
     
    public PreprocessingGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = Pool.PATH_PREPROCESSING_LIST;

        String[] endings = {".fa", ".fq"};
        String[] outputEnding = new String[]{".fa"};
        ParseableProgramParameters mainProcessor = new ParseableProgramParameters("gzip",
                                                                "gzip", 
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
    public ParseableProgramParameters get(String name){
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
