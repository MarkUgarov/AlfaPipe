/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.awt.event.ActionEvent;

/**
 *
 * @author mugarov
 */
public class PreprocessingListener implements ProgramListener{
    private ParseableProgramParameters selectedPreprocessing;
    private SetOfFiles fileSet;
    
    public PreprocessingListener(){
        this.fileSet = null;
        String[] names = ParameterPool.GENERATOR_PREPROCESSING.getAvailableNames();
        if(names.length > 0){
            this.selectedPreprocessing = ParameterPool.GENERATOR_PREPROCESSING.get(names[0]);
        }
        else{
            this.selectedPreprocessing = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selectedPreprocessing = ParameterPool.GENERATOR_PREPROCESSING.get(ae.getActionCommand());
       if(this.fileSet != null){
            this.fileSet.setPreprocessing(this.selectedPreprocessing);
        }
    }
    
    @Override
    public void setInitialParameters(){
       String[] names = ParameterPool.GENERATOR_PREPROCESSING.getAvailableNames();
        if(names.length > 0){
            this.selectedPreprocessing = ParameterPool.GENERATOR_PREPROCESSING.get(names[0]);
        }
        else{
            this.selectedPreprocessing = null;
        }
        if(this.fileSet != null){
             this.fileSet.setPreprocessing(this.selectedPreprocessing);
         }
    }
    
    @Override
    public ParseableProgramParameters getSelected(){
        return this.selectedPreprocessing;
    }
    
    @Override
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setPreprocessing(this.selectedPreprocessing);
        
    }
    
    @Override
    public String[] getValidSelections(){
        return ParameterPool.GENERATOR_PREPROCESSING.getAvailableNames();
    }

    public int getMaxNameLength() {
        int maxLength = ParameterPool.LABEL_PREPROCESSING.length();
        String[] names = ParameterPool.GENERATOR_PREPROCESSING.getAvailableNames();
        for(String name:names){
            if(name.length()<maxLength){
                maxLength=name.length();
            }
        }
        return maxLength;
    }
}
