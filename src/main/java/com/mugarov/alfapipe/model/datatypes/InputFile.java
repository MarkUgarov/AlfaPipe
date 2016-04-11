/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.datatypes;

import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.ExecutionCommandBuilder;
import com.mugarov.alfapipe.model.LogFileManager;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Mark
 */
public class InputFile extends File implements Executable{
    
    private final LogFileManager log;
    private final ArrayList<File> pairedWith;
    private boolean valid;
    
    private final ArrayList<ProgramSet> programParameters;
    private ProgramSet lastNonNullParameters;
    private ArrayList<ProgramSet>tools;
    private ProgramSet firstNonNullParameters;
    
    
    // needed for checking if the tool is selected for this File only
    private boolean[] toolSelected;
    
    
    private final ArrayList<ExecutionCommandBuilder> commandBuilders;
    private ArrayList<ExecutionCommandBuilder> toolCommands;
    private ArrayList<File> toolOutputDirectories;
    
    private ProgramSet currentParameters;
    private ExecutionCommandBuilder lastProgramCommand;
    private ExecutionCommandBuilder currentCommand;
    private ArrayList<File> lastRelevantOutputFiles;
    
    private final ArrayList<Boolean> programXBuilt;
    private boolean toolsBuilt;
    

    public InputFile(String inputPath, ArrayList<ProgramSet> tools ,LogFileManager logManager) {
        super(inputPath);
        this.tools = new ArrayList<>();
        this.pairedWith = new ArrayList<>();
        this.valid =false;
        this.programParameters = new ArrayList<>();
        this.tools = tools;
        this.toolSelected = new boolean[this.tools.size()];
        this.commandBuilders = new ArrayList<>();
        this.programXBuilt = new ArrayList<>();
        this.log = logManager;
        this.log.appendLine("File added : "+this.getName(), InputFile.class.getName());
    }
    
    public InputFile(String inputPath, LogFileManager logManager) {
        super(inputPath);
        this.tools = new ArrayList<>();
        this.pairedWith = new ArrayList<>();
        this.valid =false;
        this.programParameters = new ArrayList<>();
        for(String tool:ComponentPool.GENERTATOR_TOOLS.getAvailableNames()){
            this.tools.add(new ProgramSet(ComponentPool.GENERTATOR_TOOLS.get(tool)));
        }
        this.toolSelected = new boolean[this.tools.size()];
        this.commandBuilders = new ArrayList<>();
        this.programXBuilt = new ArrayList<>();
        this.log = logManager;
        this.log.appendLine("File added : "+this.getName(), InputFile.class.getName());
    }

    public void setAvailableTools(ArrayList<ProgramSet> set){
        this.tools = set;
        this.toolSelected = new boolean[this.tools.size()];
    }
    
