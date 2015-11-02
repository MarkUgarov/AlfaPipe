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
public class AssemblerGenerator implements Generator{
    
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<Parseable> defaultList;
     
    public AssemblerGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = Pool.PATH_ASSEMBLER_LIST;

        String[] endings = {".fa", ".fq"};
        String outputEnding = ".fa";
        Parseable newbler = new Parseable( "Newbler",
                                                                "runAssembler", 
                                                                "-p",
                                                                -1,
                                                                "-o",
                                                                0,
                                                                endings,
                                                                outputEnding);
        newbler.setOnlyOutputDirectorySetable(true);
        newbler.setEnterCommand("newbler enter command");
        newbler.setExitCommand("newbler exit command");
        Parseable allpath=  new Parseable( "Allpath",
                                                                "apply startCommand", 
                                                                "apply inputPathCommand",
                                                                -1,
                                                                "apply outputPathCommand",
                                                                0,
                                                                endings,
                                                                outputEnding);
        Parseable nullAss= new Parseable( "Choose non", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
        this.defaultList.add(newbler);
        this.defaultList.add(allpath);
        this.defaultList.add(nullAss);
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
