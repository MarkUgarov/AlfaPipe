/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated;

import com.mugarov.alfapipe.control.FileSetManager;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.view.mainview.tab.DataTabbedPane;

/**
 *
 * @author Mark
 */
public class TabListenerBag {
    private final AssemblerRadioButtonListener radioListener;
    private final TabButtonListener  buttonListener;
    private final ToolSelectListener toolListener;
    
    private SetOfFiles fileSet;
    private FileSetManager fileManager;
    
    
    public TabListenerBag(){
        this.radioListener = new AssemblerRadioButtonListener();
        this.buttonListener = new TabButtonListener();
        this.toolListener = new ToolSelectListener();
        this.fileSet = null;
    }
    
    public void setFileSet(SetOfFiles set){
        this.fileSet = set;
        this.buttonListener.setFileSet(this.fileSet);
        this.radioListener.setFileSet(this.fileSet);
        this.toolListener.setFileSet(this.fileSet);
    }
    
    public void setFileManager(FileSetManager man){
        this.fileManager = man;
        this.buttonListener.setFileManager(this.fileManager);
    }
    
    public TabButtonListener getButtonListener(){
        return this.buttonListener;
    }
    
    public AssemblerRadioButtonListener getRadioListener(){
        return this.radioListener;
    }
    
    public ToolSelectListener getToolListener(){
        return this.toolListener;
    }
}
