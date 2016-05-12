/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.singlefile.SingleFileListener;
import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.ExecutionCommandBuilder;
import com.mugarov.alfapipe.model.Executioner;
import com.mugarov.alfapipe.model.LogFileManager;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import com.mugarov.alfapipe.view.MultiFileChooser;
import com.mugarov.alfapipe.view.OutputDirectoryChooser;
import com.mugarov.alfapipe.view.mainview.tab.Tab;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author Mark
 */
public class SetOfFiles implements Executable, Runnable{
    private ArrayList<InputFile> files;
    private final String id;
    private String name;
    
    private final MultiFileChooser inputChooser;
    private final OutputDirectoryChooser outputChooser;
    
    private ProgramSet clusterParameters;
    
    private File outputDirectory;
    private final LogFileManager log;
    private final ArrayList<ProgramSet> usedPrograms;
    private final boolean[] useCluster;
    private final boolean[] useClusterOnTool;

    private final ArrayList<ProgramSet> availableTools;
    
    private final Tab tab;
    
    private Executioner execution;
    private boolean started;
    
    public SetOfFiles(String id, Tab tab){
        this.id = id;
        this.name = id;
        this.outputDirectory = new File(ParameterPool.FILE_ORIGIN_DEFAULT, id);
        this.files = new ArrayList<>();
        this.tab = tab;
        this.inputChooser = new MultiFileChooser();
        this.outputChooser = new OutputDirectoryChooser();
        this.tab.setOutputPath(this.outputDirectory.getAbsolutePath());
        this.log = new LogFileManager(this.outputDirectory.getAbsolutePath());
        this.log.appendLine("Log of the Set "+this.id, SetOfFiles.class.getName());
        this.usedPrograms = new ArrayList<>();
        this.availableTools = new ArrayList<>();
        int i=0;
        this.useCluster = new boolean[ComponentPool.PROGRAM_GENERATOR.getAllPrograms().size()];
        this.useClusterOnTool = new boolean[ComponentPool.GENERTATOR_TOOLS.getAvailableNames().length];
        for(String toolName: ComponentPool.GENERTATOR_TOOLS.getAvailableNames()){
            this.availableTools.add(new ProgramSet(ComponentPool.GENERTATOR_TOOLS.get(toolName)));
            this.useCluster[i]=false;
            i++;
        }
        i =0;
        for(ProgramSet par:this.availableTools){
            ParameterListener paramListener = new ParameterListener(par.getInputParameters());
            this.tab.addTool(par.getName(), par.getInputParameters(), paramListener);
            if(par.getParsedParameters().isDisableCluster()){
                this.tab.disableCluster(i, true);
            }
            this.useClusterOnTool[i]=false;
            i++;
        }
        this.tab.recalculateSize();
        this.started =false;
    }

    
    public void addFiles(File[] additionalFiles){
        for(File file:additionalFiles){
            boolean add= true;
            int i=0;
            while(add && i<this.files.size()){
                InputFile old = this.files.get(i);
                if(old.getAbsolutePath().equals(file.getAbsolutePath())){
                    add = false;
                }
                else if(old.shouldBePairedWith(file)){
                    old.addPairedFile(file);
                    this.tab.addPaired(old.getAbsolutePath(), file.getName());
                    add = false;
                }
                i++;
            }
            if(add){
                InputFile inFile = new InputFile(file.getPath(), this.availableTools, this.log);
                for(int j = 0; j<this.usedPrograms.size(); j++){
                    inFile.addProgram(this.usedPrograms.get(j));
                }
                this.files.add(inFile);
                this.tab.addFile(file.getAbsolutePath(),file.getName(), new SingleFileListener(inFile, this));
                this.tab.setValidation(inFile.getAbsolutePath(), inFile.validateFile(), inFile.getValidTools());
            }
            
        }

    }
    
    
    public void deleteFile(String path){
        for(int i=0; i<this.files.size(); i++){
            if (this.files.get(i).getPath().equals(path)|| !this.files.get(i).exists() ){
                this.files.remove(i);
            }
        }
        this.tab.deleteFile(path);
    }
    
