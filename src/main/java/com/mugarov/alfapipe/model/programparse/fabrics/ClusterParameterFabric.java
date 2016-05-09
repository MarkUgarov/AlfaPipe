/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.fabrics;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.programparse.datatypes.ParameterField;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
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
    private final ParameterField waitPar;
    private final ParameterField rerun;
    
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
        
        this.cluster = new ParseableProgram();
        this.cluster.setName(this.clusterName);
        this.cluster.setStartCommand(this.startCommand);
       
        
        this.standardLogfile = new ParameterField("Standard Output Log", "-o",  ParameterPool.PROGRAM_PATH_VALUE+"/clusterlog.txt",1,true);
        this.errorLogfile = new ParameterField("Error Output Log", "-e", ParameterPool.PROGRAM_PATH_VALUE+"/clusterlog.txt", 2, true);
        this.basicOutput = new ParameterField("Basic Output Directory", "-cwd", "", 3, true);
        this.numberOfThreads = new ParameterField("Number of threads", "-pe multislot", "8", 4, true);
        this.ramPerThread = new ParameterField("RAM per thread", "-l vf=", "1G", 5, true);
        this.ramPerThread.setAvoidLeadingSpace(true);
        this.platform = new ParameterField("Platform", "-l arch=", "lx24-amd64", 6, true);
        this.platform.setAvoidLeadingSpace(true);
        this.waitPar = new ParameterField("wait", "-sync", "y", 7, true, "Wait before continue with the next step. (y for yes, n for no)");
        this.rerun = new ParameterField("rerun", "-r", "y", 8, true, "If  the value is  'y',  the  job will be rerun if the job was aborted without leaving a consistent exit state. Type 'n' to avoid.");
        
        this.cluster.addParameter(this.standardLogfile);
        this.cluster.addParameter(this.errorLogfile);
        this.cluster.addParameter(this.basicOutput);
        this.cluster.addParameter(this.numberOfThreads);
        this.cluster.addParameter(this.ramPerThread);
        this.cluster.addParameter(this.platform);
        this.cluster.addParameter(this.waitPar);
        this.cluster.addParameter(this.rerun);
        this.cluster.setOutputSettings(true, true);
        
        this.clusterProgramSet = new ProgramSet(this.cluster);

    }
    
    
    public ParseableProgramList getList(){
        ParseableProgramList programList = new ParseableProgramList();
        programList.setIndex(0);
        programList.setName("Cluster");
        ArrayList<ParseableProgram> programs = (new ArrayList<ParseableProgram>(1));
        programs.add(this.cluster);
        programList.setPrograms(programs);
        return programList;
    }
    
    
}
