/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.view.optics.OpticPane;
import com.mugarov.alfapipe.view.optics.OpticScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author mugarov
 */
public class ProgramSelectionPanel extends OpticPane{
    private final ButtonGroup group;
    private final ActionListener listener;
    // needed to select only the first button
    private boolean isFirst;
    
    private final OpticScrollPane scrollable;
    private final JPanel radioButtonPanel;
    
    private final JPanel namePanel;
    private final JLabel nameLabel;
    
    
    public ProgramSelectionPanel(String name, ActionListener listener, String[] values){
        this.setDoubleBuffered(true);
        this.setLayout(new BorderLayout());
        this.isFirst = true;
        this.group = new ButtonGroup();
        this.listener = listener;
        
        this.namePanel = new JPanel();
        this.nameLabel = new JLabel(name);
        this.nameLabel.setPreferredSize(ParameterPool.LABEL_DIMENSION);
        this.namePanel.add(this.nameLabel);
        this.add(this.namePanel, BorderLayout.WEST);
        
        this.scrollable = new OpticScrollPane();
        this.radioButtonPanel = new JPanel();
        this.radioButtonPanel.setLayout(new FlowLayout());
        this.radioButtonPanel.setDoubleBuffered(true);
        this.scrollable.add(this.radioButtonPanel);
        this.scrollable.setViewportView(this.radioButtonPanel);
        this.add(this.scrollable, BorderLayout.CENTER);
        
        for(String a:values){
            this.addRadioButton(a);
        }
        this.group.setSelected(null, true);
        this.radioButtonPanel.setDoubleBuffered(true);
        this.namePanel.setDoubleBuffered(true);
    }
    
    /**
     * Creates basically an empty Panel.
     */
    public ProgramSelectionPanel(){
        super();
        this.isFirst = true;
        this.group = null;
        this.listener = null;
        this.scrollable = null;
        this.nameLabel = null;
        this.namePanel = null;
        this.radioButtonPanel = null;
    }
    
    private void addRadioButton(String content){
        JRadioButton b = new JRadioButton(content);
        b.addActionListener(this.listener);
        b.setDoubleBuffered(true);
        this.group.add(b);
        this.radioButtonPanel.add(b);
        if(this.isFirst){
            b.setSelected(true);
            this.isFirst = false;
        }
    }

    public void disableEditing() {
        if(this.group != null){
            Enumeration<AbstractButton> buttons = this.group.getElements();
            while (buttons.hasMoreElements()){     
                AbstractButton button = (AbstractButton)buttons.nextElement();
                button.setEnabled(false);
            }
        }
       
    }
    
    @Override
    public void setBackground(Color bg){
        super.setBackground(bg);
        if(this.namePanel != null){
            this.namePanel.setBackground(bg);
        }
        if(this.nameLabel != null){
            this.nameLabel.setBackground(bg);
        }
        if(this.radioButtonPanel != null){
            this.radioButtonPanel.setBackground(bg);
        }
        if(this.scrollable != null){
            this.scrollable.setBackground(bg);
        }
        
        if(this.group != null){
            Enumeration<AbstractButton> buttons = this.group.getElements();
            while (buttons.hasMoreElements()){     
                AbstractButton button = (AbstractButton)buttons.nextElement();
                button.setBackground(bg);
            }
        }
    }
}