    public void selectToolForAll(String toolName){
        this.tab.selectToolForAll(toolName);
        // Hint: the SingleFileListener will cause the InputFile to change their 
        // tools 
    }
    
    public void unselectToolForAll(String toolName){
        this.tab.unselectToolForAll(toolName);
        // Hint: the SingleFileListener will cause the InputFile to change their 
        // tools 
    }
    
    public void setProgram(int index, ParseableProgram proc){
        ProgramSet set = new ProgramSet(proc);
        if(index>=this.usedPrograms.size()){
            this.usedPrograms.add(set);
        }
        else{
            this.usedPrograms.remove(index);
            this.usedPrograms.add(index,set);
        }
        ParameterListener paramListener = new ParameterListener(set.getInputParameters());
        
        this.tab.selectProgram(index, set.getName(), set.getInputParameters(), paramListener);
        for(InputFile file:this.files){
            file.selectProgram(index, set, true);
            this.tab.setValidation(file.getAbsolutePath(), file.isValid(), file.getValidTools());
        }
        
        if(proc.isDisableCluster()){
            this.useCluster[index]= false;
            this.tab.disableCluster(index, false);
        }
        else{
            this.tab.reenableCluster(index, false);
        }
    }
    
    public void deleteAll(){
        for(File f:this.files){
            this.tab.deleteFile(f.getAbsolutePath());
        }
        this.files = new ArrayList<>();
    }
    
    public void setName(String arg){
        this.tab.setName(arg);
        this.name = arg;
        this.outputDirectory = new File(this.outputDirectory.getParent(), this.name);
        this.log.setParentDirectory(this.outputDirectory.getAbsolutePath());
        this.tab.setOutputPath(this.outputDirectory.getAbsolutePath());
    }
    
    
    public String getName(){
        return this.name;
    }

