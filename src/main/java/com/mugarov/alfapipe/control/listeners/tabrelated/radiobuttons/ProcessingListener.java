/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons;

import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.awt.event.ActionEvent;

/**
 *
 * @author mugarov
 */
public class ProcessingListener implements ProgramListener{
    private ParseableProgramParameters selectedProcessing;
    private SetOfFiles fileSet;
    
    public ProcessingListener(){
        this.fileSet = null;
        String[] names = Pool.GENERATOR_PROCESSING.getAvailableNames();
        if(names.length > 0){
            this.selectedProcessing = Pool.GENERATOR_PROCESSING.get(names[0]);
        }
        else{
            this.selectedProcessing = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selectedProcessing = Pool.GENERATOR_PROCESSING.get(ae.getActionCommand());
       if(this.fileSet != null){
            this.fileSet.setProcessing(this.selectedProcessing);
        }
    }
    
    @Override
    public void setInitialParameters(){
       String[] names = Pool.GENERATOR_PROCESSING.getAvailableNames();
        if(names.length > 0){
            this.selectedProcessing = Pool.GENERATOR_PROCESSING.get(names[0]);
        }
        else{
            this.selectedProcessing = null;
        }
        if(this.fileSet != null){
             this.fileSet.setProcessing(this.selectedProcessing);
         }
    }
    
    @Override
    public ParseableProgramParameters getSelected(){
        return this.selectedProcessing;
    }
    
    @Override
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setProcessing(this.selectedProcessing);
    }
    
    @Override
    public String[] getValidSelections(){
        return Pool.GENERATOR_PROCESSING.getAvailableNames();
    }

    public int getMaxNameLength() {
        int maxLength = Pool.LABEL_PROCESSING.length();
        String[] names = Pool.GENERATOR_PROCESSING.getAvailableNames();
        for(String name:names){
            if(name.length()<maxLength){
                maxLength=name.length();
            }
        }
        return maxLength;
    }
    
}
