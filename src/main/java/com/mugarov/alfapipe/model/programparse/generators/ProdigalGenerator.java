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
 * @author Mark
 */
public class ProdigalGenerator implements Generator{
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgramParameters> defaultList;
     
    public ProdigalGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = Pool.PATH_PRODIGAL_LIST;

        String[] endings = {".fa", ".fq"};
        String[] outputEnding = new String[]{".fa"};
        ParseableProgramParameters prodigal = new ParseableProgramParameters( Pool.NAME_DEFAULT_PRODIGAL,
                                                                "run prodigal", 
                                                                "-in something",
                                                                -1,
                                                                "-out something",
                                                                0,
                                                                endings,
                                                                outputEnding);
        prodigal.setOnlyOutputDirectorySetable(false);
        prodigal.setEnterCommand("prodigal enter command");
        prodigal.setExitCommand("prodigal exit command");
       
        this.defaultList.add(prodigal);

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
