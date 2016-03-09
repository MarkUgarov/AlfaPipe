/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated;

import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProcessingListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.AssemblerListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.PreprocessingListener;
import com.mugarov.alfapipe.control.FileSetManager;
import com.mugarov.alfapipe.control.listeners.tabrelated.cluster.ClusterSelectionListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProdigalListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ReadsVsContigsListener;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;

/**
 *
 * @author Mark
 */
public class TabListenerBag {
    private final ProcessingListener procRadioListener;
    private final PreprocessingListener preListener;
    private final AssemblerListener assRadioListener;
    private final ReadsVsContigsListener readsVsContigsListener;
    private final ProdigalListener prodigalListener;
    
    private final ParameterListener clusterParameterListener;
    private final ClusterSelectionListener clusterSelectionListener;
    
    private final TabButtonListener  buttonListener;
    private final ToolSelectListener toolListener;
    
    private SetOfFiles fileSet;
    private FileSetManager fileManager;
    private boolean evaluated;
    private int maxLength;
    
    
    public TabListenerBag(ProgramSet clusterSet){
        this.procRadioListener = new ProcessingListener();
        this.preListener = new PreprocessingListener();
        this.assRadioListener = new AssemblerListener();
        this.readsVsContigsListener = new ReadsVsContigsListener();
        this.prodigalListener = new ProdigalListener();
        
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
        this.preListener.setFileSet(this.fileSet);
        this.procRadioListener.setFileSet(this.fileSet);
        this.assRadioListener.setFileSet(this.fileSet);  
        this.readsVsContigsListener.setFileSet(this.fileSet);
        this.prodigalListener.setFileSet(this.fileSet);
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
    
    public PreprocessingListener getPreprocessingRadioButtonListener(){
        return this.preListener;
    }
    
    public ProcessingListener getProcessingRadioButtonListener(){
        return this.procRadioListener;
    }
    
    public AssemblerListener getAssemblerRadioListener(){
        return this.assRadioListener;
    }
    
    public ReadsVsContigsListener getReadsVsContigsListener() {
        return readsVsContigsListener;
    }

    public ProdigalListener getProdigalListener() {
        return prodigalListener;
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
        this.maxLength = this.preListener.getMaxNameLength();
        tmp = this.procRadioListener.getMaxNameLength();
        if(this.maxLength<tmp){
            this.maxLength = tmp;
        }
       tmp = this.assRadioListener.getMaxNameLength();
       if(this.maxLength<tmp){
           this.maxLength = tmp;
       }
       tmp = this.readsVsContigsListener.getMaxNameLength();
       if(this.maxLength<tmp){
           this.maxLength = tmp;
       }
       tmp = this.prodigalListener.getMaxNameLength();
       if(this.maxLength<tmp){
           this.maxLength = tmp;
       }
       tmp = this.toolListener.getMaxNameLength();
       if(this.maxLength<tmp){
           this.maxLength = tmp;
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
