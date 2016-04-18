/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.parameters;

import com.mugarov.alfapipe.control.listeners.tabrelated.cluster.ClusterSelectionListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.view.optics.OpticPane;
import com.mugarov.alfapipe.view.mainview.tab.selection.ClusterCheckBox;
import com.mugarov.alfapipe.view.optics.OpticCheckBox;
import com.mugarov.alfapipe.view.optics.OpticScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;


/**
 *
 * @author Mark
 */
public class ProgramParameterPanel extends OpticPane{
    private final String name;
    private final int maxRows;
    private final int maxColumns;
    private final ParameterListener listener;
    
    private final OpticPane offsetPanel;
    
    private final OpticPane namePanel;
    private final JLabel nameLabel;
    
    private final OpticScrollPane scrollable;
    private final OpticPane parameterPanel;
    
    private final ArrayList<JCheckBox> boxes;
    private final ArrayList<ProgramParameterTextField> textFields;
    private final ArrayList<OpticPane> singleParameters;
    
    private ClusterCheckBox clusterBox;
    
    
    
    public ProgramParameterPanel(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.setDoubleBuffered(true);
        this.name = name;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEtchedBorder());
        
        this.offsetPanel = new OpticPane(new BorderLayout());
        this.offsetPanel.setPreferredSize(ParameterPool.LABEL_OFFSET);
       

        this.namePanel = new OpticPane();
        if(this.name != null){
            this.namePanel.setLayout(new FlowLayout());
            this.nameLabel = new JLabel(this.name);
            this.nameLabel.setPreferredSize(ParameterPool.LABEL_DIMENSION);
            this.nameLabel.setOpaque(false);
            this.namePanel.add(this.offsetPanel);
            this.namePanel.add(this.nameLabel);
            this.namePanel.setDoubleBuffered(true);
            this.add(this.namePanel, BorderLayout.WEST);
        }
        else{
            this.nameLabel = null;
        }
        
        this.maxColumns = ParameterPool.PARAMETERS_IN_ONE_ROW;
        
        //count parameters
        int shown = 0;
        for(InputParameter par:parameters){
            if(par.isShown()){
                shown++;
            }
        }
        
        this.maxRows = (int) Math.ceil((((double)shown)/(double)this.maxColumns));
        //System.out.println(name+" Parameters:"+parameters.size()+", max Columns:"+this.maxColumns+", max Rows:"+this.maxRows+ "= "+(float)(((double)parameters.size())/(double)this.maxColumns));
        this.listener = listener;
        
        this.boxes = new ArrayList<>();
        this.textFields = new ArrayList<>();
        this.singleParameters = new ArrayList<>();
                
        this.scrollable = new OpticScrollPane();
        this.parameterPanel = new OpticPane();   
        this.parameterPanel.setLayout(new GridLayout(this.maxRows,this.maxColumns));
        for(InputParameter par:parameters){
            if(par.isShown()){
                this.addParameter(par);
            }
        }
        this.scrollable.add(this.parameterPanel);
        this.scrollable.setViewportView(this.parameterPanel);
        this.add(this.scrollable, BorderLayout.CENTER);
        this.clusterBox = null;
        this.setBackground(ParameterPool.COLOR_BACKGROUND_SECOND);
    }
    
 
    public void addClusterBox(ClusterSelectionListener clusterSelectionListener, int index) {
        this.clusterBox = new ClusterCheckBox(index, true);
        this.clusterBox.addItemListener(clusterSelectionListener);
        this.offsetPanel.add(this.clusterBox.inOpaquePanel(), BorderLayout.WEST);
    }
    
    public void disableClusterByPresettings(){
        if(this.clusterBox != null){
            this.clusterBox.disableByPresetting();
        }
    }
    
    public void reenableCluster(){
        if(this.clusterBox != null){
            this.clusterBox.reenable();
        }
    }
    
    private void addParameter(InputParameter parameter){
//        System.out.println("\t Add parameter "+parameter.getName());
        OpticPane parPan = new OpticPane();
        parPan.setLayout(new FlowLayout());
        
        if(parameter.isOptional()){
            OpticCheckBox toolBox= new OpticCheckBox(parameter.getName());
            toolBox.setToolTipText(parameter.getToolTip());
            toolBox.addItemListener(this.listener);
            toolBox.setSelected(parameter.getBoolean());
            toolBox.setDoubleBuffered(true);
            toolBox.setOpaque(true);
            this.boxes.add(toolBox);
            parPan.add(toolBox.inTransparentPanel());
//            System.out.println("\t \t Added checkbox "+parameter.getName());
        }
        else{
            JLabel parName = new JLabel(parameter.getName());
            parName.setOpaque(true);
            parName.setToolTipText(parameter.getToolTip());
            parPan.add(parName);
        }
 
        if(!parameter.isBoolean()){
            ProgramParameterTextField parField = new ProgramParameterTextField(parameter.getName(), this.listener);
            parField.setColumns(12);
            if(parameter.getValue() != null){
                parField.setText(parameter.getValue());
            }
            this.textFields.add(parField);
            parPan.add(parField);
//            System.out.println("\t \t Added label and field "+parameter.getName());
        }
       
        
        this.parameterPanel.add(parPan);
        this.singleParameters.add(parPan);
        this.updateUI();
    }
    
    public boolean isEmpty(){
        return this.boxes.isEmpty();
    }

    public void disableEditing() {
        for(JCheckBox box:this.boxes){
            box.setEnabled(false);
        }
        for(ProgramParameterTextField field:this.textFields){
            field.setEditable(false);
        }
        if(this.clusterBox != null){
            this.clusterBox.disableEditing();
        }
    }
   
}
