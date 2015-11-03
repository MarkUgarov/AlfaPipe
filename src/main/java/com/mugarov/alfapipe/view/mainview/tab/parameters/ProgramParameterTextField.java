/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.parameters;

import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import javax.swing.JTextField;

/**
 *
 * @author Mark
 */
public class ProgramParameterTextField extends JTextField{
    
    public ProgramParameterTextField(String name, ParameterListener listener){
        super();
        this.setDoubleBuffered(true);
        this.setDocument(new ProgramParameterDocument(name, listener));
    }
    
    
}
