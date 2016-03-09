/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.mainview.tab.parameters.ProgramParameterPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class ClusterPanel extends JPanel{
    
    private final JPanel parameterParentPanel;
    private ProgramParameterPanel parameterPanel;
    
    public ClusterPanel(){
        this.setLayout(new BorderLayout());
        this.parameterParentPanel = new JPanel();
        this.parameterParentPanel.setLayout(new BorderLayout());
        this.add(this.parameterParentPanel);

    }
    
    public void setParameters(ArrayList<InputParameter> parameters, ParameterListener listener){
        this.parameterParentPanel.removeAll();
        this.parameterPanel = new ProgramParameterPanel("Cluster", parameters, listener);
        this.parameterParentPanel.add(this.parameterPanel, BorderLayout.CENTER);
        this.updateUI();
    }
    
    
}
