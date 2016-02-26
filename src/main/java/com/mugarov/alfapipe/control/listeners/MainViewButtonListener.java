/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners;

import com.mugarov.alfapipe.control.FileSetManager;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.view.mainview.MainJFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Mark
 */
public class MainViewButtonListener implements ActionListener{
    
    private FileSetManager setManager;
    private int counter;
    
    public MainViewButtonListener(){
        this.setManager = null;
        this.counter = 0;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getActionCommand().equals(ParameterPool.BUTTON_START_COMMAND)){
            System.out.println("Here should start");
            this.setManager.run();
        }
        else if(ae.getActionCommand().equals(ParameterPool.BUTTON_ADD_DATA_SET_COMMAND)){
            this.counter ++;
//            System.out.println("Adding Set - activated for the "+counter+ "th time (ActionListener ready)");
            this.setManager.add();
            
        }
        else{
            System.out.println(ae.getActionCommand()+ " has been performed. Not detected");
        }
    }
    
    public void setFileManager(FileSetManager manager){
        this.setManager = manager;
    }
    
    
}
