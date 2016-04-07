/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.model.ParameterPool;
import javax.swing.JCheckBox;

/**
 *
 * @author mugarov
 */
public class ClusterCheckBox extends JCheckBox {
    
    private final int index;
    private final String onSelected;
    private final String onNotSelected;
    private final boolean isTool;
    
    public ClusterCheckBox(int index){
        super();
        this.index = index;
        this.onSelected = ParameterPool.TOOLTIP_CLUSTER_CHECKBOX_SELECTED;
        this.onNotSelected = ParameterPool.TOOLTIP_CLUSTER_CHECKBOX_UNSELECTED;
        this.isTool = false;
        this.setToolTipText(this.onNotSelected);
        this.setOpaque(false);
    }
    
    public ClusterCheckBox(int index, boolean isTool){
        super();
        this.index = index;
        this.onSelected = ParameterPool.TOOLTIP_CLUSTER_CHECKBOX_SELECTED;
        this.onNotSelected = ParameterPool.TOOLTIP_CLUSTER_CHECKBOX_UNSELECTED;
        this.isTool = isTool;
        this.setToolTipText(this.onNotSelected);
        this.setOpaque(false);
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public boolean isTool(){
        return this.isTool;
    }
    
    public void  setTooltipFor(boolean selected){
         String setto = selected ? onSelected : onNotSelected;
         this.setToolTipText(setto);
    }
}
