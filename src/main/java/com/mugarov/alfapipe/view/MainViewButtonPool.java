/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import com.mugarov.alfapipe.view.optics.OpticButton;
import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.ParameterPool;

/**
 *
 * @author Mark
 */
public class MainViewButtonPool {
    private final OpticButton start;
    private final OpticButton addSet;
    
    public MainViewButtonPool(){
        this.start = new OpticButton(ParameterPool.BUTTON_START_TEXT, ParameterPool.BUTTON_START_COMMAND);
        this.addSet = new OpticButton(ParameterPool.BUTTON_ADD_DATA_SET_TEXT, ParameterPool.BUTTON_ADD_DATA_SET_COMMAND);
        this.start.addActionListener(ComponentPool.LISTENER_BUTTON);
        this.addSet.addActionListener(ComponentPool.LISTENER_BUTTON);
         
    }
    
    public OpticButton getStartButton(){
        return this.start;
    }
    public OpticButton getNewSetButton(){
        return this.addSet;
    }

 
}
