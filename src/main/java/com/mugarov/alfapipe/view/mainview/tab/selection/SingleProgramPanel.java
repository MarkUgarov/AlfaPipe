/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.control.listeners.tabrelated.cluster.ClusterSelectionListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProgramListener;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.mainview.tab.parameters.ProgramParameterPanel;
import com.mugarov.alfapipe.view.optics.OpticPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;

/**
 *
 * @author mugarov
 */
public class SingleProgramPanel extends OpticPane{
    
    private final ClusterCheckBox clusterBox;
    private final OpticPane checkBoxPanel;
    private final Color background;
    
    private final OpticPane rightPanel;
    private ProgramSelectionPanel selection;
    private ProgramParameterPanel parameters;
    private boolean isEmpty;
    
   public SingleProgramPanel(int index){
       
       this.setDoubleBuffered(true);
       this.setLayout(new BorderLayout());
       
       this.checkBoxPanel = new OpticPane();
       this.checkBoxPanel.setLayout(new BorderLayout());
       this.checkBoxPanel.setDoubleBuffered(true);
       this.clusterBox = new ClusterCheckBox(index);
       this.checkBoxPanel.add(this.clusterBox.inOpaquePanel(), BorderLayout.CENTER);
       this.checkBoxPanel.setOpaque(false);
       this.checkBoxPanel.setVisible(ParameterPool.CLUSTER_ENABLE);
       this.checkBoxPanel.setDoubleBuffered(true);
       
       
       
       this.rightPanel = new OpticPane();
       this.rightPanel.setLayout(new BorderLayout());
       this.rightPanel.setDoubleBuffered(true);
       this.add(this.rightPanel, BorderLayout.CENTER);
       
       if(index%2 ==0){
           this.background = ParameterPool.DISTINGUISH_PROGRAM_COLOR_0;
           
       }
       else{
           this.background = ParameterPool.DISTINGUISH_PROGRAM_COLOR_1;
       }
       this.setOpaque();
       this.drawBackgroundImage(false);
       this.setBackground(this.background);
       this.checkBoxPanel.setBorder(BorderFactory.createEtchedBorder());
 
       
       this.selection = null;
       this.parameters = null;
       this.isEmpty = true;
   }
   
   
   public void initSelection(String name, ProgramListener listener){
       if(listener.getValidSelections().length>0){
           this.add(this.checkBoxPanel, BorderLayout.WEST);
           this.selection = new ProgramSelectionPanel(name,listener,listener.getValidSelections());
           this.isEmpty = false;
       }
       else{
           this.selection = new ProgramSelectionPanel();
           this.isEmpty=true;
       }
       this.rightPanel.add(this.selection, BorderLayout.NORTH);
       this.updateUI();
    }
   
   public void initClusterSelection(ClusterSelectionListener listener){
       this.clusterBox.addItemListener(listener);
   }
   
   public void disableClusterByPresettings(){
       this.clusterBox.disableByPresetting();
   }
   
   public void reenableCluster(){
       this.clusterBox.reenable();
   }
   
    public void setParameters(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        if(!this.isEmpty){
            this.rightPanel.removeAll();
            this.rightPanel.add(this.selection, BorderLayout.NORTH);
            this.parameters = new ProgramParameterPanel(name, parameters, listener);
            if(!this.parameters.isEmpty()){
                this.rightPanel.add(this.parameters, BorderLayout.SOUTH);
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
        if(this.clusterBox != null){
            this.clusterBox.disableEditing();
        }
        this.updateUI();
    }
    
    public boolean isEmpty(){
        return this.isEmpty;
    }
    
}
