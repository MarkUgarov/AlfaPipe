/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.parameters;

import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Mark
 */
public class ProgramParameterDocument extends PlainDocument{
    
    private final String name;
    
    public ProgramParameterDocument(String name, ParameterListener listener){
        this.name = name;
        this.addDocumentListener(listener);
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getValue(){
        String val = null;
        try {
            val= this.getText(0, this.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(ProgramParameterDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return val;
    }
    
    
}
