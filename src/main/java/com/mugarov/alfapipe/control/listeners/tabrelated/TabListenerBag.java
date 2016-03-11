/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated;

import com.mugarov.alfapipe.control.FileSetManager;
import com.mugarov.alfapipe.control.listeners.tabrelated.cluster.ClusterSelectionListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProgramPanelListener;
import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.datatypes.ProgramSetList;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class TabListenerBag {
    private final ArrayList<ProgramPanelListener> programListeners;
    
    private final ParameterListener clusterParameterListener;
    private final ClusterSelectionListener clusterSelectionListener;
    
    private final TabButtonListener  buttonListener;
    private final ToolSelectListener toolListener;
    
    private SetOfFiles fileSet;
    private FileSetManager fileManager;
    private boolean evaluated;
    private int maxLength;
    
    
    public TabListenerBag(ProgramSet clusterSet){
        ArrayList<ProgramSetList> available = ComponentPool.PROGRAM_GENERATOR.getAll();
        this.programListeners = new ArrayList<>(available.size());
        
        for(int i = 0; i<available.size(); i++){
            this.programListeners.add(new ProgramPanelListener(i));
        }
        
        this.clusterParameterListener = new ParameterListener(clusterSet.getInputParameters());
        this.clusterSelectionListener = new ClusterSelectionListener();
        
        this.buttonListener = new TabButtonListener();
        this.toolListener = new ToolSelectListener();
        this.fileSet = null;
        this.evaluateMaxLength();
    }
    
    public void setFileSet(SetOfFiles set){
        this.fileSet = set;
        this.buttonListener.setFileSet(this.fileSet);
        for(ProgramPanelListener p:this.programListeners){
            p.setFileSet(set);
        }
        this.toolListener.setFileSet(this.fileSet);
        this.clusterParameterListener.setFileSet(this.fileSet);
    }
    
    public void setFileManager(FileSetManager man){
        this.fileManager = man;
        this.buttonListener.setFileManager(this.fileManager);
    }
    
    public TabButtonListener getButtonListener(){
        return this.buttonListener;
    }
    
    public ProgramPanelListener getProgramListener(int index){
        return this.programListeners.get(index);
    }
    
    public ArrayList<ProgramPanelListener> getProgramListeners(){
        return this.programListeners;
    }
    
       
    public ToolSelectListener getToolListener(){
        return this.toolListener;
    }
    
    public ParameterListener getClusterParameterListener(){
        return this.clusterParameterListener;
    }
    
    public ClusterSelectionListener getClusterSelectionListener(){
        return this.clusterSelectionListener;
    }
    
    private void evaluateMaxLength(){
        this.maxLength = 0;
        int tmp;
        for(ProgramPanelListener p: this.programListeners){
            tmp = p.getMaxNameLength();
            if(this.maxLength<tmp){
                this.maxLength = tmp;
            }
        }
        this.evaluated = true;
    }
    
    public int getMaxNameLength(){
        if(!evaluated){
            this.evaluateMaxLength();
        }
        return this.maxLength;
    }
    
}
