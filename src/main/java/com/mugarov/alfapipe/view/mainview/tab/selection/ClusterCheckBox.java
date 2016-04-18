/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.control.listeners.MouseOver;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.view.optics.OpticCheckBox;
import java.awt.Color;
import java.awt.Dimension;
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
    private final String onDisabled;
    private boolean isTool;
    private Color enabledColor;
    private JPanel surroundingPanel;
    
    public ClusterCheckBox(int index){
        super();
        this.setDoubleBuffered(true);
        this.index = index;
        this.onSelected = ParameterPool.TOOLTIP_CLUSTER_CHECKBOX_SELECTED;
        this.onNotSelected = ParameterPool.TOOLTIP_CLUSTER_CHECKBOX_UNSELECTED;
        this.onDisabled = ParameterPool.TOOLTIP_DISABLED;
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
    
    @Override
    public void disableByPresetting(){
        super.disableByPresetting();
        this.setToolTipText(this.onDisabled);
    }
    
    @Override
    public void reenable(){
        super.reenable();
        this.setTooltipFor(this.isSelected());
    }
    
    public JPanel inOpaquePanel(){
        if(this.surroundingPanel == null){
            this.surroundingPanel = new JPanel();
            this.surroundingPanel.setOpaque(true);
            this.surroundingPanel.setBackground(this.getBackground());
            this.surroundingPanel.setDoubleBuffered(true);
            this.surroundingPanel.add(this);
        }
        return this.surroundingPanel;
    }
    

}
