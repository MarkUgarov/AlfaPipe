/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners;

import com.mugarov.alfapipe.control.FileSetManager;
import com.mugarov.alfapipe.control.QancellorControl;
import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Mark
 */
public class MainViewButtonListener implements ActionListener{
    
    private FileSetManager fileSetManager;
    private QancellorControl qancellorControl;
    
    public MainViewButtonListener(){
        this.fileSetManager = null;
        this.qancellorControl = null;

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.fileSetManager == null){
            System.err.println("No FileSetManager was set in "+MainViewButtonListener.class.getName());
        }
        else if(ae.getActionCommand().equals(ParameterPool.BUTTON_START_COMMAND)){
            this.fileSetManager.run();
        }
        else if(ae.getActionCommand().equals(ParameterPool.BUTTON_ADD_DATA_SET_COMMAND)){

            this.fileSetManager.add();
        }
        else if(ae.getActionCommand().equals(ParameterPool.BUTTON_CANCEL_ALL_COMMAND)){
            this.fileSetManager.cancelAll();
        }
        else if(ae.getActionCommand().equals(ParameterPool.BUTTON_QANCELLOR_COMMAND)){
            this.qancellorControl.openQancellor();
        }
        else{
            System.out.println(ae.getActionCommand()+ " has been performed. Not detected");
        }
    }
    
    public void setFileSetManager(FileSetManager manager){
        this.fileSetManager = manager;
        this.qancellorControl = ComponentPool.QANCELL_CONTROLL;
    }
    
    
}
