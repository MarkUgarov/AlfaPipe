/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.control.listeners.tabrelated.cluster.ClusterSelectionListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.optics.OpticPane;
import com.mugarov.alfapipe.view.mainview.tab.parameters.ProgramParameterPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author mugarov
 */
public class ClusterPanel extends OpticPane{
    
    private final OpticPane parameterParentPanel;
    private ProgramParameterPanel parameterPanel;
    
    public ClusterPanel(){
        this.setDoubleBuffered(true);
        this.setLayout(new BorderLayout());
        this.parameterParentPanel = new OpticPane();
        this.parameterParentPanel.setLayout(new BorderLayout());
        this.add(this.parameterParentPanel, BorderLayout.CENTER);
        this.add(this.getHintLabelInPanel(ParameterPool.LABEL_HINT_CLUSTER), BorderLayout.SOUTH);
        this.setOpaque();
        this.drawBackgroundImage(false);
        this.setBackground(ParameterPool.COLOR_BACKGROUND_CLUSTER);
        this.setVisible(ParameterPool.CLUSTER_ENABLE);
    }
    
    public void setParameters(ArrayList<InputParameter> parameters, ParameterListener parameterListener, ClusterSelectionListener clusterListener){
        this.parameterParentPanel.removeAll();
        this.parameterPanel = new ProgramParameterPanel("Cluster", parameters, parameterListener);
        this.parameterPanel.addClusterBox(clusterListener, -1, true);
        this.parameterParentPanel.add(this.parameterPanel, BorderLayout.CENTER);
       
        this.updateUI();
    }
    
    public void disableEditing(){
        if(this.parameterPanel != null){
            this.parameterPanel.disableEditing();
        }
    }
    
    private OpticPane getHintLabelInPanel(String labeltext){
        JLabel label = new JLabel(labeltext, SwingConstants.CENTER);
        label.setOpaque(false);
        label.setForeground(ParameterPool.LABLE_UNIMPORTANCE_COLOR);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 5, ParameterPool.LABLE_UNIMPORTANCE_COLOR));
        
        OpticPane ret = new OpticPane();
        ret.add(label);
        return ret;
    }
    
    
    
    
}
