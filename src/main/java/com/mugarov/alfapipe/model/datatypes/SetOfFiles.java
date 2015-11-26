/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.singlefile.SingleFileListener;
import com.mugarov.alfapipe.model.Executioner;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import com.mugarov.alfapipe.view.MultiFileChooser;
import com.mugarov.alfapipe.view.OutputDirectoryChooser;
import com.mugarov.alfapipe.view.mainview.tab.Tab;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private File outputDirectory;
    private ProgramParameterSet usePreprocessing;
    private ProgramParameterSet useProcessing;
    private ProgramParameterSet useAssembler;
    private ArrayList<ProgramParameterSet> availableTools;
    
    private final Tab tab;
    
    private StringBuilder log;
    
    private boolean started;
    
    public SetOfFiles(String id, Tab tab){
        this.useAssembler = null;
        this.useProcessing = null;
        this.id = id;
        this.name = id;
        this.outputDirectory = new File(Pool.FILE_ORIGIN_DEFAULT, id);
        this.files = new ArrayList<>();
        this.tab = tab;
        this.inputChooser = new MultiFileChooser();
        this.outputChooser = new OutputDirectoryChooser();
        this.tab.setOutputPath(this.outputDirectory.getAbsolutePath());
        this.log = new StringBuilder();
        this.log.append("Log of the Set "+this.id);
        this.availableTools = new ArrayList<>();
        for(String toolName: Pool.GENERTATOR_TOOLS.getAvailableNames()){
            this.availableTools.add(new ProgramParameterSet(Pool.GENERTATOR_TOOLS.get(toolName)));
        }
       
        for(ProgramParameterSet par:this.availableTools){
            ParameterListener paramListener = new ParameterListener(par.getInputParameters());
            this.tab.addProgram(par.getName(), par.getInputParameters(), paramListener);
        }
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
                    add = false;
                }
                i++;
            }
            if(add){
                InputFile inFile = new InputFile(file.getPath(), this.availableTools);
                inFile.selectPreprocessing(this.usePreprocessing, false);
                inFile.selectProcessing(this.useProcessing,false); 
                inFile.selectAssembler(this.useAssembler,false); 
                this.files.add(inFile);
                this.tab.addFile(file.getAbsolutePath(),file.getName(), new SingleFileListener(inFile, this));
                this.tab.setValidation(inFile.getAbsolutePath(), inFile.validateFile(), inFile.getValidTools());
            }
            
        }
