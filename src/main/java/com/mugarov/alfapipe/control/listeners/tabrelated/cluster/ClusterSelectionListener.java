/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated.cluster;

import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.view.mainview.tab.selection.ClusterCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author mugarov
 */
public class ClusterSelectionListener implements ItemListener{
    
    private SetOfFiles fileSet;
    
    public ClusterSelectionListener(SetOfFiles set){
        this.fileSet = set;
    }
    
    public ClusterSelectionListener(){
        this.fileSet = null;
    }

    
    public void setFileSet(SetOfFiles fileSet){
        this.fileSet = fileSet;
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        ClusterCheckBox box =((ClusterCheckBox) ie.getItemSelectable());

        int index = box.getIndex();
       
        if(ie.getStateChange() == ItemEvent.SELECTED){
           if(this.fileSet != null){
               this.fileSet.selectClusterFor(index);
           }
           box.setTooltipFor(true);
        }
       else{
           if(this.fileSet != null){
               this.fileSet.unselectClusterFor(index);
           }
           box.setTooltipFor(false);
       }
    }

    
    
}
