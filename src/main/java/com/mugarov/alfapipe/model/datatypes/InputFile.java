/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.model.ExecutionCommandBuilder;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.datatypes.Parseable;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class InputFile extends File implements Executable{
    
    private final ProgramParameterSet processingInputParameters;
    private ProgramParameterSet assemblerInputParameters;
    private final ProgramParameterSet readsVsContigsParameters;
    private final ProgramParameterSet prodigalParameters;
    private ArrayList<ProgramParameterSet>tools;
    
    
    // needed for checking if the tool is selected for this File only
    private boolean[] toolSelected;
    
    private boolean valid;
    
    private final StringBuilder log;
    private ExecutionCommandBuilder processingCommand;
    private ArrayList<File> processingOutputFiles;
    private ExecutionCommandBuilder assemblerCommand;
    private ArrayList<File> assemblerOutputFiles;
    private ExecutionCommandBuilder readsVsContigsCommand;
    private ArrayList<File> readsVsContigsOutputFiles;
    private ExecutionCommandBuilder prodigalCommandBuilder;
    private ArrayList<File> prodigalOutputFiles;
    private ExecutionCommandBuilder toolCommand;
    private ArrayList<File> toolOutputDirectories;
    
    private ProgramParameterSet lastParameters;
    private ProgramParameterSet currentParameters;
    private ExecutionCommandBuilder lastCommand;
    private ExecutionCommandBuilder currentCommand;
    private ArrayList<File> lastOutputFiles;
    private ArrayList<File> currentOutputFiles;

    public InputFile(String inputPath, ArrayList<ProgramParameterSet> set) {
        super(inputPath);
        this.tools = new ArrayList<>();
        this.valid =false;
        this.processingInputParameters = new ProgramParameterSet(Pool.GENERATOR_PROCESSING.get(Pool.NAME_DEFAULT_PROCESSING));
        this.assemblerInputParameters = null;
        this.readsVsContigsParameters = new ProgramParameterSet(Pool.GENERATOR_READS_VS_CONTIGS.get(Pool.NAME_DEFAULT_READS_VS_CONTIGS));
        this.prodigalParameters = new ProgramParameterSet(Pool.GENERATOR_PRODIGAL.get(Pool.NAME_DEFAULT_PRODIGAL));
        this.tools = set;
        this.toolSelected = new boolean[this.tools.size()];
        this.log = new StringBuilder("Log for "+this.getName()+"\n");
    }
    
    public InputFile(String inputPath) {
        super(inputPath);
        this.tools = new ArrayList<>();
        this.valid =false;
        this.processingInputParameters = new ProgramParameterSet(Pool.GENERATOR_PROCESSING.get(Pool.NAME_DEFAULT_PROCESSING));
        this.assemblerInputParameters = null;
        this.readsVsContigsParameters = new ProgramParameterSet(Pool.GENERATOR_READS_VS_CONTIGS.get(Pool.NAME_DEFAULT_READS_VS_CONTIGS));
        this.prodigalParameters = new ProgramParameterSet(Pool.GENERATOR_PRODIGAL.get(Pool.NAME_DEFAULT_PRODIGAL));
        for(String tool:Pool.GENERTATOR_TOOLS.getAvailableNames()){
            this.tools.add(new ProgramParameterSet(Pool.GENERTATOR_TOOLS.get(tool)));
        }
        this.toolSelected = new boolean[this.tools.size()];
        this.log = new StringBuilder("Log for "+this.getName()+"\n");
    }

    public void setAvailableTools(ArrayList<ProgramParameterSet> set){
        this.tools = set;
        this.toolSelected = new boolean[this.tools.size()];
    }
    
    public void selectTool(Parseable tool){
        ProgramParameterSet set;
        int i = 0;
        boolean found = false;
        while(i<this.tools.size()&& !found){
            set= this.tools.get(i);
            if(set.getName().equals(tool.getName())){
                /**Don't do 
                 * set.select();
                 * unless you want to select this tool for all files.
                 */
                this.toolSelected[i]=true;
                found = true;
            }
            else{
                i++;
            }
        }
    }
   
    public void unselectTool(Parseable tool){
        ProgramParameterSet set;
        int i = 0;
        boolean found = false;
        while(i<this.tools.size()&& !found){
            set= this.tools.get(i);
            if(set.getName().equals(tool.getName())){
                /**Don't do 
                 * set.unselect();
                 * unless you wan to unselect this for all files.
                 */
                this.toolSelected[i]=false;
                found = true;
            }
            else{
                i++;
            }
        }
    }
    
   

    public boolean selectAssembler(ProgramParameterSet param){
        this.assemblerInputParameters = param;
        return this.validateFile();
    }
    
    /**
     * @return true if the file is compatible with the current assembler if the
     * current assembler has a start-command. Else, it will automatically asume
     * that the file will not be assembled and it will be true automatically. 
     */
    public boolean validateFile(){
        if(this.assemblerInputParameters.getParsedParameters().getStartCommand() != null){
            this.valid = false;
            for(String ending:this.assemblerInputParameters.getParsedParameters().getValidInputEndings()){
                if(this.getName().endsWith(ending)){
                    this.valid=true;
                }
            }
        }
        else{
            this.valid = true;
        }
        return this.valid;
    }
    
    /**
     * 
     * @return the last calculated value of valid. It will not be checked again 
     * with this method. To get a more accurate value use validateFile().
     */
    public boolean isValid(){
        return this.valid;
    }
    
    public boolean toolIsValid(Parseable tool){
        boolean tollValid = false;
        // if an assembler is selected, the output has to be compatible with the tool
        if(this.assemblerInputParameters.getParsedParameters().getStartCommand() != null){
//             System.out.println("Assembler is not null");
            for(String ending:tool.getValidInputEndings()){
//                System.out.println("Testing if "+ending+" matches.");
                if (this.assemblerInputParameters.getParsedParameters().getOutputEnding().equals(ending)){
//                    System.out.println(this.assemblerInputParameters.getParsedParameters().getOutputEnding()+" equals "+ending);
                    tollValid = true;
                }
            }
        }
        else{ // if no assembler is selected, the input file itself has to be valid
//            System.out.println("Assembler is null");
            for(String ending:tool.getValidInputEndings()){
                if (this.getName().endsWith(ending)){
//                    System.out.println(this.getName()+" ends with "+ending);
                    tollValid = true;
                }
            }
        }
        return tollValid;
    }
    
    public ArrayList<String> getValidTools(){
        ArrayList<String> valids = new ArrayList<>();
        for(String pName:Pool.GENERTATOR_TOOLS.getAvailableNames()){
            if(this.toolIsValid(Pool.GENERTATOR_TOOLS.get(pName))){
                valids.add(pName);
            }
        }
//         System.out.println("Valid tools: "+ valids);
        return valids;
    }
       

    @Override
    public String getLog() {
        return this.log.toString();
    }
    
    private String getCurrentCommand(String parentOutputDir){
        this.currentParameters.getParsedParameters().sortParameters();
        this.currentOutputFiles = new ArrayList<>();
        StringBuilder currentStringBuilder = new StringBuilder();
        for(File lastOutputFile:this.lastOutputFiles){
            if(this.lastCommand.outputIsDirectory()){
                for(File file:lastOutputFile.listFiles()){
                    this.currentCommand = new ExecutionCommandBuilder();
                    this.currentCommand.buildString(this.currentParameters, file, parentOutputDir);
                    if(this.currentCommand.getExecutionCommand() == null){
                        currentStringBuilder.append("echo no program was selected for "+file.getName()+"\n");
                        this.currentOutputFiles.add(file);
                        this.log.append("no assembler was selected for "+file.getName()+"\n");
                    }
                    else{
                        this.currentOutputFiles.add(new File(this.currentCommand.getOutputPath()));
                        currentStringBuilder.append(this.currentCommand.getExecutionCommand());
                        this.log.append(this.currentCommand.getExecutionCommand());
                    }
                }
                
            }
            else{
                    this.currentCommand = new ExecutionCommandBuilder();
                    this.currentCommand.buildString(this.currentParameters, lastOutputFile, parentOutputDir);
                    if(this.currentCommand.getExecutionCommand() == null){
                        this.currentOutputFiles.add(lastOutputFile);
                        this.log.append("no assembler was selected for "+lastOutputFile.getName()+"\n");
                        currentStringBuilder.append("echo no program was selected for "+lastOutputFile.getName()+"\n");
                    }
                    else{
                        this.currentOutputFiles.add(new File(this.currentCommand.getOutputPath()));
                        this.log.append(this.currentCommand.getExecutionCommand());
                        currentStringBuilder.append(this.currentCommand.getExecutionCommand());
                    }

            } 
        }
        if(currentStringBuilder.toString().length() == 0){
            return "Something went wrong while building current command";
        }
        return currentStringBuilder.toString();
    }
    
    
    /**
     * TODO
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getProcessingCommand(String parentOutputDir){

        String ret;
//        this.lastParameters = new ProgramParameterSet();
//        this.currentParameters = this.processingInputParameters;
//        this.lastCommand = new ExecutionCommandBuilder();
//        this.currentCommand = this.processingCommand;
//        this.lastOutputFiles = new ArrayList<>(1);
//        this.lastOutputFiles.add(this);
        
//        ret = this.getCurrentCommand(parentOutputDir);
        
//        this.processingOutputFiles = this.currentOutputFiles;
        
        // just for testing
           this.processingOutputFiles = new ArrayList<>();
           this.processingOutputFiles.add(this);
           this.processingCommand = new ExecutionCommandBuilder();
           ret= "Processing Output Command of "+this.getName();
        // 
        return ret;
    }
    
    @Override
    public String getAssemblerCommand(String parentOutputDir){
        String ret;
        this.lastParameters = this.processingInputParameters;
        this.currentParameters = this.assemblerInputParameters;
        this.lastCommand = this.processingCommand;
        this.currentCommand = this.assemblerCommand;
        this.lastOutputFiles = this.processingOutputFiles;

        ret = this.getCurrentCommand(parentOutputDir);

        this.assemblerOutputFiles = this.currentOutputFiles;
        
        return ret;
    }
    
    /**
     * TODO
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getReadsVsContigsCommand(String parentOutputDir){
        return "Reads Vs Contigs commands of "+this.getName();
    }
    
    /**
     * TODO
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getProdigalCommand(String parentOutputDir){
        
        return "Prodigal commands of "+this.getName();
    }
    
    /**
     * TODO
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getToolCommands(String parentOutputDir){
        
//        return "Tool commands of "+this.getName();
        //TODO: use ToolParameterSet
        StringBuilder toolStringBuilder = new StringBuilder();
        for(int i = 0; i<this.tools.size(); i++){
            ProgramParameterSet tp =this.tools.get(i);
            if(this.toolSelected[i]){
                /**
                 * TODO: get the actual tool selection
                 */
                toolStringBuilder.append("Execute "+tp.getName()+" with parameters \n");
                for(InputParameter ip:tp.getInputParameters()){
                    if(ip.getBoolean()){
                        toolStringBuilder.append("\t"+ip.getName());
                        if(!ip.isBoolean()){
                            toolStringBuilder.append(" with value "+ip.getValue());
                            toolStringBuilder.append("\n");
                        }
                    }
                }
            }
        }

            

        
        return toolStringBuilder.toString();
    }

   
}
