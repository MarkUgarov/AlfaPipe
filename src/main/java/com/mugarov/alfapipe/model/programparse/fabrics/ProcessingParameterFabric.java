/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.PairedInputConditions;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.io.File;
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
        String[] outputEnding = new String[]{".fastq"};
        ParseableProgram miseq = new ParseableProgram( "MiSeqFASTQ for Newbler",
                                                                ParameterPool.FILE_SCRIPT_PATH+"MiSeqFASTQ4Newbler_v2.8b_OutputOnArgs1.pl", 
                                                                null,
                                                                1,
                                                                null,
                                                                2,
                                                                endings,
                                                                outputEnding);
        miseq.addEssentialOutput(ParameterPool.PROGRAM_FILE_VALUE, "Newbler");
        miseq.addAdditionalClusterParameter(ParameterPool.PROGRAM_BINARAY_NAME, ParameterPool.PROGRAM_BINARY_COMMAND, ParameterPool.PROGRAM_BINARY_CONFIRM, ParameterPool.PROGRAM_BINARY_POSITION, ParameterPool.PROGRAM_BINARY_OPTIONAL, ParameterPool.PROGRAM_BINARY_DESCRIPTION);
        miseq.setOutputSettings(false, true);
        miseq.setPairedConditions(new PairedInputConditions());
        
        ParseableProgram hiseq = new ParseableProgram( "HiSeqFASTQ for Newbler",
                                                                ParameterPool.FILE_SCRIPT_PATH+"HiSeq_MP_Newbler_InDirOnArgs0_OutDirOnArgs1_nameOnArgs2.sh", 
                                                                null,
                                                                1,
                                                                null,
                                                                2,
                                                                endings,
                                                                outputEnding);
        hiseq.addEssentialOutput(".+\\.fastq", "Newbler");
        hiseq.addParameter("Name", null , ParameterPool.PROGRAM_NAME_VALUE, 3, true);
        hiseq.setPairedConditions(new PairedInputConditions(false,"_",-2));
        hiseq.addLocalPrependParameter("Run on Shell", "sh", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false, "Starts this as a shell script.");
        //hiseq.addAdditionalClusterParameter(ParameterPool.PROGRAM_BINARAY_NAME, ParameterPool.PROGRAM_BINARY_COMMAND, ParameterPool.PROGRAM_BINARY_CONFIRM, ParameterPool.PROGRAM_BINARY_POSITION, ParameterPool.PROGRAM_BINARY_OPTIONAL, ParameterPool.PROGRAM_BINARY_DESCRIPTION);
        hiseq.setOutputSettings(true, true);
       
        
        ParseableProgram splitMiseq = new ParseableProgram( "split MiSeq MP nextera",
                                                                ParameterPool.FILE_SCRIPT_PATH+"split_MiSeq_MP_nextera_OutputOnArgs1.pl", 
                                                                null,
                                                                1,
                                                                null,
                                                                2,
                                                                endings,
                                                                outputEnding);
        splitMiseq.addEssentialOutput(ParameterPool.PROGRAM_FILE_VALUE, "Newbler");
        splitMiseq.addAdditionalClusterParameter(ParameterPool.PROGRAM_BINARAY_NAME, ParameterPool.PROGRAM_BINARY_COMMAND, ParameterPool.PROGRAM_BINARY_CONFIRM, ParameterPool.PROGRAM_BINARY_POSITION, ParameterPool.PROGRAM_BINARY_OPTIONAL, ParameterPool.PROGRAM_BINARY_DESCRIPTION);
        splitMiseq.setOutputSettings(false, true);
        
        ParseableProgram nullProcessor= new ParseableProgram( "Skip", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
        this.defaultList.add(nullProcessor);
        this.defaultList.add(miseq);
        this.defaultList.add(hiseq);
        this.defaultList.add(splitMiseq);
        
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    public ParseableProgramList getList(){
        return this.parameterList;
    }
}
