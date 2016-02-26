/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProgramListener;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.mainview.tab.parameters.ProgramParameterPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class SingleProgramPanel extends JPanel{
    
    private ProgramSelectionPanel selection;
    private ProgramParameterPanel parameters;
    private boolean isEmpty;
    
   public SingleProgramPanel(){
       this.setDoubleBuffered(true);
       this.setLayout(new BorderLayout());
       
       this.selection = null;
       this.parameters = null;
   }
   
   
   public void initSelection(String name, ProgramListener listener){
       if(listener.getValidSelections().length>0){
           this.selection = new ProgramSelectionPanel(name,listener,listener.getValidSelections());
           this.isEmpty = false;
       }
       else{
           this.selection = new ProgramSelectionPanel();
       }
       this.add(this.selection, BorderLayout.NORTH);
       this.updateUI();
    }
    
    public void setParameters(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        if(!this.isEmpty){
            this.removeAll();
            this.add(this.selection, BorderLayout.NORTH);
            this.parameters = new ProgramParameterPanel(name, parameters, listener);
            if(!this.parameters.isEmpty()){
                this.add(this.parameters, BorderLayout.SOUTH);
            }
            this.updateUI();
        }
    }
    
    public void disableEditing(){
        if(this.selection != null & !this.isEmpty){
            this.selection.disableEditing();
        }
        if(this.parameters != null && !this.isEmpty){
            this.parameters.disableEditing();
        }
        this.updateUI();
    }
    
    public boolean isEmpty(){
        return this.isEmpty;
    }
    
}
