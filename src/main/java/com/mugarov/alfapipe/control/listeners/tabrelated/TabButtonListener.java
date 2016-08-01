/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated;

import com.mugarov.alfapipe.control.FileSetManager;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.view.mainview.tab.DataTabbedPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Mark
 */
public class TabButtonListener implements ActionListener{
    
    private SetOfFiles fileSet;
    private FileSetManager fileManager;
    private DataTabbedPane tabPane;
    
    public TabButtonListener(){
        this.fileSet = null;
        this.fileManager = null;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
//        System.out.println(ae.getActionCommand()+" was performed from "+this.fileSet.getID());
       if(ae.getActionCommand().equals(ParameterPool.BUTTON_CHOOSE_INPUT_FILE_COMMAND)){
//            System.out.println("Open-Dialog should be shown");
            this.fileSet.showOpenDialog();
        }
       else if(ae.getActionCommand().equals(ParameterPool.BUTTON_CHOOSE_OUTPUT_COMMAND)){
//           System.out.println("Directory open dialog should be shown");
           this.fileSet.showOuputDirDialog();
       }
       else if(ae.getActionCommand().equals(ParameterPool.BUTTON_APPLY_NAME_COMMAND)){
//           System.out.println("Name should change");
           this.fileManager.rename(this.fileSet.getID());
       }
       else if(ae.getActionCommand().equals(ParameterPool.BUTTON_DELETE_SET_COMMAND)){
//           System.out.println("Delete should happen");
           this.fileManager.remove(this.fileSet.getID());
       }
       else if(ae.getActionCommand().equals(ParameterPool.BUTTON_CANCEL_SET_COMMAND)){
           this.fileManager.cancel(this.fileSet.getID());
       }
       else{
            System.err.println(ae.getActionCommand()+ " has been performed. Not detected");
        }
    }
    
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
    }
    
    public void setFileManager(FileSetManager man){
        this.fileManager = man;
    }
    
    public void setTabPane(DataTabbedPane pane){
        this.tabPane = pane;
    }
    
    
    
}
