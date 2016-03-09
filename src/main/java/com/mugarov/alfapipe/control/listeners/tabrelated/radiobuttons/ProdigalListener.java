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
 * @author mugarov
 */
public class ProdigalListener implements ProgramListener{
    private ParseableProgram selectedProdigal;
    private SetOfFiles fileSet;
    
    public ProdigalListener(){
        this.fileSet = null;
        String[] names = ParameterPool.GENERATOR_PRODIGAL.getAvailableNames();
        if(names.length > 0){
            this.selectedProdigal = ParameterPool.GENERATOR_PRODIGAL.get(names[0]);
        }
        else{
            this.selectedProdigal = null;
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selectedProdigal = ParameterPool.GENERATOR_PRODIGAL.get(ae.getActionCommand());
       if(this.fileSet != null){
            this.fileSet.setProgram(4, selectedProdigal);
        }
    }
    
    @Override
    public void setInitialParameters(){
       String[] names = ParameterPool.GENERATOR_PRODIGAL.getAvailableNames();
        if(names.length > 0){
            this.selectedProdigal = ParameterPool.GENERATOR_PRODIGAL.get(names[0]);
        }
        else{
            this.selectedProdigal = null;
        }
        if(this.fileSet != null){
             this.fileSet.setProdigal(this.selectedProdigal);
         }
    }
    
    @Override
    public ParseableProgram getSelected(){
        return this.selectedProdigal;
    }
    
    @Override
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setProdigal(this.selectedProdigal);
    }
    
    @Override
    public String[] getValidSelections(){
        return ParameterPool.GENERATOR_PRODIGAL.getAvailableNames();
    }

    public int getMaxNameLength() {
        int maxLength = ParameterPool.LABEL_ANNOTATION.length();
        String[] names = ParameterPool.GENERATOR_PRODIGAL.getAvailableNames();
        for(String name:names){
            if(name.length()<maxLength){
                maxLength=name.length();
            }
        }
        return maxLength;
    }
}