    public void selectTool(ParseableProgram tool){
        ProgramSet set;
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
   
    public void unselectTool(ParseableProgram tool){
        ProgramSet set;
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
   
    /**
     * Setting another program to an index or adding it to the end of the list
     * if the index is higher than the size of the list. 
     * @param index is of the program - adds the program on the end of the
     * list if index is greater than the size of the list or substitutes the 
     * program of this index
     * @param param
     * @param validate
     * @return 
     */
    public boolean selectProgram(int index, ProgramSet param, boolean validate){
        System.out.println("Trying to set "+param.getName()+" as index "+index+"in file "+this.getName());
        int i;
        if(index>=this.programParameters.size()){
            this.log.appendLine("Index out of bound. Set "+param.getName()+" as last.", InputFile.class.getName());
            this.programParameters.add(param);
            i =(this.programParameters.size()-1);

        }
        else{
            this.programParameters.remove(index);
            this.programParameters.add(index, param);
            i = index;
            
        }
        this.log.appendLine("Program of index "+i+" of file "+this.getName()+" has been changed to "+this.programParameters.get(i).getName(), InputFile.class.getName());
        if(validate){
            return this.validateFile();
        }
        
        return this.valid;
    }
    
    public void addProgram(ProgramSet param){
//        System.out.println("Adding program "+param.getName()+" to "+this.getName());
        this.programParameters.add(param);
    }
    

    public boolean shouldBePairedWith(File file){
        String regex;
        int distinguishPosition;
        if(this.firstNonNullParameters == null || this.firstNonNullParameters.getParsedParameters().getPairedConditions() == null){
            return false;
        }
        else{
            regex = this.firstNonNullParameters.getParsedParameters().getPairedConditions().getRegex();
            distinguishPosition = this.firstNonNullParameters.getParsedParameters().getPairedConditions().getUpperIndex();
        }
        String[] splitName1 = this.getName().split(regex);
        String[] splitName2 = file.getName().split(regex);
        if(splitName1.length != splitName2.length){
            return false;
        }
        int pos1;
        if(distinguishPosition<0){
            pos1 = (splitName1.length)+distinguishPosition;
        }
        else{
            pos1 = distinguishPosition;
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
        this.log.appendLine("Paired : "+file.getName()+" added als 'paired' to "+this.getName(), InputFile.class.getName());
    }
    
    private boolean validateFromTo(ArrayList<String> from, ParseableProgram to){
        if(from == null){
            return true;
        }
        for(String f:from){
            if(f== null){
                this.log.appendLine("Ending (from) is null - can't validate (threrefore assuming 'valid==true')", InputFile.class.getName());
                return true;
            }
            for(String t:to.getValidInputEndings()){
                if(t==null||f.equals(t) || t.equals("."+f)){
//                    System.out.println(f+ " or "+"."+f+ " is valid for "+t);
                    return true;
                }
                else{
//                    System.out.println(f +" or "+"."+f+ " are not valid for "+t);
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
        String[] splitname = this.getName().split("\\.",2);
        ArrayList<String> from = new ArrayList<>();
        from.add(splitname[splitname.length-1]);
        
        this.firstNonNullParameters = null;
        this.lastNonNullParameters = null;
        ParseableProgram to = null;
        
        boolean preprocessingValid = true;
        for(ProgramSet set:this.programParameters){
            if(set != null && !set.isEmpty() && set.getParsedParameters().getStartCommand()!= null){
                to = set.getParsedParameters();
                this.valid = this.validateFromTo(from, to);
                if(this.firstNonNullParameters == null){
                    this.firstNonNullParameters =set;
                }
                from.addAll(Arrays.asList(set.getParsedParameters().getOutputEndings()));
                this.lastNonNullParameters = set;
            }
            if(!this.valid){
                this.log.appendLine(set.getName()+" not valid for "+this.getName(), InputFile.class.getName());
            }
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
    
    public boolean toolIsValid(ParseableProgram tool){
        if(tool == null){
            return true;
        }
        ArrayList<String> from = new ArrayList<>();
        for(ProgramSet set: this.programParameters){
            if(set != null && !set.isEmpty() && set.getParsedParameters().getStartCommand() != null){
                from.addAll(Arrays.asList(set.getParsedParameters().getOutputEndings()));
            }
        }
        return this.validateFromTo(from, tool);
    }
    
    public ArrayList<String> getValidTools(){
        ArrayList<String> valids = new ArrayList<>();
        for(String pName:ComponentPool.GENERTATOR_TOOLS.getAvailableNames()){
            if(this.toolIsValid(ComponentPool.GENERTATOR_TOOLS.get(pName))){
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
    
    private String getCurrentCommandString(String parentOutputDir, boolean onCluster){
        this.currentParameters.getParsedParameters().sortParameters();
        StringBuilder currentStringBuilder = new StringBuilder();
       
        this.lastRelevantOutputFiles= new ArrayList<>();
        
        if(this.lastProgramCommand == null){
            this.lastRelevantOutputFiles.add(this);
            this.lastRelevantOutputFiles.addAll(this.pairedWith);
        }
        else{
            boolean addMore = true;
            int index = 0 ;
            for(ExecutionCommandBuilder command:this.commandBuilders){
                if(command != null && addMore){
                    if(command.useOnlyThisOutput(this.currentParameters)){
                        this.log.appendLine("Program with index "+index+" has specific outputs for " +this.getName()+" for parameters "+this.currentParameters.getName()+" without allowing other inputs.", InputFile.class.getName());
                        this.lastRelevantOutputFiles = command.getSpecifiFilesFor(this.currentParameters);
                        addMore =false;
                    }
                    else if(command.getSpecifiFilesFor(this.currentParameters) != null && command.getSpecifiFilesFor(this.currentParameters).size()>0){
                        this.log.appendLine("Program with index "+index+" has specific outputs for "+this.getName()+","+this.currentParameters.getName()+" but still allows other inputs.", InputFile.class.getName());
                        this.lastRelevantOutputFiles.addAll(command.getSpecifiFilesFor(this.currentParameters)); 
                    }
                    else{
                         this.log.appendLine("Program with index "+index+" has no outputs for "+this.getName()+","+this.currentParameters.getName()+".", InputFile.class.getName());
                    }
                }
                else if(!addMore){
                    this.log.appendLine("No more files will be added as relevant for "+this.currentParameters, InputFile.class.getName());
                }
                else{
                    this.log.appendLine("Trying to get informations of a null-entity of "+ExecutionCommandBuilder.class.getName(), InputFile.class.getName());
                }
                index++;
            }
           
            if(addMore){ // && lastCommand != null
                this.log.appendLine("No program has specific outputs where found for "+this.getName()+","+this.currentParameters.getName()+". Appending all files from the previous.", InputFile.class.getName());
                this.lastRelevantOutputFiles.addAll(this.lastProgramCommand.getAllIfNotSpecified(this.currentParameters));
                          
            }

        }
        if(this.lastRelevantOutputFiles.size() > 0){
            File file = this.lastRelevantOutputFiles.get(0);
            ArrayList<File> currentPaired = new ArrayList<>();
            if(this.lastRelevantOutputFiles.size()>1){
                for(int i=1; i<this.lastRelevantOutputFiles.size(); i++){
                    currentPaired.add(this.lastRelevantOutputFiles.get(i));
                }
            }
            else{
                currentPaired=null;
            }
            this.currentCommand = new ExecutionCommandBuilder(this.log);
            this.currentCommand.useClusterParameters(onCluster);
            this.currentCommand.buildString(this.currentParameters, file, parentOutputDir, currentPaired, this);
            if(this.currentCommand.getExecutionCommand() == null){
                currentStringBuilder.append(ParameterPool.MESSAGE_PREFIX+"no program was selected for "+file.getName()+"\n");
                this.log.appendLine("Null command detected for "+file.getName(), InputFile.class.getName());
            }
            else{
                this.log.appendLine("Command for "+file.getName()+" is "+this.currentCommand.getExecutionCommand(), InputFile.class.getName());
                currentStringBuilder.append(this.currentCommand.getExecutionCommand());
            }
        }
        else{
            this.log.appendLine("No input files where found for "+this.getName()+" on step "+ this.currentParameters.getName(), InputFile.class.getName());
        }
        return currentStringBuilder.toString();
    }
    
    @Override
    public String getProgramCommand(int index, String parentOutputDir){
        String ret;
        if(index>=this.programParameters.size()){
            ret = ParameterPool.MESSAGE_PREFIX+ParameterPool.MESSAGE_OUT_OF_INDEX+":"+index;
        }
        else if(this.programParameters.get(index).isEmpty()){
            ret = ParameterPool.MESSAGE_PREFIX+ParameterPool.MESSAGE_PROGRAM_EMPTY+", Index:"+index;
        }
        else if(this.programParameters.get(index).getParsedParameters().getStartCommand() == null){
            ret = ParameterPool.MESSAGE_PREFIX+ParameterPool.MESSAGE_PROGRAM_COMMAND_NULL+", Name: "+this.programParameters.get(index).getParsedParameters().getName()+"Index: "+index;
        }
        else{
            this.currentParameters = this.programParameters.get(index);
            ret = this.getCurrentCommandString(parentOutputDir,false);
            this.addCommandBuilder(this.currentCommand);
            this.lastProgramCommand = this.getCommand(this.commandBuilders.size()-1);
        }
        this.setProgramXBuilt(index, true);
        return ret;
    }
    
    public String getProgramCommand(int index, String parentOutputDir, boolean onCluster){
        String ret;
        if(index>=this.programParameters.size()){
            ret = ParameterPool.MESSAGE_PREFIX+ParameterPool.MESSAGE_OUT_OF_INDEX+":"+index;
        }
        else if(this.programParameters.get(index).isEmpty()){
            ret = ParameterPool.MESSAGE_PREFIX+ParameterPool.MESSAGE_PROGRAM_EMPTY+", Index:"+index;
        }
        else if(this.programParameters.get(index).getParsedParameters().getStartCommand() == null){
            ret = ParameterPool.MESSAGE_PREFIX+ParameterPool.MESSAGE_PROGRAM_COMMAND_NULL+", Name: "+this.programParameters.get(index).getParsedParameters().getName()+"Index: "+index;
        }
        else{
            this.currentParameters = this.programParameters.get(index);
            ret = this.getCurrentCommandString(parentOutputDir,true);
            this.addCommandBuilder(this.currentCommand);
            this.lastProgramCommand = this.getCommand(this.commandBuilders.size()-1);
        }
        this.setProgramXBuilt(index, true);
        return ret;
    }
    
    public boolean checkLastCommandFiles(){
        return this.lastProgramCommand.getFileLister().checkEssentialFiles();
    }
    
    private void addCommandBuilder( ExecutionCommandBuilder command){
        this.commandBuilders.add(command);
    }
    
    private ExecutionCommandBuilder getCommand(int index){
        if(index >= this.commandBuilders.size()){
            this.log.appendLine(ParameterPool.LOG_WARNING+"Not yet constructed: CommandBuilder on index "+index+", returning null.", InputFile.class.getName());
            return null;
        }
        else{
            return this.commandBuilders.get(index);
        }
    }
    
    private void setProgramXBuilt(int index, boolean built){
        if(index >= this.programXBuilt.size()){
            this.programXBuilt.add(built);
        }
        else{
            this.programXBuilt.remove(index);
            this.programXBuilt.add(index,built);
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
        this.toolCommands = new ArrayList<>(this.tools.size());
        for(int i = 0; i<this.tools.size(); i++){
            ProgramSet tp =this.tools.get(i);
            if(this.toolSelected[i]){
                ret.add(this.getSingleToolCommand(parentOutputDir, tp,false));
            }
            else{
                ret.add(null);
            }
        }
        if(ret.size() == 0){
            this.log.appendLine(ParameterPool.LOG_WARNING+"Input file "+this.getName() +" has no tool to execute.", InputFile.class.getName());
            ret.add(ParameterPool.MESSAGE_PREFIX+ParameterPool.MESSAGE_TOOL_IS_NULL);

        }
        this.toolsBuilt = true;
        return ret;
    }
    

    public ArrayList<String> getToolCommands(String parentOutputDir, boolean[] onCluster){
        ArrayList<String> ret = new ArrayList<>();
        this.toolCommands = new ArrayList<>(this.tools.size());
        for(int i = 0; i<this.tools.size(); i++){
            ProgramSet tp =this.tools.get(i);
            if(this.toolSelected[i]){
                ret.add(this.getSingleToolCommand(parentOutputDir, tp, onCluster[i]));
            }
            else{
                ret.add(null);
            }
        }
        if(ret.size() == 0){
            this.log.appendLine(ParameterPool.LOG_WARNING+"Input file "+this.getName() +" has no tool to execute.", InputFile.class.getName());
            ret.add(ParameterPool.MESSAGE_PREFIX+ParameterPool.MESSAGE_TOOL_IS_NULL);

        }
        this.toolsBuilt = true;
        return ret;
    }
    
    public boolean checkToolFiles(int index){
        return (index>=this.toolCommands.size() ||this.toolCommands.get(index) == null) ? false: this.toolCommands.get(index).getFileLister().checkEssentialFiles();
    }
    
    private String getSingleToolCommand(String parentOutputDir, ProgramSet tool, boolean onCluster){
        String ret;
        this.currentParameters = tool;
        ret = this.getCurrentCommandString(parentOutputDir, onCluster);
        if(ret == null){
            this.log.appendLine("Tool command of "+tool.getName()+" is null.", InputFile.class.getName());
        }
        return ret;
        
    }

   
}
