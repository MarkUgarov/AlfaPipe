/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.view.optics.OpticPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class ColorExplanation extends OpticPane{
    
    private final ArrayList<String> descriptions;
    private final ArrayList<Color> colors;
    private final int maxColumns;
    private final int maxRows;
    
    public ColorExplanation(){
        super();
        this.descriptions = new ArrayList<>();
        this.colors = new ArrayList<>();
        this.maxColumns = 2;
        this.createLists();
        
        int maxEntries = Math.max(this.descriptions.size(), this.colors.size());
        this.maxRows = (int) Math.ceil((((double)maxEntries)/(double)this.maxColumns));
        this.setLayout(new GridLayout(this.maxRows,this.maxColumns));
        
        this.addPanels();
 
    }
    
    private void createLists(){
       
        this.colors.add(ParameterPool.COLOR_BACKGROUND_CLUSTER);
        this.descriptions.add("Cluster related - check Tooltips for further information");
        
        this.colors.add(ParameterPool.COLOR_BACKGROUND_DISABLED);
        this.descriptions.add("Disabled owing to current circumstances (configuration, selection or state)");
        
        this.colors.add(ParameterPool.COLOR_VALID);
        this.descriptions.add("Valid input in regard to the selected pipe");
        
        this.colors.add(ParameterPool.COLOR_INVALID);
        this.descriptions.add("Invalid input in regard to the selected pipe");
        
        this.colors.add(ParameterPool.COLOR_SUCCESS);
        this.descriptions.add("Section of the selected pipe processed successfully");
        
        this.colors.add(ParameterPool.COLOR_FAILURE);
        this.descriptions.add("Section of the selected pipe processed unsuccessfully");
        
    }
    
    private void addPanels(){
        for(int i = 0; i<this.descriptions.size() && i<this.colors.size(); i++){
            this.add(this.getPane(this.descriptions.get(i), this.colors.get(i)));
        }
    }
    
    
    private OpticPane getPane(String description, Color color){
        OpticPane element = new OpticPane(new FlowLayout());
        //element.setLayout(new BorderLayout());
        
        JPanel colorBox = new JPanel();
        
        colorBox.setBackground(color);
        colorBox.setPreferredSize(ParameterPool.COLOR_EPLANATION_BOX_DIMENSION);
        
        JLabel descriptionLabel = new JLabel(description);
        
        element.add(colorBox);
        element.add(new JLabel(":"));
        element.add(descriptionLabel);
        
        
        return element;
    }
    
}
