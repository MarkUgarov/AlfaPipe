/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.model.Pool;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/**
 *
 * @author mugarov
 */
public class SelectionPanel extends JPanel{
    private final ButtonGroup group;
    private final ActionListener listener;
    // needed to select only the first button
    private boolean isFirst;
    
    private JScrollPane scrollable;
    private JPanel buttonPanel;
    
    private JPanel namePanel;
    private JLabel nameLabel;
    
    
    public SelectionPanel(String name, ActionListener listener, String[] values){
        this.setLayout(new BorderLayout());
        this.isFirst = true;
        this.group = new ButtonGroup();
        this.listener = listener;
        
        this.namePanel = new JPanel();
        this.nameLabel = new JLabel(name);
        this.namePanel.add(this.nameLabel);
        this.add(this.namePanel, BorderLayout.WEST);
        
        this.scrollable = new JScrollPane();
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

    void disableEditing() {
       Enumeration<AbstractButton> buttons = this.group.getElements();
        while (buttons.hasMoreElements()){     
            AbstractButton button = (AbstractButton)buttons.nextElement();
            button.setEnabled(false);
        }
    }
}
