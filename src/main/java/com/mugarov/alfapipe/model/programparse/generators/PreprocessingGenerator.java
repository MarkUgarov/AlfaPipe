/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.NameField;
import com.mugarov.alfapipe.model.programparse.datatypes.PairedInputConditions;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class PreprocessingGenerator implements Generator{
    private final String localFilePath;
    private final GeneratorCore core;
    private final ArrayList<ParseableProgram> defaultList;
     
    public PreprocessingGenerator(){
        this.defaultList = new ArrayList<>();       
        this.localFilePath = ParameterPool.PATH_PREPROCESSING_LIST;

        String[] endings = {".fastq.gz"};
        String[] outputEnding = new String[]{".fastq"};
        ParseableProgram mainProcessor = new ParseableProgram("gzip",
                                                                "sh SCRITPS/gzipPaired.sh", 
                                                                null,
                                                                0,
                                                                null,
                                                                1,
                                                                endings,
                                                                outputEnding);
        mainProcessor.setOnlyOutputDirectorySetable(true);
        NameField essential = new NameField();
        essential.setDynamic(false);
        essential.setName("Unziped_R1_001.fastq");
        essential.setEssentialFor("MiSeqFASTQ4Newbler_v2.8b");
        essential.setUseOnly(true);
        ArrayList<NameField> fields = new ArrayList<>();
        fields.add(essential);
        mainProcessor.setEssentialOutputs(fields);
        mainProcessor.setPairedConditions(new PairedInputConditions(true, "_",-2));
        
        ParseableProgram nullPreProcessor= new ParseableProgram( "Choose non", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
        this.defaultList.add(mainProcessor);
        this.defaultList.add(nullPreProcessor);

        this.core = new GeneratorCore(this.localFilePath, this.defaultList);
    }
    
    @Override
    public String[] getAvailableNames(){
        return this.core.getAvailableNames();
    }
 
    @Override
    public ParseableProgram get(String name){
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
