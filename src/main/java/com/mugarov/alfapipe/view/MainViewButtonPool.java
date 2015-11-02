/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import com.mugarov.alfapipe.control.listeners.MainViewButtonListener;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.view.Button;

/**
 *
 * @author Mark
 */
public class MainViewButtonPool {
    private Button start;
    private Button addSet;
    
    public MainViewButtonPool(){
        this.start = new Button(Pool.BUTTON_START_TEXT, Pool.BUTTON_START_COMMAND);
        this.addSet = new Button(Pool.BUTTON_ADD_DATA_SET_TEXT, Pool.BUTTON_ADD_DATA_SET_COMMAND);
        this.start.addActionListener(Pool.LISTENER_BUTTON);
        this.addSet.addActionListener(Pool.LISTENER_BUTTON);
         
    }
    
    public Button getStartButton(){
        return this.start;
    }
    public Button getNewSetButton(){
        return this.addSet;
    }

 
}
