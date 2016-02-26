/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.ParameterPool;

/**
 *
 * @author Mark
 */
public class MainViewButtonPool {
    private final Button start;
    private final Button addSet;
    
    public MainViewButtonPool(){
        this.start = new Button(ParameterPool.BUTTON_START_TEXT, ParameterPool.BUTTON_START_COMMAND);
        this.addSet = new Button(ParameterPool.BUTTON_ADD_DATA_SET_TEXT, ParameterPool.BUTTON_ADD_DATA_SET_COMMAND);
        this.start.addActionListener(ComponentPool.LISTENER_BUTTON);
        this.addSet.addActionListener(ComponentPool.LISTENER_BUTTON);
         
    }
    
    public Button getStartButton(){
        return this.start;
    }
    public Button getNewSetButton(){
        return this.addSet;
    }

 
}
