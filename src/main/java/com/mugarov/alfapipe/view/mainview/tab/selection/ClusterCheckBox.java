/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.control.listeners.MouseOver;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.view.optics.Optic;
import com.mugarov.alfapipe.view.optics.OpticCheckBox;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author mugarov
 */
public class ClusterCheckBox extends OpticCheckBox {
    
    private final int index;
    private final String onSelected;
    private final String onNotSelected;
    private boolean isTool;
    
    public ClusterCheckBox(int index){
        super();
        this.setDoubleBuffered(true);
        this.index = index;
        this.onSelected = ParameterPool.TOOLTIP_CLUSTER_CHECKBOX_SELECTED;
        this.onNotSelected = ParameterPool.TOOLTIP_CLUSTER_CHECKBOX_UNSELECTED;
        this.isTool = false;
        this.setToolTipText(this.onNotSelected);
        super.setOpaque(true);
        super.drawBackgroundImage(false);
        this.setBackground(ParameterPool.COLOR_BACKGROUND_CLUSTER);
        this.addMouseListener(new MouseOver(this));
        Dimension size = new Dimension(10,10);
        this.setHorizontalAlignment(SwingConstants.CENTER);

    }
    
    public ClusterCheckBox(int index, boolean isTool){
        this(index);
        this.isTool = isTool;
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public boolean isTool(){
        return this.isTool;
    }
    
    public void setTooltipFor(boolean selected){
         String setto = selected ? onSelected : onNotSelected;
         this.setToolTipText(setto);
    }
    
    public void disableEditing(){
        this.setEnabled(false);
    }
    
    public JPanel inOpaquePanel(){
        JPanel ret = new JPanel();
        ret.setOpaque(true);
        ret.setBackground(this.getBackground());
        ret.setDoubleBuffered(true);
        ret.add(this);
        return ret;
    }

}
