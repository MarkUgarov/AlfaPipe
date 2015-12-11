/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.parameters;

import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.model.Pool;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Mark
 */
public class ProgramParameterPanel extends JPanel{
    private final String name;
    private final int maxRows;
    private final int maxColumns;
    private final ParameterListener listener;
    
    private final JPanel namePanel;
    private final JLabel nameLabel;
    
    private final JScrollPane scrollable;
    private final JPanel parameterPanel;
    
    private final ArrayList<JCheckBox> boxes;
    private final ArrayList<ProgramParameterTextField> textFields;
    
    
    
    public ProgramParameterPanel(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        super();
        this.setDoubleBuffered(true);
        System.out.println("Added Program "+name);
        this.name = name;
        this.setDoubleBuffered(true);
        this.setLayout(new BorderLayout());
        this.namePanel = new JPanel();
        this.namePanel.setLayout(new FlowLayout());
        this.nameLabel = new JLabel(this.name);
        this.nameLabel.setPreferredSize(Pool.LABEL_DIMENSION);
        this.namePanel.add(this.nameLabel);
        this.add(this.namePanel, BorderLayout.WEST);
        
        
        this.maxColumns = Pool.PARAMETERS_IN_ONE_ROW;
        this.maxRows = parameters.size()/this.maxColumns;
        this.listener = listener;
        
        this.boxes = new ArrayList<>();
        this.textFields = new ArrayList<>();
                
        this.scrollable = new JScrollPane();
        this.parameterPanel = new JPanel();   
        this.parameterPanel.setLayout(new GridLayout(this.maxRows,this.maxColumns));
        for(InputParameter par:parameters){
            if(par.isShown()){
                this.addParameter(par);
            }
        }
        this.scrollable.add(this.parameterPanel);
        this.scrollable.setViewportView(this.parameterPanel);
        this.add(this.scrollable, BorderLayout.CENTER);
        
    }
    
    private void addParameter(InputParameter parameter){
//        System.out.println("\t Add parameter "+parameter.getName());
        JPanel parPan = new JPanel();
        parPan.setLayout(new FlowLayout());
        
        if(parameter.isOptional()){
            JCheckBox toolBox= new JCheckBox(parameter.getName());
            toolBox.addItemListener(this.listener);
            toolBox.setSelected(parameter.getBoolean());
            this.boxes.add(toolBox);
            parPan.add(toolBox);
//            System.out.println("\t \t Added checkbox "+parameter.getName());
        }
        else{
            JLabel parName = new JLabel(parameter.getName());
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
    }
    
   
}
