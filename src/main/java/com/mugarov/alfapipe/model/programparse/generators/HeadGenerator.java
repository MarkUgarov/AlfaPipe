/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.generators;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.datatypes.ProgramSetList;
import com.mugarov.alfapipe.model.programparse.ProgramListSorter;
import com.mugarov.alfapipe.model.programparse.ProgramSorter;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramList;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import com.mugarov.alfapipe.model.programparse.fabrics.*;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class HeadGenerator{
    
    private ArrayList<ExtendedCore> programCores;
    private ExtendedCore clusterCore;
    private final File programConfigDir;
    private final File clusterFile;
    private final ArrayList<Integer> usedIndices;
    
    public HeadGenerator(){
        this.programCores = new ArrayList<>();
        this.usedIndices = new ArrayList<>();
//        this.configDir = new File(ParameterPool.CONFIG_PREFIX+System.getProperty("user.name")+File.separator+"TestExtendedCore"); 
        this.programConfigDir = new File(ParameterPool.CONFIG_PREFIX+System.getProperty("user.name")+File.separator+ParameterPool.CONFIG_INFIX+File.separator+ParameterPool.PATH_PREFIX_OBLIGATORY_PROGRAMS); 
        this.clusterFile = new File(ParameterPool.CONFIG_PREFIX+System.getProperty("user.name")+File.separator+ParameterPool.CONFIG_INFIX+File.separator+ParameterPool.PATH_CLUSTER); 
        if(!this.programConfigDir.exists() || !this.programConfigDir.isDirectory()){
            this.programConfigDir.mkdirs();
        }
        if(!this.clusterFile.exists() || this.clusterFile.isDirectory()){
            this.clusterFile.getParentFile().mkdirs();
        }
        this.parseInPrograms();
        this.parseInCluster();
        this.parseOut();
        this.sortProgramLists();
    }
    


    public String[] getAvailableNames(int listID) {
        return this.programCores.get(listID).getAvailableNames();
    }


    /**
     * 
     * @param name the name of the application
     * @param listID the ID of the list you want to getProgramSetList the 
 ParseableProgramParamter from
     * @return the ParseableProgram with the requested name or
 null if the list is null or the index is out of range or if the 
 requested name does not exist in that list
     */
    public ParseableProgram get(String name, int listID) {
        if(this.programCores != null && !this.programCores.isEmpty() && listID<this.programCores.size()){
            return this.programCores.get(listID).get(name);
        }
        else if(this.programCores != null && !this.programCores.isEmpty() && listID<0){
            int i = listID;
            while(i<0){
                i= this.programCores.size() + i;
            }
            return this.programCores.get(i).get(name);
        }
        else{
            return null;
        }
    }
    
    public ParseableProgramList getParseableProgramList(int listID){
        if(this.programCores != null && !this.programCores.isEmpty() && listID<this.programCores.size()){
            return this.programCores.get(listID).getList();
        }
        else if(this.programCores != null && !this.programCores.isEmpty() && listID<0){
            int i = listID;
            while(i<0){
                i= this.programCores.size() + i;
            }
            return this.programCores.get(i).getList();
        }
         else{
             return null;
         }
    }

    /**
     * Parses in all config-files which exist.
     */
    public final void parseInPrograms() {
        File[] files = this.programConfigDir.listFiles(new YamlFilter());
        for(File f:files){           
            if(!f.getName().equals(ParameterPool.NAME_TOOLS_LIST)){
                //System.out.println("Creating core of "+f.getName());
                this.programCores.add(new ExtendedCore(f.getPath(), null,  -1));
                
                this.usedIndices.add(this.programCores.get(this.programCores.size()-1).getList().getIndex());
            }
            
        }
    }
    
    public final void parseInCluster(){
        if(this.clusterFile.exists()){
            this.clusterCore = new ExtendedCore(this.clusterFile.getPath(), null, 0);
        }
        
    }

    /**
     * Creates all config-files which are non-existent, but obligatory. 
     */
    public final void parseOut() {
        File[] files = this.programConfigDir.listFiles(new YamlFilter());
        ArrayList<String> fileNames = new ArrayList<>(files.length);
        for(File f:files){
            fileNames.add(f.getName());
        }
        int index=0;
        ParseableProgramList params;
        for(String obl:ParameterPool.CONFIG_OBLIGATORIES){
            if(!fileNames.contains(obl)){
                params = this.generateDefaultOfObligatory(obl);
                if(params == null){
                    System.err.println("Parameter for "+obl+" could not be found!");
                }
                else{
                    index = params.getIndex();
                    while(this.usedIndices.contains(index)){
                        index++;
                    }
                    this.programCores.add(new ExtendedCore((new File(this.programConfigDir, obl)).getPath(), params, index));
                    this.usedIndices.add(index);
                }
                
            }
        }
        if(!this.clusterFile.exists()){
            System.err.println("Cluster Parameters could not be found!");
            this.clusterCore = new ExtendedCore(this.clusterFile.getPath(), this.generateDefaultCluster(),0);
        }
    }
    
    public final void sortProgramLists(){
        ProgramSorter sorter = new ProgramSorter(this.programCores);
        this.programCores = sorter.sort();
        
    }
    
    public ProgramSetList getProgramSetList(int index){
        return this.getAllPrograms().get(index);
    }
    
    public ArrayList<ProgramSetList> getAllPrograms(){
        ArrayList<ProgramSetList> ret = new ArrayList<>(this.programCores.size());
        for(ExtendedCore core:this.programCores){
            ret.add(new ProgramSetList(core.getList()));
        }
        ret = this.sortProgramSetLists(ret);
        for(int i = 0; i<this.programCores.size(); i++){
            ret.get(i).setIndex(i);
        }
        return ret;
    }
    
    public ParseableProgram getClusterSet(){
        return this.clusterCore.getList().getPrograms().get(0);
    }
    
    private ArrayList<ProgramSetList> sortProgramSetLists(ArrayList<ProgramSetList> list){
        ProgramListSorter sorter = new ProgramListSorter(list);
        sorter.sort();
        return list;
    }
    
    private ParseableProgramList generateDefaultOfObligatory(String name){
        switch(name){
            case ParameterPool.OBLIGATORY_PREPROCESSING: return (new PreprocessingParameterFabric()).getList();
            case ParameterPool.OBLIGATORY_PROCESSING: return (new ProcessingParameterFabric()).getList();
            case ParameterPool.OBLIGATORY_ASSEMBLY: return (new AssemblerParameterFabric()).getList();
            case ParameterPool.OBLIGATORY_COMPARISON: return (new ComparisonParameterFabric()).getList();
            case ParameterPool.OBLIGATORY_ANNOTATION: return (new GeneAnnotationParameterFabric()).getList();
            default: return null;
        }
    }
    
    private ParseableProgramList generateDefaultCluster(){
        return (new ClusterParameterFabric()).getList();
    }

    private static class YamlFilter implements FilenameFilter {
        public YamlFilter() {  
        }
        @Override
        public boolean accept(File dir, String name) {
            String lowercaseName = name.toLowerCase();
            return (lowercaseName.endsWith(".yaml") || lowercaseName.endsWith(".yml"));
        }
    }
    
    
}
