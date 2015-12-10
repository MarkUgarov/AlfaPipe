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
public class ReadsVsContigsListener implements ProgramListener{
      private ParseableProgramParameters selectedReadsVsContigs;
    private SetOfFiles fileSet;
    
    public ReadsVsContigsListener(){
        this.fileSet = null;
        String[] names = Pool.GENERATOR_READS_VS_CONTIGS.getAvailableNames();
        if(names.length > 0){
            this.selectedReadsVsContigs = Pool.GENERATOR_READS_VS_CONTIGS.get(names[0]);
        }
        else{
            this.selectedReadsVsContigs = null;
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selectedReadsVsContigs = Pool.GENERATOR_READS_VS_CONTIGS.get(ae.getActionCommand());
       if(this.fileSet != null){
            this.fileSet.setReadsVsContigs(this.selectedReadsVsContigs);
        }
       
    }
    
      @Override
    public ParseableProgramParameters getSelected(){
        return this.selectedReadsVsContigs;
    }
    
      @Override
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setReadsVsContigs(this.selectedReadsVsContigs);
    }
    
      @Override
    public String[] getValidSelections(){
        return Pool.GENERATOR_READS_VS_CONTIGS.getAvailableNames();
    }
}
