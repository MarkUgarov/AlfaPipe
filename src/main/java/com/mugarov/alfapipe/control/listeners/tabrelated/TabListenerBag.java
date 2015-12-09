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
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProdigalListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ReadsVsContigsListener;
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
    
    private final TabButtonListener  buttonListener;
    private final ToolSelectListener toolListener;
    
    private SetOfFiles fileSet;
    private FileSetManager fileManager;
    
    
    public TabListenerBag(){
        this.procRadioListener = new ProcessingListener();
        this.preListener = new PreprocessingListener();
        this.assRadioListener = new AssemblerListener();
        this.readsVsContigsListener = new ReadsVsContigsListener();
        this.prodigalListener = new ProdigalListener();
        
        this.buttonListener = new TabButtonListener();
        this.toolListener = new ToolSelectListener();
        this.fileSet = null;
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
    
}
