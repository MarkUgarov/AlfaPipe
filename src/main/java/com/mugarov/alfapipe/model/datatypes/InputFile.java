/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.model.ExecutionCommandBuilder;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class InputFile extends File implements Executable{
    
    private ProgramParameterSet processingInputParameters;
    private ProgramParameterSet assemblerInputParameters;
    private final ProgramParameterSet readsVsContigsParameters;
    private final ProgramParameterSet prodigalParameters;
    private ArrayList<ProgramParameterSet>tools;
    
    
    // needed for checking if the tool is selected for this File only
    private boolean[] toolSelected;
    
    private final ArrayList<File> pairedWith;
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
    private ArrayList<File> currentPaired;
    private ExecutionCommandBuilder lastCommand;
    private ExecutionCommandBuilder currentCommand;
    private ArrayList<File> lastOutputFiles;
    private ArrayList<File> currentOutputFiles;

    public InputFile(String inputPath, ArrayList<ProgramParameterSet> set) {
        super(inputPath);
        this.tools = new ArrayList<>();
        this.pairedWith = new ArrayList<>();
        this.valid =false;
        this.processingInputParameters = null;
//        this.processingInputParameters = new ProgramParameterSet(Pool.GENERATOR_PROCESSING.get(Pool.NAME_DEFAULT_PROCESSING));
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
        this.pairedWith = new ArrayList<>();
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
    
    public void selectTool(ParseableProgramParameters tool){
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
   
    public void unselectTool(ParseableProgramParameters tool){
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
        if(this.processingInputParameters != null){
            return this.validateFile();
        }
        else{
            return this.valid;
        }
    }
    
    public boolean selectProcessing(ProgramParameterSet param){
        this.processingInputParameters = param;
        if(this.assemblerInputParameters != null){
            return this.validateFile();
        }
        else{
            return this.valid;
        }
    }
    
    public boolean shouldBePairedWith(File file){
        String[] splitName1 = this.getName().split(Pool.FILE_PAIR_DISTIGNUISHER_REGEX);
        String[] splitName2 = file.getName().split(Pool.FILE_PAIR_DISTIGNUISHER_REGEX);
        if(splitName1.length != splitName2.length){
            return false;
        }
        int pos1;
        if(Pool.FILE_PAIR_DISTINGUISHER_POSITION<0){
            pos1 = (splitName1.length)+Pool.FILE_PAIR_DISTINGUISHER_POSITION;
        }
        else{
            pos1 = Pool.FILE_PAIR_DISTINGUISHER_POSITION;
        }
        if(splitName1.length < pos1 || pos1<0){
            return false;
        }
        
        boolean should = true;
        for(int i=0; (i<pos1 && should); i++){
            if(!splitName1[i].equals(splitName2[i])){
                should =false;
            }
        }
        return should;
    }
    
    public void addPairedFile(File file){
        this.pairedWith.add(file);
    }
    
    /**
     * @return true if the file is compatible with the current assembler if the
     * current assembler has a start-command. Else, it will automatically asume
     * that the file will not be assembled and it will be true automatically. 
     */
    public boolean validateFile(){
        // check if processing can work
        boolean processingValid = false;
        if(this.processingInputParameters.getParsedParameters().getStartCommand() != null){
            for(String ending: this.processingInputParameters.getParsedParameters().getValidInputEndings()){
                if(this.getName().endsWith(ending)){
                    processingValid = true;
                }
            }
            if(!processingValid){
                this.valid = false;
                return false;
            }
        }
        else{
            processingValid = true;
        }
        // check if output of the processing can be input of the assembler
        boolean assemblerValid = false;
        if(this.assemblerInputParameters.getParsedParameters().getStartCommand() != null){
            for(String ending:this.assemblerInputParameters.getParsedParameters().getValidInputEndings()){
                if(this.processingInputParameters.getParsedParameters().getOutputEnding().equals(ending)){
                    assemblerValid=true&&processingValid;
                }
            }
            if(!assemblerValid){
                this.valid = false;
                return false;
            }
        }
        else{
            assemblerValid = true;
        }
        // check if output of the assembler can be input of the reads vs contigs
        boolean readsVsContigsValid = false;
        if(this.readsVsContigsParameters.getParsedParameters().getStartCommand() != null){
            for(String ending:this.readsVsContigsParameters.getParsedParameters().getValidInputEndings()){
                if(this.assemblerInputParameters.getParsedParameters().getOutputEnding().equals(ending)){
                    readsVsContigsValid=true&&assemblerValid;
                }
            }
            if(!readsVsContigsValid){
                this.valid = false;
                return false;
            }
        }
        else{
            readsVsContigsValid = true;
        }
         // check if output of the readsVsContigs can be input of prodigal
        boolean prodigalValid = false;
        if(this.prodigalParameters.getParsedParameters().getStartCommand() != null){
            for(String ending:this.prodigalParameters.getParsedParameters().getValidInputEndings()){
                if(this.readsVsContigsParameters.getParsedParameters().getOutputEnding().equals(ending)){
                    prodigalValid=true&&readsVsContigsValid;
                }
            }
        }
        else{
            prodigalValid = true;
        }
        
        this.valid = prodigalValid;
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
    
    public boolean toolIsValid(ParseableProgramParameters tool){
        boolean toolValid = false;
        // if an assembler is selected, the output has to be compatible with the tool
        if(this.assemblerInputParameters.getParsedParameters().getStartCommand() != null){
//             System.out.println("Assembler is not null");
            for(String ending:tool.getValidInputEndings()){
//                System.out.println("Testing if "+ending+" matches.");
                if (this.assemblerInputParameters.getParsedParameters().getOutputEnding().equals(ending)){
//                    System.out.println(this.assemblerInputParameters.getParsedParameters().getOutputEnding()+" equals "+ending);
                    toolValid = true;
                }
            }
        }
        else{ // if no assembler is selected, the input file itself has to be valid
//            System.out.println("Assembler is null");
            for(String ending:tool.getValidInputEndings()){
                if (this.getName().endsWith(ending)){
//                    System.out.println(this.getName()+" ends with "+ending);
                    toolValid = true;
                }
            }
        }
        return toolValid;
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
                    this.currentCommand.buildString(this.currentParameters, file, parentOutputDir, this.currentPaired);
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
                    this.currentCommand.buildString(this.currentParameters, lastOutputFile, parentOutputDir, this.currentPaired);
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
        this.currentPaired = this.pairedWith;
//        this.lastCommand = new ExecutionCommandBuilder();
//        this.currentCommand = this.processingCommand;
//        this.lastOutputFiles = new ArrayList<>(1);
//        this.lastOutputFiles.add(this);
        
//        ret = this.getCurrentCommand(parentOutputDir);
//          this.currentPaired = null;
        
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
