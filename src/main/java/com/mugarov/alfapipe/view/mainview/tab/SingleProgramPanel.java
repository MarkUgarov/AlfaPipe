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
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class SingleProgramPanel extends JPanel{
    
    private ProgramSelectionPanel selection;
    private JPanel parameterPanel;
    private ProgramParameterPanel parameters;
    
   public SingleProgramPanel(){
       this.setDoubleBuffered(true);
       this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       
       this.selection = null;
       this.parameterPanel = new JPanel();
       this.parameterPanel.setLayout(new BoxLayout(this.parameterPanel, BoxLayout.Y_AXIS));
       this.parameters = null;
   }
   
   
   public void initSelection(String name, ProgramListener listener){
       this.selection = new ProgramSelectionPanel(name,listener,listener.getValidSelections());
       this.removeAll();
       this.add(this.selection);
       this.add(this.parameterPanel);
       this.updateUI();
    }
    
    public void setParameters(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.parameterPanel.removeAll();
        this.parameters = new ProgramParameterPanel(name, parameters, listener);
        if(!this.parameters.isEmpty()){
            System.out.println("Parameters of "+name+"should be shown.");
            for(InputParameter par:parameters){
                System.out.println("\t"+par.getName());
            }
            this.parameterPanel.add(this.parameters);
        }
        else{
            System.out.println("Parameters of "+name+" are empty!");
        }
        this.updateUI();
    }
    
    public void disableEditing(){
        this.selection.disableEditing();
        this.parameters.disableEditing();
        this.updateUI();
    }
    
    
}
