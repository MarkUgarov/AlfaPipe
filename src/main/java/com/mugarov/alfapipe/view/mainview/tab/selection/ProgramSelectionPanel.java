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
    private final JPanel buttonPanel;
    
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
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new FlowLayout());
        this.scrollable.add(this.buttonPanel);
        this.scrollable.setViewportView(this.buttonPanel);
        this.add(this.scrollable, BorderLayout.CENTER);
        
        for(String a:values){
            this.addButton(a);
        }
        this.group.setSelected(null, true);
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
        this.buttonPanel = null;
    }
    
    private void addButton(String content){
        JRadioButton b = new JRadioButton(content);
        b.addActionListener(this.listener);
        this.group.add(b);
        this.buttonPanel.add(b);
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
        if(this.buttonPanel != null){
            this.buttonPanel.setBackground(bg);
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
