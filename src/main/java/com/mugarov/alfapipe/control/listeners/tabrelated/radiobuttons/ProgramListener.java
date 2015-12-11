/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons;

import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.awt.event.ActionListener;

/**
 *
 * @author mugarov
 */
public interface ProgramListener extends ActionListener{
    
    public abstract ParseableProgramParameters getSelected();
    
    public abstract void setFileSet(SetOfFiles set);
    
    public abstract String[] getValidSelections();
    
    public abstract int getMaxNameLength();
    
    public abstract void setInitialParameters();
    
}
