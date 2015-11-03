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
public class ProcessingRadioButtonListener implements ActionListener{
    private ParseableProgramParameters selectedProcessing;
    private SetOfFiles fileSet;
    
    public ProcessingRadioButtonListener(){
        this.fileSet = null;
        this.selectedProcessing = Pool.GENERATOR_PROCESSING.get(Pool.GENERATOR_PROCESSING.getAvailableNames()[0]);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selectedProcessing = Pool.GENERATOR_PROCESSING.get(ae.getActionCommand());
       if(this.fileSet != null){
            this.fileSet.setProcessing(this.selectedProcessing);
        }
       
    }
    
    public ParseableProgramParameters getSelectedProcessing(){
        return this.selectedProcessing;
    }
    
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setProcessing(this.selectedProcessing);
        
    }
    
}
