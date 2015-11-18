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
    
    private ProgramParameterSet preprocessingParameters;
    private ProgramParameterSet processingParameters;
    private ProgramParameterSet assemblerParameters;
    private final ProgramParameterSet readsVsContigsParameters;
    private final ProgramParameterSet prodigalParameters;
    private ProgramParameterSet lastNonNullParameters;
    private ArrayList<ProgramParameterSet>tools;
    
    
    // needed for checking if the tool is selected for this File only
    private boolean[] toolSelected;
    
    private final ArrayList<File> pairedWith;
    private boolean valid;
    
    private final StringBuilder log;
    private ExecutionCommandBuilder preprocessingCommand;
    private ExecutionCommandBuilder processingCommand;
    private ExecutionCommandBuilder assemblerCommand;
    private ExecutionCommandBuilder readsVsContigsCommand;
    private ExecutionCommandBuilder prodigalCommand;
    private ArrayList<ExecutionCommandBuilder> toolCommands;
    private ArrayList<File> toolOutputDirectories;
    
    private ProgramParameterSet currentParameters;
    private ArrayList<File> currentPaired;
    private ExecutionCommandBuilder lastCommand;
    private ExecutionCommandBuilder currentCommand;
    private ArrayList<File> lastRelevantOutputFiles;

    public InputFile(String inputPath, ArrayList<ProgramParameterSet> set) {
        super(inputPath);
        this.tools = new ArrayList<>();
        this.pairedWith = new ArrayList<>();
        this.valid =false;
        this.preprocessingParameters = null;
        this.processingParameters = null;
//        this.processingInputParameters = new ProgramParameterSet(Pool.GENERATOR_PROCESSING.get(Pool.NAME_DEFAULT_PROCESSING));
        this.assemblerParameters = null;
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
        this.preprocessingParameters = new ProgramParameterSet(Pool.GENERATOR_PREPROCESSING.get(Pool.GENERATOR_PREPROCESSING.getAvailableNames()[0]));
        this.processingParameters = new ProgramParameterSet(Pool.GENERATOR_PROCESSING.get(Pool.NAME_DEFAULT_PROCESSING));
        this.assemblerParameters = null;
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
    
    public boolean selectPreprocessing(ProgramParameterSet param, boolean validate){
         this.preprocessingParameters = param;
         if(this.preprocessingParameters != null && validate){
             return this.validateFile();
         }
         else{
             return this.valid;
         }
     }
    
    public boolean selectProcessing(ProgramParameterSet param, boolean validate){
         this.processingParameters = param;
         if(this.assemblerParameters != null && validate){
             return this.validateFile();
         }
         else{
             return this.valid;
         }
     }

    public boolean selectAssembler(ProgramParameterSet param, boolean validate){
        this.assemblerParameters = param;
        if(this.assemblerParameters != null && validate){
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
                should = false;
                return false;
            }
        }
        // check endings
        splitName1 = this.getName().split("\\.",2);
        splitName2 = this.getName().split("\\.",2);
        if(splitName1.length != splitName2.length || !splitName1[splitName1.length-1].equals(splitName2[splitName2.length-1])){
            return false;
        }
        return true;
    }
    
    public void addPairedFile(File file){
        this.pairedWith.add(file);
    }
    
    private boolean validateFromTo(String[] from, ParseableProgramParameters to){
        if(from == null){
            return true;
        }
        for(String f:from){
            if(f== null){
                return true;
            }
            for(String t:to.getValidInputEndings()){
                if(t==null||t.equals(f)){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * @return true if the file is compatible with the current assembler if the
     * current assembler has a start-command. Else, it will automatically asume
     * that the file will not be assembled and it will be true automatically. 
     */
    public boolean validateFile(){
        // check if processing can work
        
        String[] splitname = this.getName().split("\\.",2);
        String[] from = new String[]{splitname[splitname.length-1]};
        
        this.lastNonNullParameters = null;
        ParseableProgramParameters to = null;
        
        boolean preprocessingValid = true;
        if(this.preprocessingParameters.getParsedParameters().getStartCommand()!= null){
            to = this.preprocessingParameters.getParsedParameters();
            preprocessingValid = this.validateFromTo(from, to);
            from = this.preprocessingParameters.getParsedParameters().getOutputEndings();
            this.lastNonNullParameters = this.preprocessingParameters;
        }
        if(!preprocessingValid){
            this.valid = false;
            return false;
        }
        
        boolean processingValid = true;
        if(this.processingParameters.getParsedParameters().getStartCommand() != null){
            to = this.processingParameters.getParsedParameters();
            processingValid = this.validateFromTo(from, to);
            from = this.processingParameters.getParsedParameters().getOutputEndings();
            this.lastNonNullParameters = this.processingParameters;
        }
        if(!processingValid){
            this.valid = false;
            return false;
        }
          
        // check if output of the processing can be input of the assembler
        boolean assemblerValid = true;
        if(this.assemblerParameters.getParsedParameters().getStartCommand() != null){
            to = this.assemblerParameters.getParsedParameters();
            assemblerValid = this.validateFromTo(from, to);
            from = this.assemblerParameters.getParsedParameters().getOutputEndings();
            this.lastNonNullParameters = this.assemblerParameters;
        }
        if(!assemblerValid){
            this.valid = false;
            return false;
        }
        
        // check if output of the assembler can be input of the reads vs contigs
        boolean readsVsContigsValid = true;
        if(this.readsVsContigsParameters.getParsedParameters().getStartCommand() != null){
            to = this.readsVsContigsParameters.getParsedParameters();
            readsVsContigsValid = this.validateFromTo(from, to);
            from = this.readsVsContigsParameters.getParsedParameters().getOutputEndings();
            this.lastNonNullParameters = this.readsVsContigsParameters;
        }
        if(!readsVsContigsValid){
            this.valid = false;
            return false;
        }
         // check if output of the readsVsContigs can be input of prodigal
        boolean prodigalValid = true;
        if(this.prodigalParameters.getParsedParameters().getStartCommand() != null){
            to = this.prodigalParameters.getParsedParameters();
            prodigalValid = this.validateFromTo(from, to);
            from = this.prodigalParameters.getParsedParameters().getOutputEndings();
            this.lastNonNullParameters = this.prodigalParameters;
        }
        if(!prodigalValid){
            this.valid = false;
            return false;
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
        if(tool == null){
            return true;
        }
        String[] from;
        if(this.lastNonNullParameters == null || this.lastNonNullParameters.getParsedParameters().getStartCommand() == null){
            String[] splitname = this.getName().split("\\.",2);
            from = new String[]{splitname[splitname.length-1]};
        }
        else{
            from = this.lastNonNullParameters.getParsedParameters().getOutputEndings();
        }
        return this.validateFromTo(from, tool);
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
    
    private String getCurrentCommandString(String parentOutputDir){
        this.currentParameters.getParsedParameters().sortParameters();
        StringBuilder currentStringBuilder = new StringBuilder();
        if(this.lastCommand == null){
            this.lastRelevantOutputFiles= new ArrayList<>();
            this.lastRelevantOutputFiles.add(this);
        }
        else{
            this.lastCommand.getRelevantOutputFor(this.currentParameters, this);
        }
        for(File lastOutputFile:this.lastRelevantOutputFiles){
            for(File file:lastOutputFile.listFiles()){
                this.currentCommand = new ExecutionCommandBuilder();
                this.currentCommand.buildString(this.currentParameters, file, parentOutputDir, this.currentPaired);
                if(this.currentCommand.getExecutionCommand() == null){
                    currentStringBuilder.append("echo no program was selected for "+file.getName()+"\n");
                    this.log.append("no assembler was selected for "+file.getName()+"\n");
                }
                else{
                    currentStringBuilder.append(this.currentCommand.getExecutionCommand());
                    this.log.append(this.currentCommand.getExecutionCommand());
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
    public String getPreprocessingCommand(String parentOutputDir){
        if(this.preprocessingParameters.getParsedParameters().getStartCommand() == null){
            return Pool.MESSAGE_PREFIX+Pool.MESSAGE_PREPROCESSING_IS_NULL;
        }
        else{
            String ret;
            this.currentParameters = this.preprocessingParameters;
            this.currentPaired = this.pairedWith;
            ret = this.getCurrentCommandString(parentOutputDir);
            this.preprocessingCommand = this.currentCommand;
            this.lastCommand = this.preprocessingCommand;
            this.currentPaired = null;
            return ret;
        }
    }
    
    /**
     * TODO
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getProcessingCommand(String parentOutputDir){
        if(this.processingParameters.getParsedParameters().getStartCommand() == null){
            return Pool.MESSAGE_PREFIX+Pool.MESSAGE_PROCESSING_IS_NULL;
        }
        else{
            String ret;
            this.currentParameters = this.processingParameters;
            ret = this.getCurrentCommandString(parentOutputDir);
            this.processingCommand = this.currentCommand;
            this.lastCommand = this.processingCommand;
            return ret;
        }
    }
    
    @Override
    public String getAssemblerCommand(String parentOutputDir){
        if(this.assemblerParameters.getParsedParameters().getStartCommand() == null){
            return Pool.MESSAGE_PREFIX+Pool.MESSAGE_ASSEMBLER_IS_NULL;
        }
        else{
            String ret;
            this.currentParameters = this.assemblerParameters;
            ret = this.getCurrentCommandString(parentOutputDir);
            this.assemblerCommand = this.currentCommand;
            this.lastCommand = this.assemblerCommand;
            return ret;
        }
    }
    
    /**
     * TODO
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getReadsVsContigsCommand(String parentOutputDir){
        if(this.readsVsContigsParameters.getParsedParameters().getStartCommand() == null){
            return Pool.MESSAGE_PREFIX+Pool.MESSAGE_READSVSCONTIGS_IS_NULL;
        }
        else{
            String ret;
            this.currentParameters = this.readsVsContigsParameters;
            ret = this.getCurrentCommandString(parentOutputDir);
            this.readsVsContigsCommand = this.currentCommand;
            this.lastCommand = this.readsVsContigsCommand;
            return ret;
        }
    }
    
    /**
     * TODO
     * @param parentOutputDir
     * @return 
     */
    @Override
    public String getProdigalCommand(String parentOutputDir){
        if(this.prodigalParameters.getParsedParameters().getStartCommand() == null){
            return Pool.MESSAGE_PREFIX+Pool.MESSAGE_READSVSCONTIGS_IS_NULL;
        }
        else{
            String ret;
            this.currentParameters = this.prodigalParameters;
            ret = this.getCurrentCommandString(parentOutputDir);
            this.prodigalCommand = this.currentCommand;
            this.lastCommand = this.prodigalCommand;
            return ret;
        }
    }
    
    /**
     * TODO
     * @param parentOutputDir
     * @return 
     */
    @Override
    public ArrayList<String> getToolCommands(String parentOutputDir){
        ArrayList<String> ret = new ArrayList<>();
        for(int i = 0; i<this.tools.size(); i++){
            ProgramParameterSet tp =this.tools.get(i);
            if(this.toolSelected[i]){
                ret.add(this.getSingleToolCommand(parentOutputDir, tp));
            }
            else{
                ret.add(null);
            }
        }
        if(ret.size() ==0){
            System.out.println("ATTENTION: Input file "+this.getName() +" has no tool to execute.");
            ret.add(Pool.MESSAGE_PREFIX+Pool.MESSAGE_TOOL_IS_NULL);

        }
        return ret;
    }
    
    private String getSingleToolCommand(String parentOutputDir, ProgramParameterSet tool){
        String ret;
        if(this.preprocessingParameters.getParsedParameters().getStartCommand() != null && this.preprocessingParameters.specificOutputDefinedFor(tool.getName())){
            this.lastRelevantOutputFiles=this.preprocessingCommand.getRelevantOutputFor(tool, this);
        }
        else if(this.processingParameters.getParsedParameters().getStartCommand() != null && this.processingParameters.specificOutputDefinedFor(tool.getName())){
            this.lastRelevantOutputFiles=this.processingCommand.getRelevantOutputFor(tool, this);
        }
        else if(this.assemblerParameters.getParsedParameters().getStartCommand() != null && this.assemblerParameters.specificOutputDefinedFor(tool.getName())){
            this.lastRelevantOutputFiles=this.assemblerCommand.getRelevantOutputFor(tool, this);
        }
        else if(this.readsVsContigsParameters.getParsedParameters().getStartCommand() != null && this.readsVsContigsParameters.specificOutputDefinedFor(tool.getName())){
            this.lastRelevantOutputFiles=this.readsVsContigsCommand.getRelevantOutputFor(tool, this);
        }
        else if(this.prodigalParameters.getParsedParameters().getStartCommand() != null && this.prodigalParameters.specificOutputDefinedFor(tool.getName())){
            this.lastRelevantOutputFiles=this.prodigalCommand.getRelevantOutputFor(tool, this);
        }
        else if(this.lastCommand != null){
            this.lastRelevantOutputFiles=this.lastCommand.getRelevantOutputFor(tool, this);
        }
        // the else should be redundant to this.getCurrentCommandString(...) but the code will get some changes so I do it to get save
        else{
            this.lastRelevantOutputFiles = new ArrayList<>();
            this.lastRelevantOutputFiles.add(this);
        }
        ret = this.getCurrentCommandString(parentOutputDir);
        this.toolCommands.add(this.currentCommand);
        // DO NOT: this.lastCommand = this.currentCommand;
        return ret;
        
    }

   
}
