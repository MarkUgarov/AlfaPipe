/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class GeneAnnotationParameterFabric {
    
    private final ArrayList<ParseableProgram> defaultList;
    private final int index;
    private final String name;
    
    private final ParseableProgramList parameterList;
            
    public GeneAnnotationParameterFabric(){
        this.defaultList = new ArrayList<>();       
        this.name = ParameterPool.LABEL_ANNOTATION;
        this.index = 4;
       

        String[] endings = {".fa", ".fasta", ".fna"};
        String[] outputEnding = new String[]{".fa"};
        ParseableProgram prodigal = new ParseableProgram( "Prodigal",
                                                                "prodigal", 
                                                                "-i",
                                                                -2,
                                                                "-o",
                                                                -1,
                                                                endings,
                                                                outputEnding);
        prodigal.addParameter("write protein translations to","-a" , ParameterPool.PROGRAM_PATH_VALUE+File.separator+ParameterPool.PROGRAM_NAME_VALUE+"_protein_translation.fa", 1, true, "Choose a file where to write the protein translation.");
        prodigal.addParameter("write nucleotide sequence to", "-d", ParameterPool.PROGRAM_PATH_VALUE+File.separator+ParameterPool.PROGRAM_NAME_VALUE+"_nucleotide_sequence.fa", 2, true, "Choose a file where to write the nucleotide translation.");
        prodigal.setOutputSettings(false, true);
        prodigal.addAdditionalClusterParameter(ParameterPool.PROGRAM_BINARAY_NAME, ParameterPool.PROGRAM_BINARY_COMMAND, ParameterPool.PROGRAM_BINARY_CONFIRM, ParameterPool.PROGRAM_BINARY_POSITION, ParameterPool.PROGRAM_BINARY_OPTIONAL, ParameterPool.PROGRAM_BINARY_DESCRIPTION);
        prodigal.addAdditionalClusterParameter("Export environment variables", "-V", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false, "Specifies that all environment variables active within the  qsub utility be exported to the context of the job.");
        
        ParseableProgram nullAnnotation= new ParseableProgram( "Skip", 
                                                                null, 
                                                                null,
                                                                0,
                                                                null,
                                                                0,
                                                                null,
                                                                null); 
        
        ParseableProgram prokka = new ParseableProgram( "Prokka", 
                                                    "prokka", 
                                                    null,
                                                    -1,
                                                    "--outdir",
                                                    0,
                                                    endings,
                                                    outputEnding);  
        prokka.setOutputSettings(true, false);
        //prokka.setEnterCommand("lqxterm -l vf=128G -cwd ");
        //prokka.setExitCommand("exit");
        prokka.addParameter("Force", "--force", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false);
        prokka.addAdditionalClusterParameter(ParameterPool.PROGRAM_BINARAY_NAME, ParameterPool.PROGRAM_BINARY_COMMAND, ParameterPool.PROGRAM_BINARY_CONFIRM, ParameterPool.PROGRAM_BINARY_POSITION, ParameterPool.PROGRAM_BINARY_OPTIONAL, ParameterPool.PROGRAM_BINARY_DESCRIPTION);
        prokka.addAdditionalClusterParameter("Export environment variables", "-V", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 1, false, "Specifies that all environment variables active within the  qsub utility be exported to the context of the job.");
        
        
        this.defaultList.add(nullAnnotation);
        this.defaultList.add(prodigal);
        this.defaultList.add(prokka);
        
        this.parameterList = new ParseableProgramList();
        this.parameterList.setIndex(this.index);
        this.parameterList.setName(this.name);
        this.parameterList.setPrograms(this.defaultList);
    }
    
    public ParseableProgramList getList(){
        return this.parameterList;
    }
}
