/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import com.mugarov.alfapipe.model.programparse.generators.GeneratorCore;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class ProcessingParameterFabric {
    
    private final ArrayList<ParseableProgram> defaultList;
    private final int index;
    private final String name;
    
    private final ParseableProgramList parameterList;
    
    public ProcessingParameterFabric(){
        this.defaultList = new ArrayList<>();       
        this.index = 1;
        this.name = ParameterPool.LABEL_PROCESSING;


        String[] endings = {".fastq"};
        String[] outputEnding = new String[]{".fa"};
        ParseableProgram miseq = new ParseableProgram( "MiSeqFASTQ4Newbler_v2.8b",
                                                                "SCRITPS/MiSeqFASTQ4Newbler_v2.8b_OutputOnArgs1.pl", 
                                                                null,
                                                                1,
                                                                null,
                                                                2,
                                                                endings,
                                                                outputEnding);
        miseq.setOnlyOutputDirectorySetable(false);
        
        ParseableProgram splitMiseq = new ParseableProgram( "split_MiSeq_MP_nextera",
                                                                "SCRITPS/split_MiSeq_MP_nextera_OutputOnArgs1.pl", 
                                                                null,
                                                                1,
                                                                null,
                                                                2,
                                                                endings,
                                                                outputEnding);
        splitMiseq.setOnlyOutputDirectorySetable(false);
        
        ParseableProgram nullProcessor= new ParseableProgram( "Skip", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
       
        this.defaultList.add(miseq);
        this.defaultList.add(splitMiseq);
        this.defaultList.add(nullProcessor);
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    public ParseableProgramList getList(){
        return this.parameterList;
    }
}
