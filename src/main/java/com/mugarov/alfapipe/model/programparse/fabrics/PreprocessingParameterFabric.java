/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import com.mugarov.alfapipe.model.programparse.datatypes.PairedInputConditions;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class PreprocessingParameterFabric {
    
    private final ArrayList<ParseableProgram> defaultList;
    private final int index;
    private final String name;
    
    private final ParseableProgramList parameterList;
            
    public PreprocessingParameterFabric(){
        this.defaultList = new ArrayList<>();       
        this.name = ParameterPool.LABEL_PREPROCESSING;
        this.index = 0;
        String[] endings = {".fastq.gz"};
        String[] outputEnding = new String[]{".fastq"};
        ParseableProgram mainProcessor = new ParseableProgram("gzip",
                                                                "SCRIPTS/gzipPaired.sh", 
                                                                null,
                                                                0,
                                                                null,
                                                                1,
                                                                endings,
                                                                outputEnding);
        mainProcessor.setOutputSettings(true, true);
        NameField essential = new NameField();
        essential.setDynamic(false);
        essential.setName("Unziped_R1_001.fastq");
        essential.setEssentialFor("MiSeqFASTQ4Newbler_v2.8b");
        essential.setUseOnly(true);
        ArrayList<NameField> fields = new ArrayList<>();
        fields.add(essential);
        mainProcessor.setEssentialOutputs(fields);
        mainProcessor.setPairedConditions(new PairedInputConditions(true, "_",-2));
        
        ParseableProgram nullPreProcessor= new ParseableProgram( "Skip", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
        this.defaultList.add(mainProcessor);
        this.defaultList.add(nullPreProcessor);
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    public ParseableProgramList getList(){
        return this.parameterList;
    }
    
        
}
