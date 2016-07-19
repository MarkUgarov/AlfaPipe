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
        ParseableProgram unzipPaired = new ParseableProgram("unzip paired",
                                                                ParameterPool.FILE_SCRIPT_PATH+"gzipPaired.sh", 
                                                                null,
                                                                0,
                                                                null,
                                                                1,
                                                                endings,
                                                                outputEnding);
        unzipPaired.setOutputSettings(true, true);
        unzipPaired.setRemoveFilesAfterSetCompletion(true);
        NameField essentialPaired = new NameField();
        essentialPaired.setDynamic(false);
        essentialPaired.setFileName("Unziped_R1_001.fastq");
        essentialPaired.setEssentialFor("MiSeqFASTQ for Newbler");
        essentialPaired.setUseOnly(true);
        ArrayList<NameField> fields = new ArrayList<>();
        fields.add(essentialPaired);
        unzipPaired.setEssentialOutputs(fields);
        unzipPaired.setPairedConditions(new PairedInputConditions(true, "_",-2));
        
        String[] endings2 = {".fastq.zip"};
        ParseableProgram unzipBundle = new ParseableProgram("unzip bundle",
                                                                ParameterPool.FILE_SCRIPT_PATH+"unzipBundle.sh", 
                                                                null,
                                                                0,
                                                                null,
                                                                1,
                                                                endings2,
                                                                outputEnding);
        unzipBundle.setOutputSettings(true, false);
        unzipBundle.setRemoveFilesAfterSetCompletion(true);
        NameField essentialBundle = new NameField();
        essentialBundle.setUseAll(true);
        essentialBundle.setEssentialFor("MiSeqFASTQ for Newbler");
        essentialBundle.setUseOnly(true);
        ArrayList<NameField> fieldsB = new ArrayList<>();
        fieldsB.add(essentialBundle);
        unzipBundle.setEssentialOutputs(fieldsB);
        unzipBundle.setPairedConditions(new PairedInputConditions(false, "_",-2));
        
        ParseableProgram nullPreProcessor= new ParseableProgram( "Skip", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
        
        
        this.defaultList.add(nullPreProcessor);
        this.defaultList.add(unzipPaired);
        this.defaultList.add(unzipBundle);
        
        
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    public ParseableProgramList getList(){
        return this.parameterList;
    }
    
        
}
