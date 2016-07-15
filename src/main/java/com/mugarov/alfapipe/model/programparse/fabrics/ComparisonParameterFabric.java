/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
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
        
        
        String[] endings = ParameterPool.ENDINGS_FASTA;
        ParseableProgram neview = new ParseableProgram();
        neview.setName("NeView");
        neview.setDisableCluster(true);
        neview.setSkipWaiting(true);
        neview.setValidInputEndings(endings);
        neview.setStartCommand("java -jar /vol/ampipe/data/NeView/NeView-0.0.2.jar");
        neview.setInputPathCommand(null, 1);
        neview.setOutputSettings(true, false);
        neview.setRemoveFilesAfterSetCompletion(true);
        neview.addParameter("Name", null, ParameterPool.PROGRAM_NAME_VALUE, 0, true, "Sets a name for the chart.");
        neview.setOutputSettings(false, false);
        
        ParseableProgram nullReadsVsContigs= new ParseableProgram( "Skip", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
       
        this.defaultList.add(nullReadsVsContigs);
        this.defaultList.add(neview);
        
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    public ParseableProgramList getList(){
        return this.parameterList;
    }
}
