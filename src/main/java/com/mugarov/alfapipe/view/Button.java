/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import javax.swing.JButton;

/**
 *
 * @author Mark
 */
public class Button extends JButton{
    
    public Button(String text, String command){
        super(text);
        this.setDoubleBuffered(true);
        this.setActionCommand(command);
    }

}
