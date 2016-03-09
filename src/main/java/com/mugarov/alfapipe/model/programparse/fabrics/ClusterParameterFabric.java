/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class ClusterParameterFabric{
    
    private final String clusterName;
    private final String startCommand;
    private final String inputPathCommand;
    private final int inputPathPosition;
    private final String outputPathCommand;
    private final int outputPathPosition;
    private final String[] validEndings;
    private final String[] outputEndings;
    
    private final ParseableProgram cluster;
    private final ParameterField standardLogfile;
    private final ParameterField errorLogfile;
    private final ParameterField basicOutput;
    private final ParameterField numberOfThreads;
    private final ParameterField ramPerThread;
    private final ParameterField platform;
    
    private final ProgramSet clusterProgramSet;
    
    public ClusterParameterFabric(){
        this.clusterName = "Cluster";
        this.startCommand = "qsub";
        this.inputPathCommand = null;
        this.inputPathPosition = 0;
        this.outputPathCommand = null;
        this.outputPathPosition = 0;
        this.validEndings = null;
        this.outputEndings = null;
        this.cluster = new ParseableProgram(this.clusterName, this.startCommand, this.inputPathCommand, this.inputPathPosition, this.outputPathCommand, this.outputPathPosition, this.validEndings, this.outputEndings);
        
        this.standardLogfile = new ParameterField("Standard Output Log", "-o", "[path]/clusterlog",1,true);
        this.errorLogfile = new ParameterField("Error Output Log", "-e", "[path]/clusterlog", 2, true);
        this.basicOutput = new ParameterField("Basic Output Directory", "-cwd", "", 3, true);
        this.numberOfThreads = new ParameterField("Number of threads", "-pe multislot", "8", 4, true);
        this.ramPerThread = new ParameterField("RAM per thread", "-l vf=", "1G", 5, true);
        this.ramPerThread.setAvoidLeadingSpace(true);
        this.platform = new ParameterField("Platform", "-l arch=lx24-amd64", ParameterPool.PROGRAM_EMPTY_PARAMETER_VALUE, 6, true);
        
        this.cluster.addParameter(this.standardLogfile);
        this.cluster.addParameter(this.errorLogfile);
        this.cluster.addParameter(this.basicOutput);
        this.cluster.addParameter(this.numberOfThreads);
        this.cluster.addParameter(this.ramPerThread);
        this.cluster.addParameter(this.platform);
        
        this.clusterProgramSet = new ProgramSet(this.cluster);

    }
    
    public ProgramSet getSet(){
        return this.clusterProgramSet;
    }
    
    
}
