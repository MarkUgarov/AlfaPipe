/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Mark
 */
public class MenuListener implements ActionListener{
    
    public MenuListener(){
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }

    
}
