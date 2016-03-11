/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons;

import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgram;
import java.awt.event.ActionEvent;

/**
 *
 * @author mugarov
 */
public class ProgramPanelListener implements ProgramListener{
    private ParseableProgram selected;
    private SetOfFiles fileSet;
    private final int index;
    private final String[] names;
    
    public ProgramPanelListener(int index){
        this.fileSet = null;
        this.index = index;
    
        this.names =  ComponentPool.PROGRAM_GENERATOR.getAvailableNames(index);
        
        this.setInitialParameters();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
//       System.out.println(ae.getActionCommand() +" has been performed");
       this.selected = ComponentPool.PROGRAM_GENERATOR.get(ae.getActionCommand(), index);
       if(this.fileSet != null){
            this.fileSet.setProgram(this.index, selected);
        }
    }
    
    @Override
    public final void setInitialParameters(){
        if(names.length > 0){
            this.selected =  ComponentPool.PROGRAM_GENERATOR.get(names[0], index);
        }
        else{
            this.selected = null;
        }
        if(this.fileSet != null){
             this.fileSet.setProgram(this.index, this.selected);
        }
    }
    
    @Override
    public ParseableProgram getSelected(){
        return this.selected;
    }
    
    @Override
    public void setFileSet(SetOfFiles set){
        this.fileSet =set;
        this.fileSet.setProgram(this.index, this.selected);
    }
    
    @Override
    public String[] getValidSelections(){
        return this.names;
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public String getName(){
        return ComponentPool.PROGRAM_GENERATOR.getProgramSetList(index).getName();
    }

    @Override
    public int getMaxNameLength() {
        int maxLength = ComponentPool.PROGRAM_GENERATOR.getProgramSetList(this.index).getName().length();
        for(String name:this.names){
            if(name.length()<maxLength){
                maxLength=name.length();
            }
        }
        return maxLength;
    }
}
