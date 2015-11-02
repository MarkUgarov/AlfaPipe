/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated.parameters;

import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.mainview.tab.parameters.ProgramParameterDocument;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Mark
 */
public class ParameterListener implements ItemListener, DocumentListener{
    
    private final ArrayList<InputParameter> parameters;
    private InputParameter lastParameter;
    
    public ParameterListener( ArrayList<InputParameter> parameters){
        this.parameters = parameters;
        if(this.parameters.isEmpty()){
            this.lastParameter = null;
        }
        else{
            this.lastParameter = this.parameters.get(0);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        JCheckBox source = (JCheckBox) ie.getSource();
//        System.out.println(ie.getStateChange() == ItemEvent.SELECTED ? source.getText() +" was selected" : source.getText()+" was unselected");
        this.checkBoolean(source.getText(), ie.getStateChange() == ItemEvent.SELECTED );
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        ProgramParameterDocument field = (ProgramParameterDocument) de.getDocument();
//        System.out.println("\tInsert on "+field.getName() + " so the text is now "+field.getValue() );
        this.checkValue(field);
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        ProgramParameterDocument field = (ProgramParameterDocument) de.getDocument();
//        System.out.println("\tRemoval on "+field.getName() + " so the text is now "+field.getValue() );
        this.checkValue(field);
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        ProgramParameterDocument field = (ProgramParameterDocument) de.getDocument();
//        System.out.println("\tChange on "+field.getName() + " so the text is now "+field.getValue() );
        this.checkValue(field);
    }
    
    private void checkValue(ProgramParameterDocument doc){
//        System.out.println("\tTrying to apply "+doc.getName()+" to value "+doc.getValue());
        if(this.lastParameter == null || !this.lastParameter.getName().equals(doc.getName())){
            // change lastParameter
            boolean found = false;
            int i = 0;
            while(!found &&i<this.parameters.size()){
                if(this.parameters.get(i).getName().equals(doc.getName())){
                    this.lastParameter = this.parameters.get(i);
                    found=true;
                }
                i++;
            }
            if(!found){
                System.err.println(doc.getName() +" NOT FOUND");
            }
        }
        this.lastParameter.setValue(doc.getValue());
    }
    
    private void checkBoolean(String name, boolean select){
        if(this.lastParameter == null || !this.lastParameter.getName().equals(name)){
            // change lastParameter
            boolean found = false;
            int i = 0;
            while(!found &&i<this.parameters.size()){
                if(this.parameters.get(i).getName().equals(name)){
                    this.lastParameter = this.parameters.get(i);
                    found=true;
                }
                i++;
            }
            if(!found){
                System.err.println(name +" NOT FOUND");
            }
        }
        this.lastParameter.setBoolean(select);
    }
    
    public ArrayList<InputParameter> getInputParameters(){
        return this.parameters;
    }
}