//        System.out.println("Files are now:");
//        for(InputFile f: this.files){
//            System.out.println("\t"+f.getName());
//        }
    }
    
    public void deleteFile(String path){
//        System.out.println("Set tries to find and delete "+path);
        for(int i=0; i<this.files.size(); i++){
            if (this.files.get(i).getPath().equals(path)|| !this.files.get(i).exists() ){
                this.files.remove(i);
            }
        }
        this.tab.deleteFile(path);
//        System.out.println("Files are now:");
//        for(InputFile f: this.files){
//            System.out.println("\t"+f.getName());
//        }
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
    
    public void setPreprocessing(ParseableProgramParameters proc){
        this.usePreprocessing = new ProgramParameterSet(proc);
        for(InputFile file: this.files){
            file.selectPreprocessing(this.usePreprocessing,true);
            this.tab.setValidation(file.getAbsolutePath(), file.isValid(), file.getValidTools());
        }
    }
    
    public void setProcessing(ParseableProgramParameters proc){
        this.useProcessing = new ProgramParameterSet(proc);
        for(InputFile file: this.files){
            file.selectProcessing(this.useProcessing,true);
            this.tab.setValidation(file.getAbsolutePath(), file.isValid(), file.getValidTools());
        }
    }
    
    public void setAssembler(ParseableProgramParameters ass){
        this.useAssembler = new ProgramParameterSet(ass);
        ParameterListener paramListener = new ParameterListener(this.useAssembler.getInputParameters());
        this.tab.setAssembler(this.useAssembler.getName(),this.useAssembler.getInputParameters(), paramListener );
        for(InputFile file: files){
            file.selectAssembler(this.useAssembler,true);
            this.tab.setValidation(file.getAbsolutePath(), file.isValid(), file.getValidTools());
        }
    }
    
    
    
    public void deleteAll(){
        this.files = new ArrayList<>();
    }
    
    public void setName(String arg){
        this.tab.setName(arg);
        this.name = arg;
        this.outputDirectory = new File(this.outputDirectory.getParent(), this.name);
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
//            System.out.println("File Choosing was canceled by user.");
        }
    }
    
    public void showOuputDirDialog(){
        int returnVal = this.outputChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            this.outputDirectory=new File(this.outputChooser.getSelectedFile(), this.name);
            this.tab.setOutputPath(this.outputDirectory.getAbsolutePath());
        }
        else{
//            System.out.println("Output-Directory was not changed.");
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
    
     /**
     * This method only returns commands; to execute you may prefer to start this Runnable.
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getPreprocessingCommand(String parentOutputDir) {
        StringBuilder builder = new StringBuilder();
         for(InputFile file: this.files){
            if(file.getPreprocessingCommand(parentOutputDir) == null){
                builder.append("echo Null");
            }
            else{
                builder.append(file.getPreprocessingCommand(parentOutputDir));
            }
            builder.append("\n");
            this.log.append(file.getPreprocessingCommand(parentOutputDir).replaceAll("\n", "\n \t"));
            this.log.append("\n");
        }
        return builder.toString();
    }
    

    /**
     * This method only returns commands; to execute you may prefer to start this Runnable.
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getProcessingCommand(String parentOutputDir) {
        StringBuilder builder = new StringBuilder();
         for(InputFile file: this.files){
            if(file.getProcessingCommand(parentOutputDir) == null){
                builder.append("echo Null");
            }
            else{
                builder.append(file.getProcessingCommand(parentOutputDir));
            }
            builder.append("\n");
            this.log.append(file.getProcessingCommand(parentOutputDir).replaceAll("\n", "\n \t"));
            this.log.append("\n");
        }
        return builder.toString();
    }

    /**
     * This method only returns commands; to execute you may prefer to start this Runnable.
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getAssemblerCommand(String parentOutputDir) {
        StringBuilder builder = new StringBuilder();
         for(InputFile file: this.files){  
            if(file.getAssemblerCommand(parentOutputDir) == null){
                builder.append("echo Null");
            }
            else{
                builder.append(file.getAssemblerCommand(parentOutputDir));
            }
            builder.append("\n");
            this.log.append(file.getAssemblerCommand(parentOutputDir).replaceAll("\n", "\n \t"));
            this.log.append("\n");
        }
        return builder.toString();
    }

    /**
     * This method only returns commands; to execute you may prefer to start this Runnable.
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getReadsVsContigsCommand(String parentOutputDir) {
        StringBuilder builder = new StringBuilder();
         for(InputFile file: this.files){
            if(file.getReadsVsContigsCommand(parentOutputDir) == null){
                builder.append("echo Null");
            }
            else{
                builder.append(file.getReadsVsContigsCommand(parentOutputDir));
            }
            builder.append("\n");
            this.log.append(file.getReadsVsContigsCommand(parentOutputDir).replaceAll("\n", "\n \t"));
            this.log.append("\n");
        }
        return builder.toString();
    }

    /**
     * This method only returns commands; to execute you may prefer to start this Runnable.
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getProdigalCommand(String parentOutputDir) {
        StringBuilder builder = new StringBuilder();
         for(InputFile file: this.files){
            if(file.getProdigalCommand(parentOutputDir) == null){
                builder.append("echo Null");
            }
            else{
                builder.append(file.getProdigalCommand(parentOutputDir));
            }
            builder.append("\n");
            this.log.append(file.getProdigalCommand(parentOutputDir).replaceAll("\n", "\n \t"));
            this.log.append("\n");
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
                    log.append(s.replaceAll("\n", "\n \t"));
                }
                ret.add("\n");
            }
            
            this.log.append("\n");
        }
        return ret;
    }

    @Override
    public String getLog() {
        return this.log.toString();
    }

    @Override
    public void run() {
        if(!this.started){
            this.started = true;
            System.out.println("Set "+this.name+ " has started.");
            this.tab.disableEditing();
            try {
                Executioner execution = new Executioner(this.outputDirectory.getAbsolutePath());

                for(InputFile file:this.files){

                    boolean filePassed;
                    filePassed = execution.execute(file.getPreprocessingCommand(this.outputDirectory.getPath()));
                    if(filePassed){
                        filePassed = execution.execute(file.getProcessingCommand(this.outputDirectory.getPath()));
                    }
                    else{
                        System.out.println("Fail at preprocessing");
                    }
                    if(filePassed){
                        filePassed = execution.execute(file.getAssemblerCommand(this.outputDirectory.getPath()));
                    }
                    else{
                        System.out.println("Fail at processing");
                    }
                    if(!filePassed){
                        System.out.println("Fail at assembly");
                    }
                    
//                    if(filePassed){
//                        filePassed = execution.execute(file.getReadsVsContigsCommand(this.outputDirectory.getPath()));
//                    }
//                    if(filePassed){
//                        filePassed = execution.execute(file.getProdigalCommand(this.outputDirectory.getPath()));
//                    }
                    
                    this.tab.setFileProgressed(file.getAbsolutePath(), filePassed);
                    ArrayList<Boolean> toolBools = new ArrayList<>(file.getValidTools().size());
                    for(String p:file.getToolCommands(this.outputDirectory.getPath())){
                        toolBools.add(execution.execute(p));
                        toolBools.add(true);
                    }
                    this.tab.setToolProgressed(file.getAbsolutePath(), file.getValidTools(), toolBools);
                }
            } catch (IOException ex) {
                Logger.getLogger(SetOfFiles.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            this.tab.setAllProgressed(true);
            this.tab.reenableSetRemoval();
        }
        else{
            System.out.println("Set "+this.name+" allready progressed.");
        }
    }
    
    private void checkInputFormat(){
        if (this.usePreprocessing != null|| this.usePreprocessing.getParsedParameters().getStartCommand() != null){
            this.inputChooser.setInputFilter("Preprocessing input", this.usePreprocessing.getParsedParameters().getValidInputEndings());
        }
        else if (this.useProcessing != null|| this.useProcessing.getParsedParameters().getStartCommand() != null){
            this.inputChooser.setInputFilter("Processing input", this.useProcessing.getParsedParameters().getValidInputEndings());
        }
        else if (this.useAssembler != null|| this.useAssembler.getParsedParameters().getStartCommand() != null){
            this.inputChooser.setInputFilter("Assembler input", this.useAssembler.getParsedParameters().getValidInputEndings());
        }
        else{
            this.inputChooser.setInputFilter("All", null);
        }
    }
    
    
}
