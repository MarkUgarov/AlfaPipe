/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class ComparisonParameterFabric {
    
    private final ArrayList<ParseableProgram> defaultList;
    private final int index;
    private final String name;
    
    private final ParseableProgramList parameterList;
            
    public ComparisonParameterFabric(){
        this.defaultList = new ArrayList<>();       
        this.name = ParameterPool.LABEL_COMPARISON;
        this.index = 3;
        
        
        String[] endings = {".fa", ".fq"};
        String[] outputEnding = new String[]{".fa"};
        ParseableProgram readsVsContigs = new ParseableProgram( "Reads vs Contigs",
                                                                null, 
                                                                "-in something",
                                                                -1,
                                                                "-out something",
                                                                0,
                                                                endings,
                                                                outputEnding);
        readsVsContigs.setOnlyOutputDirectorySetable(false);
        readsVsContigs.setEnterCommand("reads vs contigs enter command");
        readsVsContigs.setExitCommand(" reads vs contigs exit command");
        
        ParseableProgram nullReadsVsContigs= new ParseableProgram( "Choose non", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
       
        this.defaultList.add(readsVsContigs);
        this.defaultList.add(nullReadsVsContigs);
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    public ParseableProgramList getList(){
        return this.parameterList;
    }
}
