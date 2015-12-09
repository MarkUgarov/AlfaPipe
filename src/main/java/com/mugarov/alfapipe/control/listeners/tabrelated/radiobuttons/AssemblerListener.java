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
import java.awt.event.ActionListener;

/**
 *
 * @author Mark
 */
public class AssemblerListener implements ActionListener{
    private ParseableProgramParameters selectedAssembler;
    private SetOfFiles fileSet;
    
    public AssemblerListener(){
        this.fileSet = null;
        String[] names = Pool.GENERATOR_ASSEMBLER.getAvailableNames();
        if(names.length > 0){
            this.selectedAssembler = Pool.GENERATOR_ASSEMBLER.get(names[0]);
        }
        else{
            this.selectedAssembler = null;
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selectedAssembler = Pool.GENERATOR_ASSEMBLER.get(ae.getActionCommand());
       if(this.fileSet != null){
            this.fileSet.setAssembler(this.selectedAssembler);
        }
       
    }
    
    public ParseableProgramParameters getSelectedAssembler(){
        return this.selectedAssembler;
    }
    
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setAssembler(this.selectedAssembler);
    }
    
    public String[] getValidSelections(){
        return Pool.GENERATOR_ASSEMBLER.getAvailableNames();
    }
    
}
