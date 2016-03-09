/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.awt.event.ActionEvent;

/**
 *
 * @author Mark
 */
public class AssemblerListener implements ProgramListener{
    private ParseableProgram selectedAssembler;
    private SetOfFiles fileSet;
    
    public AssemblerListener(){
        this.fileSet = null;
        String[] names = ParameterPool.GENERATOR_ASSEMBLER.getAvailableNames();
        if(names.length > 0){
            this.selectedAssembler = ParameterPool.GENERATOR_ASSEMBLER.get(names[0]);
        }
        else{
            this.selectedAssembler = null;
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selectedAssembler = ParameterPool.GENERATOR_ASSEMBLER.get(ae.getActionCommand());
       if(this.fileSet != null){
            this.fileSet.setProgram(2, selectedAssembler);
        }
    }
    
    @Override
    public void setInitialParameters(){
       String[] names = ParameterPool.GENERATOR_ASSEMBLER.getAvailableNames();
        if(names.length > 0){
            this.selectedAssembler = ParameterPool.GENERATOR_ASSEMBLER.get(names[0]);
        }
        else{
            this.selectedAssembler = null;
        }
        if(this.fileSet != null){
             this.fileSet.setAssembler(this.selectedAssembler);
        }
    }
    
    @Override
    public ParseableProgram getSelected(){
        return this.selectedAssembler;
    }
    
    @Override
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setAssembler(this.selectedAssembler);
    }
    
    @Override
    public String[] getValidSelections(){
        return ParameterPool.GENERATOR_ASSEMBLER.getAvailableNames();
    }

    @Override
    public int getMaxNameLength() {
        int maxLength = ParameterPool.LABEL_ASSEMBLER.length();
        String[] names = ParameterPool.GENERATOR_ASSEMBLER.getAvailableNames();
        for(String name:names){
            if(name.length()<maxLength){
                maxLength=name.length();
            }
        }
        return maxLength;
    }
    
}
