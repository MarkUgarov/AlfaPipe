/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.control.listeners.tabrelated.AssemblerRadioButtonListener;
import com.mugarov.alfapipe.model.programparse.datatypes.ParseableProgramParameters;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Mark
 */
public class AssemblerSelection extends JPanel{
    
    private final ButtonGroup group;
    private final AssemblerRadioButtonListener listener;
    private final GridBagConstraints c;
    // needed to select only the first button
    private boolean isFirst;
    
    public AssemblerSelection(AssemblerRadioButtonListener listener){
        c = new GridBagConstraints();
        this.setLayout(new GridLayout(1,1));
        this.isFirst = true;
        this.group = new ButtonGroup();
        this.listener = listener;
        for(String a:Pool.GENERATOR_ASSEMBLER.getAvailableNames()){
            this.addButton(a);
        }
        this.group.setSelected(null, true);
    }
    
    private void addButton(String content){
        JRadioButton b = new JRadioButton(content);
        b.addActionListener(this.listener);
        this.group.add(b);
        this.add(b);
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