    public void showOpenDialog() {
        this.checkInputFormat();
        int returnVal =this.inputChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.addFiles(this.inputChooser.getSelectedFiles());

        } else {
        }
    }
    
    public void showOuputDirDialog(){
        int returnVal = this.outputChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            this.outputDirectory=new File(this.outputChooser.getSelectedFile(), this.name);
            this.tab.setOutputPath(this.outputDirectory.getAbsolutePath());
            this.log.setParentDirectory(this.outputDirectory.getAbsolutePath());
        }
        else{
        }
        
    }
    
    public String getID(){
        return this.id;
    }

    public void applyName() {
        this.setName(this.tab.getCustumName());
    }

    
    public String getOutputPath(){
        if(!this.outputDirectory.exists()||!this.outputDirectory.isDirectory()){
            this.outputDirectory.mkdirs();
        }
        return this.outputDirectory.getAbsolutePath();
    }
    
    @Override
    public String getProgramCommand(int index, String parentOutputDir){
        StringBuilder builder = new StringBuilder();
         for(InputFile file: this.files){
            if(file.getProgramCommand(index, parentOutputDir) == null){
                builder.append("echo Null");
            }
            else{
                builder.append(file.getProgramCommand(index, parentOutputDir));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
    

    /**
     * This method only returns commands; to execute you may prefer to start this Runnable.
     * @param parentOutputDir
     * @return 
     */
    @Override
    public ArrayList<String> getToolCommands(String parentOutputDir) {
        ArrayList<String> ret = new ArrayList<>();
        for(InputFile file: this.files){
            if(file.getToolCommands(parentOutputDir) == null){
                ret.add("echo Null");
            }
            else{
                for (String s:file.getToolCommands(parentOutputDir)){
                    ret.add(s);
                }
                ret.add("\n");
            }
        }
        return ret;
    }

    @Override
    public String getLog() {
        return this.log.toString();
    }
    
    public boolean runs(){
        return this.started;
    }

    @Override
    public void run() {
        if(!this.started){
            this.started = true;
            System.out.println("Set "+this.name+ " has started.");
            this.tab.disableEditing();
            execution = new Executioner(this.log);
            ExecutionCommandBuilder clusterCommandBuilder = new ExecutionCommandBuilder(this.log);
            String clusterCommand = null;
            if(ParameterPool.CLUSTER_ENABLE && this.clusterParameters != null){
                clusterCommandBuilder.buildString(this.clusterParameters, this.outputDirectory, this.outputDirectory.getPath(), null, this.outputDirectory, false);
                clusterCommand = clusterCommandBuilder.getExecutionCommand();
            }
            
            for(InputFile file:this.files){
                boolean filePassed=true;
                boolean essentialFilesExist = true;
                boolean wait;
                String command;
                for(int i = 0; (i<this.usedPrograms.size() && filePassed); i++){
                   wait = !this.usedPrograms.get(i).getParsedParameters().isSkipWaiting();
                    if(ParameterPool.CLUSTER_ENABLE && this.useCluster[i] && clusterCommand != null && this.usedPrograms.get(i).getParsedParameters().getStartCommand() != null){
//                        System.out.println("Use program "+i+" on Cluster!");
                        command =  file.getProgramCommand(i, this.outputDirectory.getPath(),true);
                        filePassed = execution.execute(clusterCommand,command, wait);
                    }
                    else{
                        command =  file.getProgramCommand(i, this.outputDirectory.getPath(),false);
                        filePassed = execution.execute(null, command, wait);
                    }
                    if(command != null){
                        essentialFilesExist = (essentialFilesExist && file.checkLastCommandFiles());
                    }
                    if(!filePassed){
                        String error= "File did not pass! Stopped at program "+this.usedPrograms.get(i).getName()+" with index "+i;
                        System.err.println(error);
                        this.log.appendLine(error, SetOfFiles.class.getName());
                        
                    }
                }
                this.tab.setFileProgressed(file.getAbsolutePath(), filePassed && essentialFilesExist);
                ArrayList<Boolean> toolBools = new ArrayList<>(file.getValidTools().size());
                int toolIndex = 0;
                for(String p:file.getToolCommands(this.outputDirectory.getPath(),this.useClusterOnTool)){
                    wait = !this.availableTools.get(toolIndex).getParsedParameters().isSkipWaiting();
                    if(p == null){
                        toolBools.add(true);
                    }
                    if(ParameterPool.CLUSTER_ENABLE && this.useClusterOnTool[toolIndex] && clusterCommand != null){
                        toolBools.add(execution.execute(clusterCommand, p,wait) && file.checkToolFiles(toolIndex));
                    }
                    else{
                        toolBools.add(execution.execute(null, p,wait) && file.checkToolFiles(toolIndex));
                    }
                    
                    toolIndex++;
                }
                this.tab.setToolProgressed(file.getAbsolutePath(), file.getValidTools(), toolBools);
            }
            this.tab.setAllProgressed(true);
            this.tab.reenableSetRemoval();
        }
        else{
            System.out.println("Set "+this.name+" allready progressed.");
        }
        this.started = false;
    }
    
    private void checkInputFormat(){
        boolean defined = false;
        ProgramSet set;
        for(int i = 0; (i<this.usedPrograms.size() && !defined); i++){
            set = this.usedPrograms.get(i);
            if(set.getParsedParameters().getStartCommand() != null){
                this.inputChooser.setInputfilter(set.getName(), set.getParsedParameters().getValidInputEndings());
                defined = true;
            }
        }
        if(!defined){
            this.inputChooser.setInputfilter("All", null);
            defined = true;
        }
    }
    
    public void setClusterParameters(ProgramSet set){
        this.clusterParameters = set;
    }

    public void selectClusterFor(int index, boolean forTool) {
        if(forTool){
            this.log.appendLine("Select Cluster for tool "+index, SetOfFiles.class.getName());
            this.useClusterOnTool[index] = true;
        }
        else{
            this.log.appendLine("Select Cluster for program "+index, SetOfFiles.class.getName());
            this.useCluster[index] = true;
        }
        
    }

    public void unselectClusterFor(int index, boolean forTool) {
        if(forTool){
            this.log.appendLine("Unselect Cluster for tool "+index, SetOfFiles.class.getName());
            this.useClusterOnTool[index] = false;
        }
        else{
            this.log.appendLine("Unselect Cluster for program "+index, SetOfFiles.class.getName());
            this.useCluster[index] = false;
        }
    }
    
    public void interrupt(){
        if(this.execution != null){
            this.execution.interrupt();
        }
        this.started = false;
    }
    
    
}
