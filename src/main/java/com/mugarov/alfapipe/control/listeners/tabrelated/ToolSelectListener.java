/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;

/**
 *
 * @author Mark
 */
public class ToolSelectListener implements ItemListener{
    private SetOfFiles fileSet;
    
    public ToolSelectListener(){
        this.fileSet = null;
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        JCheckBox box =((JCheckBox) ie.getItemSelectable());
//        System.out.println(ie.getStateChange() == ItemEvent.SELECTED ? "was selected" : "was unselected");
        String toolName= box.getText();
        if(this.fileSet != null){
            if(ie.getStateChange() == ItemEvent.SELECTED){
//                System.out.println(box.getText()+" was selected;");
                this.fileSet.selectToolForAll(toolName);
            }
            else{
//                System.out.println(box.getText()+" was unselected;");
                this.fileSet.unselectToolForAll(toolName);
            }
            
        }
        

        
    }
    
    public void setFileSet(SetOfFiles set){
        this.fileSet = set;
    }

    public int getMaxNameLength() {
        int maxLength = 0;
        String[] names = ParameterPool.GENERTATOR_TOOLS.getAvailableNames();
        for(String name:names){
            if(name.length()<maxLength){
                maxLength=name.length();
            }
        }
        return maxLength;
    }
    
}
