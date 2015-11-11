/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated;

import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mugarov
 */
public class PreprocessingListener implements ActionListener{
    private ParseableProgramParameters selectedPreprocessing;
    private SetOfFiles fileSet;
    
    public PreprocessingListener(){
        this.fileSet = null;
        this.selectedPreprocessing = Pool.GENERATOR_PREPROCESSING.get(Pool.GENERATOR_PREPROCESSING.getAvailableNames()[0]);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selectedPreprocessing = Pool.GENERATOR_PREPROCESSING.get(ae.getActionCommand());
       if(this.fileSet != null){
            this.fileSet.setPreprocessing(this.selectedPreprocessing);
        }
       
    }
    
    public ParseableProgramParameters getSelectedProcessing(){
        return this.selectedPreprocessing;
    }
    
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setPreprocessing(this.selectedPreprocessing);
        
    }
}
